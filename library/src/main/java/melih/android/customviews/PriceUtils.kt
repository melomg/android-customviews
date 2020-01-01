package melih.android.customviews

import android.util.Log
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

internal const val DEFAULT_MAX_LENGTH: Int = 13
internal const val PRICE_FORMAT: String = "#,##0.00"

internal fun formatPriceText(
    text: String,
    decimalFormat: DecimalFormat,
    fractionDivider: Char
): String = try {
    val shouldAddZero = text.takeLast(2) == "${fractionDivider}0"

    val formatted = text.replace("[^0-9${fractionDivider}]".toRegex(), "")

    if (formatted.isEmpty()) {
        ""
    } else {
        var decimalFormatted =
            decimalFormat.format(formatted.replace(fractionDivider, '.').toDouble())

        if (shouldAddZero) {
            decimalFormatted = decimalFormatted.plus("${fractionDivider}0")
        }

        decimalFormatted
    }
} catch (e: NumberFormatException) {
    Log.e("PriceUtils", e.message, e)
    ""
}

internal fun formatPriceValueFromTextToBigDecimal(
    text: String,
    fractionDivider: Char
): BigDecimal {
    val formatted = text.replace("[^0-9${fractionDivider}]".toRegex(), "")
        .replace(fractionDivider, '.')
    return if (formatted.isEmpty()) {
        BigDecimal.ZERO
    } else {
        BigDecimal.valueOf(formatted.toDouble())
    }
}

internal fun formatPriceValueFromBigDecimalToText(
    value: BigDecimal,
    decimalFormat: DecimalFormat,
    maxLength: Int
): String {
    val formattedText = decimalFormat.format(value.toDouble())
    if (formattedText.length > maxLength) {
        throw MaxLengthExceededException()
    }
    return formattedText
}

internal fun replaceLastDotWithFractionDivider(text: String, fractionDivider: Char): String {
    var replacedText = text

    if (text.isNotEmpty() && text.last() == '.') {
        replacedText = text.dropLast(1).plus(fractionDivider)
        if (hasTextMoreThanOneFractionDivider(replacedText, fractionDivider)) {
            replacedText = text.dropLast(1)
        }
    }

    return replacedText
}

internal fun getDecimalFormat(locale: Locale): DecimalFormat =
    DecimalFormat(PRICE_FORMAT, DecimalFormatSymbols.getInstance(locale)).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }

private fun hasTextMoreThanOneFractionDivider(
    replacedText: String,
    fractionDivider: Char
) = replacedText.count { it == fractionDivider } > 1
