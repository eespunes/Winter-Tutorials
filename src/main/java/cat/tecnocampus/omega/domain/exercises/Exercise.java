package cat.tecnocampus.omega.domain.exercises;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Exercise {

    protected String exercise_ID;

    @NotNull(message = "The Description can't be null")
    @Size(min = 5, max = 1024, message = "Description has to be between 5 and 1024 characters")
    protected String description;

    protected boolean enable;

    protected int difficulty;
    protected int experience_points;
    protected String type;

    protected Submission submission;
    protected List<Question> questions;
    protected boolean drag;

    public Exercise(String exercise_ID, String description, int difficulty) {
        this.exercise_ID = exercise_ID;
        setUp(description, difficulty);
    }

    public Exercise(String description, int difficulty) {
        this.exercise_ID = UUID.randomUUID().toString();
        setUp(description, difficulty);
    }

    public Exercise() {
        this.exercise_ID = UUID.randomUUID().toString();
        enable = true;
    }

    private void setUp(String description, int difficulty) {
        this.description = description;
        this.difficulty = difficulty;
        enable = true;
        questions = new ArrayList<Question>();
    }

    public String getExercise_ID() {
        return exercise_ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getExperience_points() {
        return experience_points;
    }

    public void setExperience_points(int experience_points) {
        this.experience_points = experience_points;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestions(List<Question> question) {
        questions.addAll(question);
    }

    public void addQuestion(Question question) {
        if(questions==null)
            questions=new ArrayList<Question>();
        questions.add(question);
    }

    public String getType() {
        return type;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public float solve(String[] solutions) {
        float numSol = 0;
        float numCorr = 0;
        for (int counter = 0; counter < solutions.length; counter++) {
            numCorr += questions.get(counter).solve(solutions[counter]);
            numSol++;
        }
        return (numCorr / numSol) * 10;
    }

    protected void setQuestions(String questions) {

    }

    public void setType(String type) {
        this.type = type;
    }
}
