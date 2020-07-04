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
        calyEkran()
        //Set content view AFTER ABOVE sequence (to avoid crash):
        setContentView(R.layout.activity_main)
        wygenerujButtony()
        ustawListenerNaTVCyfra()
        ustawListeneryNaKlawisze()
    }  //koniec onCreate()

    fun calyEkran() {
        //* Aplikacja rozdmuchana na caly ekran */
        //1.Remove title bar:
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //2.Remove notification bar:
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //3. Usuwa godzine, date i inne pierdoly:
        supportActionBar?.hide()
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

        var dx = 0; //margin w pionie pomiedzy klawiszami
        //
        oszacujWysokoscButtonow_i_Tekstu()
        //
        for (i in 0..lBts-1) {

            mb = MojButton(this, (0..6).random(), true, txSize, btH)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mb.setLetterSpacing(0.4.toFloat())
            }

            mb.setOnClickListener(coNaKlikNaBtn)
            tButtons[i] = mb
            buttons_area.addView(tButtons[i])
            //Ustawienie marginesow miedzy buttonami (musi byc poza konstruktorem - klawisz musi fizyczne lezec na layoucie, inaczej nie dziala):
            val params: LinearLayout.LayoutParams
            params = tButtons[i]?.getLayoutParams() as LinearLayout.LayoutParams
            dx = 10;
            if (lBts<4) dx = 20;
            params.setMargins(0,dx,0,0 )
            tButtons[i]?.setLayoutParams(params)
            tButtons[i]?.setVisibility(View.VISIBLE) //za chwile pokaze z opoznieniem - efekciarstwo ;)
        }
    }  //koniec Funkcji



    var coNaKlikNaBtn = View.OnClickListener {
    /* Wypisanie liczby bądź kolek w polu tv_liczba */

        if ((it as MojButton).getCzyJakLiczba()) { //jezeli na klawiszu jest liczba, to w tv_cyfra wyswietlamu kolka
            var ts = (it as Button).textSize
            ts = (ts/1.2).toFloat()
            tv_cyfra.setTextSize(ts)
            tv_cyfra.text = ""
            tv_cyfra.text = (it as MojButton).dajWartoscJakoKolka()
        }
        else //jak na klawiszu kolka, to wyswietlamy cyfre
            tv_cyfra.text = (it as MojButton).dajWartoscJakoCyfre()
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
            btH = height / (lBts + 1)  //button height; doswiadczalnie
        }
        txSize = (btH / 3.5).toFloat()

        //podrasowanie na Kotlin - 2020.07.03:
        btH = when (lBts) {
            6 -> (btH/1.30).toInt()
            5 -> (btH/1.25).toInt()
            else -> (btH/1.20).toInt()
        }

    } //koniec Metody()


}




