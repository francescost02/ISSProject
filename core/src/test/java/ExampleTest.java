import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import io.ISSProject.game.Calculator;

public class ExampleTest {

    private Calculator calculator;
    //test fallimentare prima dello sviluppo del codice
    @Test
    void testAdd(){
        Calculator calculator = new Calculator();
        int result = calculator.add(2,3);
        assertEquals(5,result);
    }

    @BeforeEach
    void setup(){
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown(){
        
    }


}
