import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions.assertEquals;

public class TestMorse {
    static MorseTree mt = new MorseTree();


    @BeforeAll
    public static void init() {
        MainClass.buildTree(mt, "extended.morse");
    }


    @Test
    void test_something() {
        assertDoesNotThrow(() -> {
            for (int i = 'a'; i <= 'z'; i++)
            {
                char result = mt.decode()
            }
            char result = mt.decode("---");
            assertEquals(result, 'r', "Incorrect decode result");
        }, "Oh no! Exception!");
    }
}
