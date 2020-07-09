package autyzmsoft.pl.liczykropka
/***
 * Klasa do generowania liczb losowych z przedzialu min..max]
 * M.Skibinski, 2020.07.09
 * 2 tryby:
 * a) liczby losowe z przedzialu min..max, NIEPOWTARZALNE
 * b) jw., ale powtarzalne
 * W trybie pierwszym rzuca wyjatek, jesli nie da sie uzyskac liczby, ktora wczesniej nie wystapila
 */

class MojGenerator(val min:Int, val max:Int) {

    private val zbLiczb = mutableSetOf<Int>() //obiekt klasy pamieta liczby, ktore juz kiedys wygenerowal

    class CustomExceptionSkib(message: String) : Exception(message)

    fun dajWartUnikalna():Int {

        //Najpierw sprawdzam, czy da sie cos jeszcze niepowtarzalnego wygenerowac
        var jestCosWolnego:Boolean = false;
        for (i in min..max) {
            if (!zbLiczb.contains(i)) {
                jestCosWolnego = true
            }
        }
        if (!jestCosWolnego) {
            throw (CustomExceptionSkib("brak wolnych liczb"))
        }

        var gen = (min..max).random()
        while (zbLiczb.contains(gen)) {
            gen = (min..max).random()
        }
        zbLiczb.add(gen) //zeby pamietac, ze juz kiedys wygenerowano te liczbe
        return gen;
    }

    fun dajWartDowolna():Int {
        return (min..max).random()
    }

}