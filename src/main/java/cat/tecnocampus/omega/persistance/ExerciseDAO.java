package cat.tecnocampus.omega.persistance;

import cat.tecnocampus.omega.domain.exercises.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExerciseDAO {
    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;

    private final String INSERT_EXERCISE = "INSERT INTO Exercises VALUES (?, ?, ?, ?,?,?,?,?);";
    private final String INSERT_QUESTION = "INSERT INTO Questions VALUES (?, ?, ?, ?);";
    private final String INSERT_SOLUTION = "INSERT INTO Solutions (solution_id,texts, correct, enable, question_id) VALUES (?, ?, ?, ?,?);";
    private final String INSERT_SUBMISSION = "INSERT INTO Submissions VALUES (?,?,?,?,?);";

    private final String DELETE_EXERCISE = "";
    private final String DELETE_QUESTION = "";
    private final String DELETE_SOLUTION = "";

    private final String SELECT_EXERCISE_BY_POST = "SELECT * FROM Exercises WHERE post_id = ?";
    private final String SELECT_EXERCISE_BY_ID_AND_TYPE = "SELECT * FROM Exercises WHERE exercise_id = ? AND son_type = ?";
    private final String SELECT_EXERCISE_BY_ID = "SELECT * FROM Exercises WHERE exercise_id = ?";
    private final String SELECT_QUESTION_BY_EXERCISE = "SELECT * FROM Questions WHERE exercise_id = ?";
    private final String SELECT_SOLUTION_BY_QUESTION = "SELECT * FROM Solutions WHERE question_id = ?";
    private final String SELECT_SUBMISSION_BY_EXERCISE_AND_USERNAME = "SELECT * FROM Submissions WHERE exercise = ? AND username = ?";

    public ExerciseDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDAO = userDAO;
    }

    private Exercise exerciseMapper(ResultSet resultSet) throws SQLException {
        if (resultSet.getString("son_type").equals("Test")) {
            TestExercise exercise = new TestExercise(resultSet.getString("exercise_id"), resultSet.getString("description"), resultSet.getInt("difficulty"));
            exercise.setExperience_points(resultSet.getInt("experience_points"));
            return exercise;
        } else {
            FillTheGapExercise exercise = new FillTheGapExercise(resultSet.getString("exercise_id"), resultSet.getString("description"), resultSet.getInt("difficulty"),resultSet.getBoolean("drag"));
            exercise.setExperience_points(resultSet.getInt("experience_points"));
            return exercise;
        }
    }

    private RowMapper<Question> questionMapper = (resultSet, i) -> {
        Question question = new Question(resultSet.getString("question_id"), resultSet.getString("texts"));
        return question;
    };

    private RowMapper<Solution> solutionMapper = (resultSet, i) -> {
        Solution solution = new Solution(resultSet.getString("solution_id"), resultSet.getString("texts"), resultSet.getBoolean("correct"));
        return solution;
    };

    private RowMapper<Exercise> mapperEager = (resultSet, i) -> {
        Exercise exercise = exerciseMapper(resultSet);

        List<Question> questions = findQuestionByExercise(exercise.getExercise_ID());
        for (Question question : questions) {
            List<Solution> solutions = findSolutionByQuestion(question.getQuestion_ID());
            question.addSolutions(solutions);
        }
        exercise.addQuestions(questions);
        return exercise;
    };

    private RowMapper<Submission> submissionMapper = (resultSet, i) -> {
        Submission submission = new Submission(resultSet.getFloat("mark"), userDAO.findByUsername(resultSet.getString("username")), jdbcTemplate.queryForObject(SELECT_EXERCISE_BY_ID, new Object[]{resultSet.getString("exercise")}, mapperEager), resultSet.getDate("creation_date"));
        return submission;
    };

    public int insertExercise(Exercise exercise, String id, String type) {
        return jdbcTemplate.update(INSERT_EXERCISE, exercise.getExercise_ID(), exercise.getDescription(), exercise.isEnable(), exercise.getDifficulty(), exercise.getExperience_points(),exercise.isDrag(), type, id);
    }

    public int insertQuestion(Question question, String id) {
        return jdbcTemplate.update(INSERT_QUESTION, question.getQuestion_ID(), question.getText(), question.isEnable(), id);
    }

    public int insertSolution(Solution solution, String id) {
        return jdbcTemplate.update(INSERT_SOLUTION, solution.getSolution_ID(), solution.getText(), solution.getCorrect(), solution.isEnable(), id);
    }

    public int insertSubmission(Submission submission) {
        return jdbcTemplate.update(INSERT_SUBMISSION, submission.getMark(), submission.getUser().getUsername(), submission.getExercise().getExercise_ID(), submission.getCreationDate(), submission.getPass());
    }

    public List<Exercise> findExercisesByPost(String id) {
        return jdbcTemplate.query(SELECT_EXERCISE_BY_POST, new Object[]{id}, mapperEager);
    }

    public Exercise findExercisesByIDAndType(String id, String type) {
        return jdbcTemplate.queryForObject(SELECT_EXERCISE_BY_ID_AND_TYPE, new Object[]{id, type}, mapperEager);
    }
    public Exercise findExercisesByID(String id) {
        return jdbcTemplate.queryForObject(SELECT_EXERCISE_BY_ID, new Object[]{id}, mapperEager);
    }

    public List<Question> findQuestionByExercise(String id) {
        return jdbcTemplate.query(SELECT_QUESTION_BY_EXERCISE, new Object[]{id}, questionMapper);
    }

    public List<Solution> findSolutionByQuestion(String id) {
        return jdbcTemplate.query(SELECT_SOLUTION_BY_QUESTION, new Object[]{id}, solutionMapper);
    }

    public List<Submission> findAllSubmissions(String id, String username) {
        return jdbcTemplate.query(SELECT_SUBMISSION_BY_EXERCISE_AND_USERNAME, new Object[]{id, username}, submissionMapper);
    }
}
