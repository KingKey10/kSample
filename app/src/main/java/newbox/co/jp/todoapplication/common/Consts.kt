package newbox.co.jp.todoapplication.common

import android.content.Context
import android.widget.Toast

/**
 * Created by toshiba on 2017/11/25.
 */
object Consts {
    const val IS_COMPLETED= true
    const val NOT_COMPLETED = false

    const val Fuga: Int = 2
    const val Piyo: Int = 3

    /**
     * 表示時間の短いトーストを表示する処理
     */
    fun createShortToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 表示時間の長いトーストを表示する処理
     */
    fun createLongToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }


}