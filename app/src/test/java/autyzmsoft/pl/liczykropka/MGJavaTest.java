package autyzmsoft.pl.liczykropka;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MGJavaTest {
    MGJava SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new MGJava(0,6);
    }

    @Test
    public void dajWartDowolna_10_Returns10() {
        int result = SUT.dajWartDowolna();
        assertEquals(10,result);
    }
}