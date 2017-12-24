package newbox.co.jp.todoapplication.common

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by toshiba on 2017/12/24.
 */
object DateUtil {
    /**
     * String型日付への変換
     */
    fun getDateToString(year:Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        return SimpleDateFormat("yyyy/MM/dd").format(calendar.time)
    }
}