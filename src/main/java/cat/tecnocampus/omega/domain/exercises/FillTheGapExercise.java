package cat.tecnocampus.omega.domain.exercises;

public class FillTheGapExercise extends Exercise {
    public FillTheGapExercise(String exercise_ID, String description, int difficulty, boolean drag) {
        super(exercise_ID, description, difficulty);
        this.type = "Fill";
        this.drag = drag;
    }

    public FillTheGapExercise() {
        super();
        this.type = "Fill";
    }

    public void setQuestions(String questions) {
        String[] before = questions.split("#R");
        boolean added = false;
        Question question = null;
        if (before.length > 1) {
            int counter = 0;
            String questionText = "";
            for (String s : before) {
                if (counter == 0) {
                    questionText = s;
                    counter++;
                    continue;
                }
                String[] after = s.split("#/R");
                if (!added)
                    question = new Question(questionText);
                question.validation();
                Solution solution = new Solution(after[0], true);
                solution.validation();
                addQuestion(question);
                question.addSolution(solution);
                added = false;
                if (after.length > 1) {
                    added = true;
                    question = new Question(after[1]);
                }
                counter++;
            }
        }
    }
}
