package newbox.co.jp.todoapplication.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import newbox.co.jp.todoapplication.common.DateUtil
import java.util.*

/**
 * Created by toshiba on 2017/12/23.
 */
class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener  {

    /**
     * 当クラスIFを継承しているActivityの格納先
     */
    var mListener:OnDataSetListener? = null


    interface OnDataSetListener{
        fun onDateSelected(dateStr: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDataSetListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "当IFの実装をしてください")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener  = null
    }

    /**
     * カレンダー表示時の初期表示日付の設定をして表示
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        ◆◆(Java.utilのカレンダーを使用すること)◆◆
        val calendar = Calendar.getInstance() // ◆現在の日時データの取得
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // ◆activity( getActivity() )=接続するactivity、
        // ◆this＝このクラス（DatePickerFragment）
        // ◆year=day＝初期表示日付のデータ
        return DatePickerDialog(activity, this, year, month, day)
    }

    /**
     * ユーザーが選択し終わったタイミングで呼ばれる
     * そのときの選択値が引数として入りメソッド内の処理を実行
     */
    override fun onDateSet(datepicker: DatePicker?, year: Int, month: Int, day: Int) {
       val deadLineDateStr: String =  DateUtil.getDateToString(year, month, day)
        // このFragmentに接続しているActivity内に記載されたonDateSelected()が実行される
        mListener?.onDateSelected(deadLineDateStr)

        // 処理を渡したらこのFragmentは閉じる
        // ★supportFragmentManagerはあくまでActivityが使用するもので、Fragment呼び出し時に使用するもの
        // ★閉じるときはsupport関係なく【fragmentManager】を使用すること
        fragmentManager.beginTransaction().remove(this).commit() // これなくてもとじるけど・・・
    }
}