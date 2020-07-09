package autyzmsoft.pl.liczykropka

import org.junit.Before
import org.junit.Test

class MojGeneratorTest extends GroovyTestCase {

    def SUT;

    @Before
    void setUp() {
        super.setUp();
        SUT = new MojGenerator(0,0);
    }


    @Test
    void testDajWartDowolna() {
        var result = -1;
        result = SUT.dajWartUnikalna();
        assertEquals(0, result);
    }