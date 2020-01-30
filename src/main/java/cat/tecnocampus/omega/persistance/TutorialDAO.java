package cat.tecnocampus.omega.persistance;

import cat.tecnocampus.omega.domain.Category;
import cat.tecnocampus.omega.domain.exercises.Exercise;
import cat.tecnocampus.omega.domain.post.Tutorial;
import com.sun.corba.se.impl.interceptors.PICurrent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.PanelUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class TutorialDAO {

    private JdbcTemplate jdbcTemplate;
    private ExerciseDAO exerciseDAO;

    private final String FIND_ALL = "SELECT * FROM Posts WHERE son_type=?";
    private final String FIND_BY_ID = "SELECT * from Posts WHERE post_id = ? AND son_type = ?";
    private final String INSERT_TUTORIAL = "INSERT INTO Posts (post_id, title, description, creationDay, likes, enable, son_type, category) VALUES (?, ?, ?, ?, ?,?,?,?)";
    private final String INSERT_CATEGORY = "INSERT INTO Category (category) VALUES (?)";
    private final String FIND_BY_CATEGORY = "SELECT * from Posts WHERE category=? AND son_type=?";
    private final String FIND_CATEGORIES = "SELECT * from Category";


    public TutorialDAO(JdbcTemplate jdbcTemplate, ExerciseDAO exerciseDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.exerciseDAO = exerciseDAO;
    }

    private Tutorial tutorialMapper(ResultSet resultSet) throws SQLException {
        Tutorial tutorial = new Tutorial(resultSet.getString("post_id"), resultSet.getString("description"), resultSet.getString("title"), resultSet.getString("category"));
        return tutorial;
    }

    private Category categoryMapper(ResultSet resultSet) throws SQLException {
        Category category = new Category(resultSet.getString("category"));
        return category;
    }

    public List<Tutorial> findByCategory(String category) {
        return jdbcTemplate.query(FIND_BY_CATEGORY, new Object[]{category, "Tutorial"}, mapperEager);
    }

    private RowMapper<Tutorial> mapperEager = (resultSet, i) -> {
        Tutorial tutorial = tutorialMapper(resultSet);
        List<Exercise> exercises = exerciseDAO.findExercisesByPost(tutorial.getPostID());
        tutorial.addExercises(exercises);
        return tutorial;
    };
    private RowMapper<Category> categoryMapperEager = (resultSet, i) -> {
        Category category = categoryMapper(resultSet);
        return category;
    };

    public List<Tutorial> findAll() {
        return jdbcTemplate.query(FIND_ALL, new Object[]{"Tutorial"}, mapperEager);
    }

    public Tutorial findById(String id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id, "Tutorial"}, mapperEager);
    }

    public int insertTutorial(Tutorial tutorial, String category) {
        return jdbcTemplate.update(INSERT_TUTORIAL, tutorial.getPostID(), tutorial.getTitle(), tutorial.getDescription(), tutorial.getCreationDay(), tutorial.getLikes(), tutorial.isEnable(), "Tutorial", category);
    }

    public List<Category> findCategories() {
        return jdbcTemplate.query(FIND_CATEGORIES, new Object[]{}, categoryMapperEager);
    }

    public int insertCategory(Category category) {
        if (!findCategories().contains(category))
            return jdbcTemplate.update(INSERT_CATEGORY, category.getName());
        else return 1;
    }
}
