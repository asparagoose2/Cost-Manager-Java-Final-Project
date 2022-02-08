package il.ac.shenkar.costManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item(1,1,"test",1,12.2,new Date(),new Category(new User("user",1),"food",1));
    }

    @AfterEach
    void tearDown() {
        item = null;
    }

    @Test
    void getDescription() {
        String expected = "test";
        String actual = item.getDescription();
        assertEquals(expected,actual);
    }

    @Test
    void setDescription() {
        item.setDescription("new name");
        String expected = "new name";
        String actual = item.getDescription();
        assertEquals(expected,actual);
    }

    @Test
    void getAmount() {
        double expected = 12.2;
        double actual = item.getCost();
        assertEquals(expected,actual,0.0);
    }

    @Test
    void setAmount() {
        item.setCost(100);
        double expected = 100;
        double actual = item.getCost();
        assertEquals(expected,actual,0.0);
    }
}