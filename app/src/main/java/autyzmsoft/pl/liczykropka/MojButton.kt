package autyzmsoft.pl.liczykropka

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue

class MojButton(
    context: Context?,
    btnWys: Int,
    textRozmiar: Float,
    wyraz: String
) : androidx.appcompat.widget.AppCompatButton(context) {
    init {
        //1. Kreowanie buttona:
        this.height = btnWys
        this.text = wyraz
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textRozmiar)
        this.setTypeface(null, Typeface.BOLD)
        /**/
        this.setBackgroundColor(Color.GRAY)

    } //koniec Konstruktora
}