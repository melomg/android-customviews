package melih.android.customviews

import android.text.Editable
import android.text.TextWatcher
import kotlin.math.min

internal class PriceTextWatcher(private val editText: PriceEditText) : TextWatcher {

    private var current: String? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // no-op
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var text = s.toString()
        if (text != current) {
            val fractionDivider = editText.getFractionDivider()

            text = replaceLastDotWithFractionDivider(text, fractionDivider)

            if (!text.contains(fractionDivider)
                || doesNotExceedMaxFractionDigitsLimit(text, fractionDivider, count)
            ) {
                editText.removeTextChangedListener(this)

                current = formatCurrencyAmount(editText.decimalFormat, text, fractionDivider)

                if (isNewCharAFractionDivider(text, fractionDivider)) {
                    current = current.plus(fractionDivider)
                }

                if (current.isNullOrEmpty() || current == "0") {
                    editText.text = null
                    current = null
                } else {
                    val cursorPositionFromEnd = text.length - editText.selectionStart
                    editText.setText(current)
                    setSelection(current!!, cursorPositionFromEnd)
                }

                editText.addTextChangedListener(this)
            } else {
                current = text.dropLast(1)
                editText.setText(current)
                editText.setSelection(min(current!!.length, editText.maxLength))
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        // no-op
    }

    private fun doesNotExceedMaxFractionDigitsLimit(
        text: String,
        fractionDivider: Char,
        newAddedCharCount: Int
    ) =
        text.indexOf(fractionDivider) != (text.lastIndex - editText.getDecimalFormatMaxFractionDigits() - newAddedCharCount)

    private fun isNewCharAFractionDivider(
        text: String,
        fractionDivider: Char
    ) = text.count { it == fractionDivider } == 1 && text.last() == fractionDivider

    private fun setSelection(currentText: String, cursorPositionFromEnd: Int) {
        var selection = currentText.length - cursorPositionFromEnd

        if (selection < 0) {
            selection = 0
        } else {
            val maxValidLengthOfSelection = min(currentText.length, editText.maxLength)
            if (selection > maxValidLengthOfSelection) {
                selection = maxValidLengthOfSelection
            }
        }
        editText.setSelection(selection)
    }
}
