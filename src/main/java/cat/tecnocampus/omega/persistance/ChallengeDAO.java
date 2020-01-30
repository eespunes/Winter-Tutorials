package cat.tecnocampus.omega.persistance;

import cat.tecnocampus.omega.domain.exercises.Exercise;
import cat.tecnocampus.omega.domain.post.Challenge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ChallengeDAO {

    private JdbcTemplate jdbcTemplate;
    private ExerciseDAO exerciseDAO;
    private final String FIND_ALL = "SELECT * FROM Posts WHERE son_type=?";
    private final String FIND_BY_ID = "SELECT * FROM Posts WHERE post_id = ? AND son_type = ?";
    private final String INSERT_CHALLENGE = "INSERT INTO posts (post_ID, title, description, creationDay, likes, enable, son_TYPE,category) VALUES(?, ?, ?, ?, ?,?,?,?)";
    private final String FIND_BY_CATEGORY= "SELECT * FROM Posts WHERE category=? AND son_type=?";


    public ChallengeDAO(JdbcTemplate jdbcTemplate, ExerciseDAO exerciseDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.exerciseDAO = exerciseDAO;
    }

    private Challenge challengeMapper(ResultSet resultSet) throws SQLException {
        Challenge challenge = new Challenge(resultSet.getString("post_id"), resultSet.getString("description"), resultSet.getString("title"), resultSet.getString("category"));
        return challenge;
    }

    public List<Challenge> findByCategory(String category){
        return jdbcTemplate.query(FIND_BY_CATEGORY,new Object[]{category,"Challenge"}, mapperEager);
    }

    private RowMapper<Challenge> mapperEager = (resultSet, i) -> {
        Challenge challenge = challengeMapper(resultSet);
        List<Exercise> exercises = exerciseDAO.findExercisesByPost(challenge.getPostID());
        challenge.addExercises(exercises);
        return challenge;
    };

    public List<Challenge> findAll() {
        return jdbcTemplate.query(FIND_ALL, new Object[]{"Challenge"}, mapperEager);
    }
    public Challenge findById(String id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id,"Challenge"}, mapperEager);
    }
    public int insertChallenge(Challenge challenge, String category) {
        return jdbcTemplate.update(INSERT_CHALLENGE, challenge.getPostID(), challenge.getTitle(), challenge.getDescription(), challenge.getCreationDay(), challenge.getLikes(), challenge.isEnable(), "Challenge",category);
    }
}
