package melih.android.customviews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        price.setLocale(Locale("tr", "TR"))

        price.setText("12.000,50")
        Log.d(MainActivity::class.java.simpleName, price.priceValue.toString())

        price.priceValue = BigDecimal.valueOf(12000.50)
        Log.d(MainActivity::class.java.simpleName, price.priceValue.toString())
    }
}
