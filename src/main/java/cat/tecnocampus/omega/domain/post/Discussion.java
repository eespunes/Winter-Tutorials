package cat.tecnocampus.omega.domain.post;

import java.util.ArrayList;
import java.util.List;
import cat.tecnocampus.omega.domain.*;

public class Discussion extends Post {

    private boolean hasBestComment;
    private User user;

    private List<Comment> comments= new ArrayList<Comment>();

    public Discussion(String postID, String description, String title,User user,String category) {
        super(postID, description, title,category);
        hasBestComment=false;
        this.user=user;
    }

    public Discussion(String body, String title,User user) {
        super(body, title,"Java");
        hasBestComment=false;
        this.user=user;
    }

    public void setDescription(String body){
        description=body;
    }

    public Discussion(){
        super();
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addComment(List<Comment> comment){
        for (Comment c:comments) {
            comments.add(c);
        }
    }

    public List<Comment> getComments(){
        return comments;
    }
    void setHasBestComment(boolean stat,Comment comment){
        hasBestComment=stat;
    }

    public User getUser(){
        return user;
    }
    public boolean hasBest(){
        return hasBestComment;
    }
}
