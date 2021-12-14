package il.ac.shenkar.costManager;

public class Category {
    private User owner;
    private String name;
    private int id;

    public Category(User owner, String name, int id) {
        this.owner = owner;
        this.name = name;
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
