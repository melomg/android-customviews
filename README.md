# android-customviews

Collection of custom views I use in my Android apps.

Work in progress 🏗

## PriceEditText

- If you would like to change `maxLength`, you can do it from layout attribute, or programmatically set it (Default `maxLength` is 13):

From layout:

```xml
<melih.android.customviews.PriceEditText
        android:id="@+id/price"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:maxLength="13"
        android:layout_margin="16dp"
        app:layout_constraintBottom_toBottomOf="parent" />
```

Programmatically:

`price.maxLength = 13`

- You can also set and get price value from PriceEditText:

`price.priceValue = BigDecimal.valueOf(12000.50)`

- DecimalFormat formats number by default locale unless you change it. You can change locale by setting it programmatically:

`price.setLocale(Locale("tr", "TR"))`

Changing locale will change group and fraction separator of DecimalFormat. 

