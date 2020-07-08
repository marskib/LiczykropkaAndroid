package autyzmsoft. pl.liczykropka

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val CHWILA = 2000

    private var txSize: Float = 0.0f
    private var height: Int = 0
    private var btH: Int = 0
    private var width: Int = 0


    val tButtons: Array<MojButton?> = arrayOfNulls<MojButton>(6) //tablica na klawisze
    var bActive: MojButton? = null;    //ktory klawisz kliknieto = jest aktualnie aktywny

    val zbLiczb = mutableSetOf<Int>()


    val lBts: Int = 6                  //liczba buttonow (z Ustawien)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calyEkran()
        //Set content view AFTER ABOVE sequence (calyEkran()) to avoid crash:
        setContentView(R.layout.activity_main)
        wygenerujButtony()
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


    private fun wygenerujButtony() {
        /* Generujemy lBts buttonow; zapamietujemy w tablicy tButtons[]; pokazujemy na ekranie */
        var mb: MojButton //robocza, dla wiekszej czytelnosci

        var dx = 0; //margin w pionie pomiedzy klawiszami
        //
        oszacujWysokoscButtonow_i_Tekstu()
        //
        zbLiczb.clear()
        var gen = -1;
        zbLiczb.add(gen)

        for (i in 0 until lBts) {

            while (zbLiczb.contains(gen)) {
                gen = (0..6).random();
            }
            zbLiczb.add(gen)

            mb = MojButton(this, gen, false, txSize, btH)
            mb.setOnClickListener(coNaKlikNaBtn)
            tButtons[i] = mb
            buttons_area.addView(tButtons[i])
            //Ustawienie marginesow miedzy buttonami (musi byc poza konstruktorem - klawisz musi fizyczne lezec na layoucie, inaczej nie dziala):
            val params: LinearLayout.LayoutParams
            params = tButtons[i]?.getLayoutParams() as LinearLayout.LayoutParams
            dx = 10;
            if (lBts < 4) dx = 20;
            params.setMargins(0, dx, 0, 0)
            tButtons[i]?.setLayoutParams(params)
            tButtons[i]?.setVisibility(View.VISIBLE) //za chwile pokaze z opoznieniem - efekciarstwo ;)

        }
    }  //koniec Funkcji


    var coNaKlikNaBtn = View.OnClickListener {
        /* Wypisanie liczby bądź kolek w polu tv_liczba i odpowiedniego stowarzyszonego stringa w tvCyfra*/
        /* Unieczynnienie innych klawiszy na chwile - dydaktyka.                                         */
        bActive = it as MojButton             //dla siebie i innych, system-wide
        if ((it as MojButton).czyJakLiczba) { //jezeli na klawiszu jest liczba, to w tv_cyfra wyswietlamy kolka
            var ts = (it as Button).textSize
            ts = (ts / 1.2).toFloat()
            tvCyfra.setTextSize(ts)
            tvCyfra.text = ""
            tvCyfra.text = (it as MojButton).dajWartoscJakoKolka()
        } else //jak na klawiszu kolka, to wyswietlamy cyfre
           tvCyfra.text = (it as MojButton).dajWartoscJakoCyfre()
        unieczynnijNaChwile(CHWILA, bActive!!)
        wyczyscPoChwili(CHWILA, tvCyfra)
    }

    private fun wyczyscPoChwili(chwila: Int, pole:TextView) {
    /* Czyszczenie tvCyfra po 'chwili' - efekty dydaktyczny */
        val mHandler = Handler()
        mHandler.postDelayed({pole.text=""},chwila.toLong())
    }

    private fun wygasTvCyfra() {
        tvCyfra.text=""
    }

    private fun unieczynnijNaChwile(chwila: Int, kl: Button) {
        /**
         * Na chwile unieczynnnia wszystkie klawisze OPROCZ 'kl'
         * Jeżeli były nieczynne, to nic sie nie zmieni.
         */
        val mHandler = Handler()
        for (b in tButtons) {
            if (b!=kl) {
                b?.isClickable = false
                b?.isEnabled = false
                mHandler.postDelayed({
                    b?.isClickable=true
                    b?.isEnabled=true
                },chwila.toLong())
            }
        }
    } //koniec Metody()


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
            6 -> (btH / 1.30).toInt()
            5 -> (btH / 1.25).toInt()
            else -> (btH / 1.20).toInt()
        }

    } //koniec Metody()

    fun bPrzelaczKlik(view: View) {
    //Zmina sposobu wyswietlania na buttonach
        tButtons.forEach {
            if (it != null) {
                it.czyJakLiczba = !it.czyJakLiczba
                it.text = it.dajWartoscStringowo()
            }
        wyczyscPoChwili(0,tvCyfra)
        }
    }


} //end of Class







