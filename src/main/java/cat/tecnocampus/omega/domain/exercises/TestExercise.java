package cat.tecnocampus.omega.domain.exercises;

import java.util.ArrayList;
import java.util.List;

public class TestExercise extends Exercise {

    public TestExercise(String exercise_ID, String description, int difficulty) {
        super(exercise_ID, description, difficulty);
        this.type = "Test";
        this.drag = false;
    }

    public TestExercise() {
        super();
        this.type = "Test";
    }

    public void setQuestions(String questions) {
        questions.replace("/n", "");
        String[] questionsArr = questions.split("#Q");
        if (questionsArr.length > 1) {
            int counter = 0;
            for (String s : questionsArr) {
                if (counter == 0) {
                    counter++;
                    continue;
                }
                String[] questionPlusSolutions = s.split("#/Q");
                Question question = new Question(counter + ". " + questionPlusSolutions[0]);
                question.validation();
                addQuestion(question);
                String[] solutions = questionPlusSolutions[1].split("#R");
                if (solutions.length > 1) {
                    List<Solution> listSolution = new ArrayList<Solution>();
                    int counterSolutions = 0;
                    for (String c : solutions) {
                        if (counterSolutions == 0) {
                            counterSolutions++;
                            continue;
                        }
                        String[] solution = c.split("#/R");
                        Solution solutionobj;
                        if (solution[0].contains("#C")) {
                            String last = "";
                            for (String end : solution[0].split("#C"))
                                last += end;
                            solutionobj = new Solution(last, true);
                        } else
                            solutionobj = new Solution(solution[0], false);
                        solutionobj.validation();
                        listSolution.add(solutionobj);
                        counterSolutions++;
                    }
                    solutionListValidation(listSolution, counter);
                    question.addSolutions(listSolution);
                }
                counter++;
            }
        }
    }

    private void solutionListValidation(List<Solution> solutions, int i) {
        int counter = 0;
        for (Solution solution : solutions)
            if (solution.getCorrect())
                counter++;

        if (counter == 0)
            throw new IllegalArgumentException("SOMETHING WENT WRONG WHEN CREATING A SOLUTION:\n\t\tThere isn't any solution for the question number " + i);
    }
}
