package autyzmsoft.pl.liczykropka

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue

class MojButton(

    context       : Context?,
    paramWartosc  : Int,
    paramCzyJakLiczba: Boolean,
    textRozmiar   : Float,
    btnWys        : Int

) : androidx.appcompat.widget.AppCompatButton(context) {

    private var wartosc = 0;      //liczba 0..6 przypisane do klawisza

    private var CzyJakLiczba = false //jak ma obrazowac 'wartosc' - jak liczbe, czy kolka

    private val kolko = 9679.toChar()
    private var kolka : String=""

    init {
        this.wartosc  = paramWartosc
        this.CzyJakLiczba = paramCzyJakLiczba
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textRozmiar)
        this.setTypeface(null, Typeface.BOLD)
        this.setBackgroundColor(Color.GRAY)
        this.height = btnWys
        this.umiescWartoscGraficznie()
    }


    fun dajWartoscStringowo(): String {
    /* Wypisuje/zwraca wartosc liczbowo lub graficznie */
        if (CzyJakLiczba)
          return wartosc.toString()
        else {
            kolka = ""
            for (i in 0..wartosc-1)  kolka=kolka.plus(kolko)
            return kolka
        }
    }

    fun dajWartoscJakoCyfre(): String {
        return wartosc.toString()
    }

    fun dajWartoscJakoKolka(): String {
        kolka = ""
        for (i in 0..wartosc-1)  kolka=kolka.plus(kolko)
        return kolka
    }

    private fun umiescWartoscGraficznie() {
    /* Zobrazowuje this.wartosc na klawiszu - liczbowo lub graficznie */
       this.text = dajWartoscStringowo()
    }

    fun getCzyJakLiczba(): Boolean {
        return this.CzyJakLiczba
    }

    fun getWartosc(): Int {
        return this.wartosc
    }




}