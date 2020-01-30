package cat.tecnocampus.omega.domain.exercises;

import cat.tecnocampus.omega.domain.User;

import java.util.Date;

public class Submission {

    private float mark;
    private User user;
    private Exercise exercise;
    private Date creationDate;

    private boolean pass;

    public Submission() {
    }

    public Submission(float mark, User user, Exercise exercise) {
        this.mark = mark;
        this.user = user;
        this.exercise = exercise;
        creationDate = new Date();
        if (mark >= 7.5)
            pass = true;
        else
            pass = false;
    }
    public Submission(float mark, User user, Exercise exercise,Date creationDate) {
        this.mark = mark;
        this.user = user;
        this.exercise = exercise;
        this.creationDate = creationDate;
        if (mark >= 7.5)
            pass = true;
        else
            pass = false;
    }

    public float getMark() {
        return mark;
    }

    public User getUser() {
        return user;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean getPass() {
        return pass;
    }
}
