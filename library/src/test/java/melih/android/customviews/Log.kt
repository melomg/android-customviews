package melih.android.customviews

object Log {

    fun e(tag: String, msg: String): Int {
        println("ERROR: $tag: $msg")
        return 0
    }
}
