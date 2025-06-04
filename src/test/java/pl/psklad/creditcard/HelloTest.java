package pl.psklad.creditcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloTest
{
    @Test
    void itMyFirstTest() {
        int a = 2;
        int b = 3;

        int result = a + b;

        assert result == 5;
    }

    @Test
    void itMyFirstFailingTest() {
        int a = 2;
        int b = 3;

        int result = a;

        assert result == 5;
    }

    @Test
    void moreReadableAssertion() {
        assertTrue(false, "Your condition is not true anymore");
    }

}
