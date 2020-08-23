package melih.android.customviews

import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import androidx.annotation.IntRange
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatEditText
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

var decimalFormat: DecimalFormat = getDecimalFormat(Locale.getDefault())
    private set

class PriceEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(ContextThemeWrapper(context, R.style.Price_Value), attrs, defStyleAttr) {

    internal var maxLength: Int

    var priceValue: BigDecimal = BigDecimal.ZERO
        get() = formatPriceValueFromTextToBigDecimal(
            text.toString(),
            getFractionDivider()
        )
        set(value) {
            field = value
            setText(formatPriceValueFromBigDecimalToText(value, decimalFormat, maxLength))
        }

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

}

class MaxLengthExceededException : RuntimeException("Max length of PriceEditText is exceeded.")
