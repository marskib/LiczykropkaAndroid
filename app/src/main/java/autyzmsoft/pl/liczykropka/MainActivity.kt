package autyzmsoft.pl.liczykropka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val btns : Array<Button?> = arrayOfNulls<Button>(6) //tablica na klawisze

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() //ukrywam pasek z nazwa aplikacji (miejsce!)

        ustawListenerNaTVCyfra()

        ustawListeneryNaKlawisze()
    }

    private fun ustawListenerNaTVCyfra() {
        tv_cyfra.setOnClickListener {
            try {
                var wart = tv_cyfra.text.toString().toInt()
                wart++;
                tv_cyfra.text = wart.toString();
            } catch (e:Exception) {
                tv_cyfra.text = "0";
            }
        }
    }

    private fun ustawListeneryNaKlawisze() {
        //tabs1 = arrayOf(b1, b2, b3, b4, b5, b6)
        btns[0]=b1;
        btns[1]=b2;
        btns[2]=b3;
        btns[3]=b4;
        btns[4]=b5;
        btns[5]=b6;
        for (b in btns) b?.setOnClickListener {tv_cyfra.text = b.tag.toString()}
    }



}
