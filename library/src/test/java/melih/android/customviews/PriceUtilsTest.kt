package melih.android.customviews

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

class PriceUtilsTest {
    private lateinit var decimalFormat: DecimalFormat
    private val fractionDivider: Char by lazy {
        decimalFormat.decimalFormatSymbols.monetaryDecimalSeparator
    }

    @Before
    fun setUp() {
        decimalFormat = getDecimalFormat(Locale.getDefault())
    }

    @Test
    fun `formatPriceText formats correctly when defaultLocale given`() {
        val inputText = "12,480,5000"
        val formattedText = formatPriceText(inputText, decimalFormat, fractionDivider)

        Assert.assertEquals("124,805,000", formattedText)
    }

    @Test
    fun `formatPriceText formats correctly when fraction divider entered`() {
        val inputText = "12,480,500."
        val formattedText = formatPriceText(inputText, decimalFormat, fractionDivider)

        Assert.assertEquals("12,480,500", formattedText)
    }

    @Test
    fun `formatPriceText does'nt remove zero first digit in fraction part`() {
        val inputText = "12,480,500.0"
        val formattedText = formatPriceText(inputText, decimalFormat, fractionDivider)

        Assert.assertEquals(inputText, formattedText)
    }

    @Test
    fun `formatPriceText formats correctly when TRLocale given`() {
        decimalFormat = getDecimalFormat(Locale("tr", "TR"))
        val inputText = "12.480.5000"
        val formattedText = formatPriceText(inputText, decimalFormat, fractionDivider)

        Assert.assertEquals("124.805.000", formattedText)
    }

    @Test
    fun `formatPriceText formats correctly when fraction divider entered and TRLocale given`() {
        decimalFormat = getDecimalFormat(Locale("tr", "TR"))
        val inputText = "12.480.500."
        val formattedText = formatPriceText(inputText, decimalFormat, fractionDivider)

        Assert.assertEquals("12.480.500", formattedText)
    }

    @Test
    fun `formatPriceValueFromTextToBigDecimal formats correctly when defaultLocale given`() {
        val inputText = "12,480,500.01"
        val priceValue = formatPriceValueFromTextToBigDecimal(inputText, fractionDivider)

        Assert.assertEquals(BigDecimal.valueOf(12480500.01), priceValue)
    }

    @Test
    fun `formatPriceValueFromTextToBigDecimal formats correctly when TRLocale given`() {
        decimalFormat = getDecimalFormat(Locale("tr", "TR"))
        val inputText = "12.480.500,01"
        val priceValue = formatPriceValueFromTextToBigDecimal(inputText, fractionDivider)

        Assert.assertEquals(BigDecimal.valueOf(12480500.01), priceValue)
    }

    @Test
    fun `formatPriceValueFromBigDecimalToText formats correctly when defaultLocale given`() {
        val priceValue = BigDecimal.valueOf(12480500.01)
        val formattedPriceValue =
            formatPriceValueFromBigDecimalToText(priceValue, decimalFormat, DEFAULT_MAX_LENGTH)

        Assert.assertEquals("12,480,500.01", formattedPriceValue)
    }

    @Test
    fun `formatPriceValueFromBigDecimalToText formats correctly when TRLocale given`() {
        decimalFormat = getDecimalFormat(Locale("tr", "TR"))
        val priceValue = BigDecimal.valueOf(12480500.01)
        val formattedPriceValue =
            formatPriceValueFromBigDecimalToText(priceValue, decimalFormat, DEFAULT_MAX_LENGTH)

        Assert.assertEquals("12.480.500,01", formattedPriceValue)
    }

    @Test(expected = MaxLengthExceededException::class)
    fun `formatPriceValueFromBigDecimalToText throws exception when max length exceeded`() {
        val priceValue = BigDecimal.valueOf(512480500.01)
        formatPriceValueFromBigDecimalToText(priceValue, decimalFormat, DEFAULT_MAX_LENGTH)
    }

    @Test
    fun `replaceLastDotWithFractionDivider succeeds when defaultLocale given`() {
        val text = "12,480,500."
        val formatted = replaceLastDotWithFractionDivider(text, fractionDivider)
        Assert.assertEquals("12,480,500.", formatted)
    }

    @Test
    fun `replaceLastDotWithFractionDivider succeeds when TRLocale given`() {
        decimalFormat = getDecimalFormat(Locale("tr", "TR"))
        val text = "12.480.500."
        val formatted = replaceLastDotWithFractionDivider(text, fractionDivider)
        Assert.assertEquals("12.480.500,", formatted)
    }

    @Test
    fun `replaceLastDotWithFractionDivider succeeds when more than one fraction divider given`() {
        val text = "12,480,500.."
        val formatted = replaceLastDotWithFractionDivider(text, fractionDivider)
        Assert.assertEquals("12,480,500.", formatted)
    }

}
