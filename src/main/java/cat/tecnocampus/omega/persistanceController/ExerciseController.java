package cat.tecnocampus.omega.persistanceController;

import cat.tecnocampus.omega.domain.exercises.*;
import cat.tecnocampus.omega.persistance.ExerciseDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service("ExerciseController")
public class ExerciseController {
    private final ExerciseDAO exerciseDAO;
    private final UserController userController;
    private Map<Float, String> marks;

    public ExerciseController(ExerciseDAO exerciseDAO, UserController userController) {
        this.exerciseDAO = exerciseDAO;
        this.userController = userController;
    }

    @Transactional
    public int addExercise(Exercise exercise, String id, String type) {
        int retorn = exerciseDAO.insertExercise(exercise, id, type);
        if (retorn == 0)
            return 0;
        for (Question q : exercise.getQuestions()) {
            retorn = addQuestion(q, exercise.getExercise_ID());
            if (retorn == 0)
                return 0;
        }
        return 1;
    }

    @Transactional
    public int addQuestion(Question question, String id) {
        int retorn = exerciseDAO.insertQuestion(question, id);
        if (retorn == 0)
            return 0;
        for (Solution s : question.getSolutions()) {
            retorn = exerciseDAO.insertSolution(s, question.getQuestion_ID());
            if (retorn == 0)
                return 0;
        }
        return 1;
    }

    public Exercise getExercise(String id) {
        return exerciseDAO.findExercisesByID(id);
    }

    public Exercise getExerciseByType(String id, String type) {
        return exerciseDAO.findExercisesByIDAndType(id, type);
    }

    public Submission getSubmission(String id, String username) {
        List<Submission> list = exerciseDAO.findAllSubmissions(id, username);
        return list.get(list.size() - 1);
    }

    public void solve(String id, String[] solutions, String username, String type) {
        Exercise exercise = exerciseDAO.findExercisesByIDAndType(id, type);
        float mark = exercise.solve(solutions);
        Submission submission = new Submission(mark, userController.getUser(username), exercise);
        exerciseDAO.insertSubmission(submission);
    }

    public String getMark(float mark) {
        if (marks == null) {
            initializeMarks();
        }
        for (float f : marks.keySet()) {
            if (mark <= f)
                return marks.get(f);
        }
        return "";
    }

    private void initializeMarks() {
        marks = new TreeMap<Float, String>();
        marks.put(10f, "A+");
        marks.put(9f, "A");
        marks.put(8f, "B+");
        marks.put(7.5f, "B");
        marks.put(7f, "B-");
        marks.put(6f, "C+");
        marks.put(5f, "C");
        marks.put(4f, "C-");
        marks.put(3f, "D+");
        marks.put(2f, "D");
        marks.put(1f, "D-");
        marks.put(0f, "F");
    }

    public String pass(boolean pass) {
        String srtPass = "YOU ";
        if (pass)
            srtPass += "PASS";
        else
            srtPass += "FAILED";
        return srtPass;
    }

    public String type(String type) {
        if (!type.equals("do"))
            return "Result";
        else return "";
    }
}
