package cat.tecnocampus.omega.persistanceController;

import cat.tecnocampus.omega.domain.Category;
import cat.tecnocampus.omega.persistance.ForumDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cat.tecnocampus.omega.domain.post.Discussion;
import cat.tecnocampus.omega.domain.post.Comment;
import java.util.List;

@Service("ForumController")
public class ForumController {

    private final ForumDAO forumDAO;

    public ForumController(ForumDAO forumDAO){
        this.forumDAO=forumDAO;
    }

    @Transactional
    public int addDiscussion(Discussion discussion, String username, String category){
        return forumDAO.insertDiscussion(discussion,username,category);
    }

    @Transactional
    public int addComment(Comment comment, String username, String id){
        return forumDAO.insertComment(comment, username,id);
    }

    @Transactional
    public int addCommentReply(Comment comment, String username, String id,String reply){
        return forumDAO.insertCommentReply(comment, username,id,reply);
    }
    public List<Comment> getComment (String id){
        return forumDAO.findCommentByDiscussion(id);
    }

    public Discussion getDiscussion (String id){
        return forumDAO.findDiscussion(id);
    }

    public List<Discussion> getDiscussions (){
        return forumDAO.getAllDiscussions();
    }
    public List<Category> getCategories(){return forumDAO.findCategories();}
    public  List<Discussion> getByCategory(String category){ return forumDAO.findByCategory(category);}
    @Transactional
    public int addCategory(Category category) { return forumDAO.insertCategory(category); }
}
