package il.ac.shenkar.costManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item("test", 200);
    }

    @AfterEach
    void tearDown() {
        item = null;
    }

    @Test
    void getName() {
        String expected = "test";
        String actual = item.getName();
        assertEquals(expected,actual);
    }

    @Test
    void setName() {
        item.setName("new name");
        String expected = "new name";
        String actual = item.getName();
        assertEquals(expected,actual);
    }

    @Test
    void getAmount() {
        double expected = 200;
        double actual = item.getAmount();
        assertEquals(expected,actual,0.0);
    }

    @Test
    void setAmount() {
        item.setAmount(100);
        double expected = 100;
        double actual = item.getAmount();
        assertEquals(expected,actual,0.0);
    }
}