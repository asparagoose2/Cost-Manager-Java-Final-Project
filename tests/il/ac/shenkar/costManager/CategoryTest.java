package il.ac.shenkar.costManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    private Category category;
    private static User user;

    @BeforeAll
    public static void setUpBeforeClass() {
        user = new User("Test user", 1);
    }

    @BeforeEach
    public void setUp() {
        category = new Category(user, "Test", 1);
    }

    @Test
    void getName() {
        assert (category.getName().equals("Test"));
    }

    @Test
    void getId() {
        assert (category.getId() == 1);
    }

    @Test
    void getOwner() {
        assert (category.getOwner().equals(user));
    }


}
