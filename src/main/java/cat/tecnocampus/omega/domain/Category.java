package cat.tecnocampus.omega.domain;

public class Category {

    private String name;

    public Category(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Category) {
            Category category = (Category) o;
            return name.equals(category.getName());
        }
        return true;
    }
}
