package il.ac.shenkar.costManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class UsetTest {
    static private User user;

    @Test
    @Order(1)
    public void testUser() {
        user = new User("test_user",2);
        assert(user.getName().equals("test_user"));
        assert(user.getUserId() == 2);
    }
    @Test
    @Order(2)
    public void testUserId() {
        user.setUserId(3);
        assert(user.getUserId() == 3);
    }
    @Test
    @Order(3)
    public void testUserName() {
        user.setName("test_user2");
        assert(user.getName().equals("test_user2"));
    }

}
