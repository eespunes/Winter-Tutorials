package cat.tecnocampus.omega.domain.exercises;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class Solution {
    @NotNull
    private String solution_ID;
    @NotNull
    @Size(max = 36,message = "The text is too long")
    private String text;
    private int order;
    @NotNull
    private boolean enable;

    @NotNull
    private boolean correct;

    public Solution() {
    }

    public Solution(@NotNull String solution_ID, @NotNull @Size(max = 1024, message = "The text is too long") String text, int order, @NotNull boolean correct) {
        this.solution_ID = solution_ID;
        this.text = text;
        this.order = order;
        this.correct = correct;
        this.enable = true;
    }
    public Solution(@NotNull String solution_ID, @NotNull @Size(max = 1024, message = "The text is too long") String text, @NotNull boolean correct) {
        this.solution_ID = solution_ID;
        this.text = text;
        this.correct = correct;
        this.enable = true;
    }

    public Solution(String text, int order, boolean correct) {
        this.solution_ID = UUID.randomUUID().toString();
        this.text = text;
        this.order = order;
        this.correct = correct;
        this.enable = true;
    }

    public Solution(String text, boolean correct) {
        this.solution_ID = UUID.randomUUID().toString();
        this.text = text;
        this.correct = correct;
        this.enable = true;
    }

    public String getSolution_ID() {
        return solution_ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean getCorrect(){
        return correct;
    }

    public void validation(){
        if(this.text.length()>1024)
            throw new IllegalArgumentException("SOMETHING WENT WRONG WHEN CREATING A Solution:\n\t\tThe text is too long, should be less than 1024 characters \n Text:"+this.text);
        if(this.text.length()==0)
            throw new IllegalArgumentException("SOMETHING WENT WRONG WHEN CREATING A Solution:\n\t\tThe text should have at least one character");
    }
}
