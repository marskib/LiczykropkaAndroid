package autyzmsoft.pl.liczykropka

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class MojGeneratorTest {

    val min=0
    val max=6

    var SUT: MojGenerator = MojGenerator(0,0)

    @Before
    fun setUp(){
        SUT = MojGenerator(min,max)
    }


    @Test
    fun dajWartDowolna_Test_ShouldBeInMinMax() {
        var gen = SUT.dajWartDowolna();
        var wPrzedziale = (gen>=min)&&(gen<=max)
        assertEquals(true,wPrzedziale)
     }

    @Test
    fun czyUnikalne() {

        var tab = arrayOfNulls<Int>(max-min+1)

//        for (i in min..max) {
//            tab[i] = SUT.dajWartUnikalna()
//        }

        for (i in tab.indices) {
            tab[i] = SUT.dajWartUnikalna()
        }


        tab.sort()

        var takieSame = (tab[0]==tab[tab.size-1])


        assertEquals(false,takieSame)
    }






}