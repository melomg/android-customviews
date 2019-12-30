package melih.android.customviews

import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.widget.EditText
import androidx.annotation.IntRange
import androidx.appcompat.view.ContextThemeWrapper
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class PriceEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EditText(ContextThemeWrapper(context, R.style.Price_Amount), attrs, defStyleAttr) {

    private var locale: Locale = Locale.getDefault()
    internal var decimalFormat: DecimalFormat = getDecimalFormat(locale)
    internal var maxLength: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PriceEditText)
        maxLength = a.getInt(R.styleable.PriceEditText_android_maxLength, DEFAULT_MAX_LENGTH)
        a.recycle()

        addTextChangedListener(PriceTextWatcher(this))
    }

    fun setLocale(locale: Locale) {
        decimalFormat = getDecimalFormat(locale)
    }

    fun setMaxLength(@IntRange(from = 2) maxLength: Int) {
        this.maxLength = maxLength
        filters = arrayOf<InputFilter>(LengthFilter(maxLength))
    }

    internal fun getFractionDivider(): Char =
        decimalFormat.decimalFormatSymbols.monetaryDecimalSeparator

    internal fun getDecimalFormatMaxFractionDigits(): Int = decimalFormat.maximumFractionDigits

    private fun getDecimalFormat(locale: Locale): DecimalFormat =
        DecimalFormat(PRICE_FORMAT, DecimalFormatSymbols.getInstance(locale)).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }

    companion object {
        private const val DEFAULT_MAX_LENGTH: Int = 13

        private const val PRICE_FORMAT: String = "#,##0.00"
    }

}
