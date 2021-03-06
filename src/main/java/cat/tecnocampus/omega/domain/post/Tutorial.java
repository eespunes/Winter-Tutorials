package cat.tecnocampus.omega.domain.post;

import java.util.Date;

public class Tutorial extends Post {

    public Tutorial(String id,String description, String title, String category) {
        super(id,description,title, category);
    }

    public Tutorial(String description, String title, String category) {
        super(description,title, category);
    }

    public Tutorial() {
        super(null,null, null);
    }


    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    public String getPostID() {
        return postID;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
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

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable=enable;
    }
}
