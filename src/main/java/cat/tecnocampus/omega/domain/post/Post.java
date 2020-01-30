package cat.tecnocampus.omega.domain.post;

import cat.tecnocampus.omega.domain.exercises.Exercise;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class Post {

    protected String postID;
    @NotNull(message = "Description cannot be null")
    @Size(max = 10000, message = "Description must be less than 10000 characters long")
    protected String description;
    @NotNull(message = "Title cannot be null")
    @Size(min = 4, max = 150, message = "Title must be between 4 an 150 characters long")
    protected String title;
    protected Date creationDay;
    protected int likes;
    protected boolean enable;
    protected List<Exercise> exerciseList;
    protected String category;

    public Post(String postID, String description, String title, String category){
        this.postID=postID;
        this.description=description;
        this.title=title;
        creationDay=new Date();
        likes = 0;
        enable=true;
        exerciseList=new ArrayList<Exercise>();
        this.category=category;
    }
    public Post(String description, String title, String category){
        postID=UUID.randomUUID().toString();
        this.description=description;
        this.title=title;
        creationDay=new Date();
        likes = 0;
        enable=true;
        exerciseList=new ArrayList<Exercise>();
        this.category=category;
    }
    public Post() {
        postID=UUID.randomUUID().toString();
        creationDay=new Date();
        likes = 0;
        enable=true;
        exerciseList=new ArrayList<Exercise>();
    }
    public String getCategory(){
        return category;
    }
    public String getPostID() {
        return postID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDay() {
        return creationDay;
    }

    public int getLikes() {
        return likes;
    }

    public void addLike() {
        likes++;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void addExercises(List<Exercise> exerciseList) {
        this.exerciseList.addAll(exerciseList);
    }
}