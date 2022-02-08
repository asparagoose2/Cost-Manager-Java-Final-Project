package il.ac.shenkar.costManager;

/**
 * This class represents a category in the application.
 */
public class Category {
    private User owner;
    private String name;
    private int id;

    /**
     * Constructor for the category.
     *
     * @param owner the owner of the category.
     * @param name  the name of the category.
     * @param id    the id of the category - new categories will receive a new id by the application.
     */
    public Category(User owner, String name, int id) {
        setId(id);
        setName(name);
        setOwner(owner);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * returns the owner of the category.
     *
     * @return the owner of the category.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the category.
     *
     * @param owner the new owner of the category.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * returns the name of the category.
     *
     * @return the name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the new name of the category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the id of the category.
     *
     * @return the id of the category.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the category.
     *
     * @param id the new id of the category.
     */
    public void setId(int id) {
        this.id = id;
    }
}
