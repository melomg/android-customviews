package melih.android.customviews

import android.util.Log
import java.text.DecimalFormat

internal fun formatCurrencyAmount(
    decimalFormat: DecimalFormat,
    amount: String,
    fractionDivider: Char
): String = try {
    val formatted = amount.replace("[^0-9${fractionDivider}]".toRegex(), "")

    val shouldAddZero = formatted.takeLast(2) == "${fractionDivider}0"

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

private fun hasTextMoreThanOneFractionDivider(
    replacedText: String,
    fractionDivider: Char
) = replacedText.count { it == fractionDivider } > 1
