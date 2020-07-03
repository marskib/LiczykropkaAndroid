package autyzmsoft.pl.liczykropka

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var screenInches: Double = 0.0
    private var txSize: Float = 0.0f
    private var height: Int = 0
    private var btH: Int = 0
    private var width: Int = 0


    val tButtons : Array<Button?> = arrayOfNulls<Button>(6) //tablica na klawisze

    val lBts :Int = 6;      //liczba buttonow (z Ustawien)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//--------------------
        //Na caly ekran:
        //1.Remove title bar:
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //2.Remove notification bar:
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //3.Set content view AFTER ABOVE sequence (to avoid crash):
        setContentView(R.layout.activity_main)
        //4. Usuwa godzine, date i inne pierdoly:
        supportActionBar?.hide()
//--------------------

        wygenerujButtony()
        ustawListenerNaTVCyfra()
        ustawListeneryNaKlawisze()

/*
        val mButton1 : MojButton = MojButton(this, btnWys = 200,
            textRozmiar = 50.0F,
            wyraz= "ala ma kota")

        val mButton2 : MojButton = MojButton(this, btnWys = 200,
            textRozmiar = 50.0F,
            wyraz= "Ola ma psa")

*/

    }

    fun ustawListenerNaTVCyfra() {
        tv_cyfra.setOnClickListener {
            try {
                var wart = tv_cyfra.text.toString().toInt()
                if (wart>100) wart = -1
                wart++;
                tv_cyfra.text = wart.toString();
            } catch (e:Exception) {
                tv_cyfra.text = "0";
            }
        }
    }

    private fun wygenerujButtony() {
        /* Generujemy lBts buttonow; zapamietujemy w tablicy tButtons[]; pokazujemy na ekranie */
        var mb: MojButton //robocza, dla wiekszej czytelnosci
        val kolko = 9679
        var kolka:String=""
        //
        oszacujWysokoscButtonow_i_Tekstu()
        //
        for (i in 0..lBts-1) {

            kolka=kolka.plus(kolko.toChar()) //zabawa.. 2020.07.03 -> wywalic...

            mb = MojButton(this, btH, txSize, kolka)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mb.setLetterSpacing(0.4.toFloat())
            } //UWAGA!!! - na "żywca"... patrz wyżej


            mb.setOnClickListener(coNaKlikNaBtn)
            tButtons[i] = mb
            buttons_area.addView(tButtons[i])
            //Ustawienie marginesow miedzy buttonami (musi byc poza konstruktorem - klawisz musi fizyczne lezec na layoucie, inaczej nie dziala):
            val params: LinearLayout.LayoutParams
            params = tButtons[i]?.getLayoutParams() as LinearLayout.LayoutParams
            params.setMargins(
                0,
                0,//(btH /9) as Int, //bylo /7
                10,
                0
            ) //substitute parameters for Left, Top, Right, Bottom ( LTRB )

            //params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tButtons[i]?.setLayoutParams(params)
            tButtons[i]?.setVisibility(View.VISIBLE) //za chwile pokaze z opoznieniem - efekciarstwo ;)
        }
    }  //koniec Funkcji



    var coNaKlikNaBtn = View.OnClickListener {
        var ts = (it as Button).textSize
        tv_cyfra.setTextSize(ts)
        tv_cyfra.text = (it as Button).text.toString()   //hashCode().toString()
    }


    fun ustawListeneryNaKlawisze() {
        for (b in tButtons) b?.setOnClickListener(coNaKlikNaBtn)
    }

    private fun oszacujWysokoscButtonow_i_Tekstu() {
        /* ******************************************************************************************************************************** */
        /* Na podstawie liczby buttonow (=wybranego poziomu trudnosci) szacuje wysokosc buttonow btH i wielkosc tekstów na buttonach txSize */
        /* Wartosci te beda uzywane przy kreowaniu buttonow (wysokosc but.) + wielkosci textu na tvWyraz                                    */
        /* Algorytm wypracowany doświadczalnie....                                                                                          */
        /* ******************************************************************************************************************************** */

        //Pobieram wymiary ekranu:

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        width = dm.widthPixels
        height = dm.heightPixels

        if (lBts <= 4) {
            val lBtsRob = 2
            btH = height / (lBtsRob + 3) //bylo 2; button height; doswiadczalnie
            btH = btH - 0
        }
        if (lBts == 5) {   // bo dobrze wyglada przy 5-ciu klawiszach:
            val lBtsRob = 4
            btH = height / (lBtsRob + 2) //button height; doswiadczalnie
        }
        if (lBts == 6) {
            btH = height / (lBts + 1) //button height; doswiadczalnie
        }
        //txSize = (float) (btH / 1.9); //to 1.9 było ok, ale ponizej 2.0 pozwala zmiescic "dziewczynka" na mniejszych rozdzielczosciach
        txSize = (btH / 3.5).toFloat()

        //Troche efektow ubocznych:
        //Przekatna ekranu w calach (na oszacowanie wielkosci tekstu wyswietlanie podpowiedzi pod obrazkiem):
        val x = Math.pow(dm.widthPixels / dm.xdpi.toDouble(), 2.0)
        val y = Math.pow(dm.heightPixels / dm.ydpi.toDouble(), 2.0)
        screenInches = Math.sqrt(x + y)

        btH = (btH/1.2).toInt() //podrasowanie na Kotlin - 2020.07.03

    } //koniec Metody()


}




