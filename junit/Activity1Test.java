package Activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1Test {
    static ArrayList<String> list;
    @BeforeEach
    public void setUp(){
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
    }

    @Test
    public void insertTest(){
        assertEquals(list.size(), 2, "Wrong size");
        list.add("charlie");
        assertEquals(list.size(), 3, "Wrong size");

        assertEquals("alpha", list.get(0), "wrong element");
        assertEquals("beta", list.get(1), "wrong element");
        assertEquals("charlie", list.get(2), "wrong element");
    }

    @Test
    public void replaceTest(){

        list.set(1, "charlie");
        assertEquals(list.size(), 2, "Wrong size");

        assertEquals("alpha", list.get(0), "wrong element");
        assertEquals("charlie", list.get(1), "wrong element");
    }
}
