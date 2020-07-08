package autyzmsoft.pl.liczykropka

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue

class MojButton(

    context : Context?,
    val wartosc     : Int,      //liczba 0..6 przypisane do klawisza
    var czyJakLiczba: Boolean,  //jak ma byc obrazowana 'wartosc' - jak liczbe, czy kolka
    val textRozmiar : Float,
    val btnWys      : Int

) : androidx.appcompat.widget.AppCompatButton(context) {

    private val kolko = 9679.toChar()
    private var circles : String = ""  //do zobrazowania wartosc'i w postaci kolek

    init {
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textRozmiar)
        this.setTypeface(null, Typeface.BOLD)
        this.setBackgroundColor(Color.GRAY)
        this.height = btnWys
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setLetterSpacing(0.4.toFloat())
        }
        this.text = dajWartoscStringowo()  //na kreowanym buttonie pojawi sie cyfra lub kolka
    }


    fun dajWartoscStringowo(): String {
    /* Wypisuje/zwraca 'wartosc' buttona liczbowo lub graficznie */
        if (czyJakLiczba)
          return dajWartoscJakoCyfre()
        else {
          return dajWartoscJakoKolka()
        }
    }

    fun dajWartoscJakoCyfre(): String {
        return wartosc.toString()
    }

    fun dajWartoscJakoKolka(): String {
        circles = ""
        for (i in 0..wartosc-1)  circles=circles.plus(kolko)
        return circles
    }



}