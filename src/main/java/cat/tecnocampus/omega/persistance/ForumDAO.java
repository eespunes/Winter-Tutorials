package cat.tecnocampus.omega.persistance;

import cat.tecnocampus.omega.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import cat.tecnocampus.omega.domain.post.Discussion;
import cat.tecnocampus.omega.domain.post.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ForumDAO {

    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;
    private Map<String, Comment> commentMap;
    private final String INSERT_DISCUSSION = "INSERT INTO Posts (post_id, title, description, creationDay, likes, enable, hasBest,son_type,username,category) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
    private final String INSERT_COMMENT = "INSERT INTO Comments (comment_id, best, comment, creation_day, likes, enable, post_id,username) VALUES (?, ?, ?, ?, ?,?,?,?)";
    private final String INSERT_COMMENT_REPLY = "INSERT INTO Comments (comment_id, best, comment, creation_day, likes, enable, post_id,username,comment_id_fk) VALUES (?, ?, ?, ?, ?,?,?,?,?)";
    private final String SELECT_DISCUSSION = "SELECT * FROM Posts WHERE post_id = ?";
    private final String SELECT_DISCUSSIONS = "SELECT * FROM Posts WHERE son_type = 'Discussion'";
    private final String SELECT_COMMENTS = "SELECT * FROM Comments WHERE post_id = ?";
    private final String SELECT_COMMENTS_BY_COMMENT = "SELECT * FROM Comments WHERE post_id = ? AND comment_id=?";
    private final String FIND_CATEGORIES= "select * from Category";
    private final String FIND_BY_CATEGORY= "select * from Posts where category=? AND son_type=?";
    private final String INSERT_CATEGORY = "INSERT INTO Category (category) VALUES (?)";

    public ForumDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDAO = userDAO;
        commentMap = new HashMap<>();
    }

    private Discussion discussionMapper(ResultSet resultSet) throws SQLException {
        Discussion discussion = new Discussion(resultSet.getString("post_id"), resultSet.getString("description"), resultSet.getString("title"), userDAO.findByUsername(resultSet.getString("username")),resultSet.getString("category"));
        return discussion;
    }

    private RowMapper<Comment> commentMapper = (resultSet, i) -> {
        Comment comment = new Comment(resultSet.getString("comment_id"), resultSet.getString("comment"), userDAO.findByUsername(resultSet.getString("username")));
        commentMap.put(comment.getCommentID(), comment);
        if (resultSet.getString("comment_id_fk") != null) {
            comment.setReply();
            commentMap.get(resultSet.getString("comment_id_fk")).setSons(comment);
        }
        return comment;
    };

    private Category categoryMapper(ResultSet resultSet) throws SQLException {
        Category category = new Category(resultSet.getString("category"));
        return category;
    }

    private RowMapper<Discussion> mapperEager = (resultSet, i) -> {
        Discussion discussion = discussionMapper(resultSet);
        System.out.println(discussion.getCategory());
        for (Comment c : findCommentByDiscussion(discussion.getPostID())) {
            if (!c.isReply())
                discussion.addComment(c);
        }
        return discussion;
    };
    private RowMapper<Category> categoryMapperEager = (resultSet, i) -> {
        Category category = categoryMapper(resultSet);
        return category;
    };

    public int insertDiscussion(Discussion discussion, String username, String category) {
        return jdbcTemplate.update(INSERT_DISCUSSION, discussion.getPostID(), discussion.getTitle(), discussion.getDescription(), discussion.getCreationDay(), discussion.getLikes(), discussion.isEnable(), discussion.hasBest(), "Discussion", username,category);
    }

    public int insertComment(Comment comment, String username, String id) {
        return jdbcTemplate.update(INSERT_COMMENT, comment.getCommentID(), comment.isBestComment(), comment.getComment(), comment.getCreationDay(), comment.getLikes(), comment.isEnable(), id, username);
    }

    public int insertCommentReply(Comment comment, String username, String id, String reply) {
        return jdbcTemplate.update(INSERT_COMMENT_REPLY, comment.getCommentID(), comment.isBestComment(), comment.getComment(), comment.getCreationDay(), comment.getLikes(), comment.isEnable(), id, username, reply);
    }

    public List<Comment> findCommentByDiscussion(String id) {
        return jdbcTemplate.query(SELECT_COMMENTS, new Object[]{id}, commentMapper);
    }

    public List<Discussion> getAllDiscussions() {
        return jdbcTemplate.query(SELECT_DISCUSSIONS, mapperEager);
    }

    public Discussion findDiscussion(String id) {
        return jdbcTemplate.queryForObject(SELECT_DISCUSSION, new Object[]{id}, mapperEager);
    }

    public List<Comment> findCommentByComment(String id, String comment_id) {
        return jdbcTemplate.query(SELECT_COMMENTS_BY_COMMENT, new Object[]{id, comment_id}, commentMapper);
    }

    public List<Category> findCategories() {
        return jdbcTemplate.query(FIND_CATEGORIES, new Object[]{}, categoryMapperEager);
    }
    public List<Discussion> findByCategory(String category){
        return jdbcTemplate.query(FIND_BY_CATEGORY,new Object[]{category,"Discussion"}, mapperEager);
    }
    public int insertCategory(Category category) {
        if (!findCategories().contains(category))
            return jdbcTemplate.update(INSERT_CATEGORY, category.getName());
        else return 1;
    }
}
