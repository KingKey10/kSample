package newbox.co.jp.todoapplication.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_edit.*
import newbox.co.jp.todoapplication.R
import newbox.co.jp.todoapplication.common.Consts
import newbox.co.jp.todoapplication.common.FragmentTag
import newbox.co.jp.todoapplication.common.IntentKeys
import newbox.co.jp.todoapplication.common.ModeInEdit
import newbox.co.jp.todoapplication.ui.fragment.DatePickerFragment
import newbox.co.jp.todoapplication.ui.fragment.EditFragment

class MainActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener,
                                        DatePickerFragment.OnDataSetListener{


    companion object {
        val TAG = this::class.java.name
    }

    /**
     * タブレットかどうか
     */
    private var isTablet = false


    /**
     * 何処からのコールバックか明示的にするために以下の在りかの記載を行う
     * ★重要★この部分はDatePickerFragmnet内で呼ばれ、その中で実行される
     * DatePickerFragment.OnDataSetListener#onDataSet
     */
    override fun onDateSelected(dateStr: String) {
        inputDateText.setText(dateStr)
    }

    /**
     * 何処からのコールバックか明示的にするために以下の在りかの記載を行う
     * ★重要★この部分はEditFragment内で呼ばれ、その中で実行される
     * EditFragment.OnFragmentInteractionListener#onDatePickerLaunched
     */
    override fun onDatePickerLaunched() {
        // ◆DatePickerFragment(自作)を開く処理実装
        DatePickerFragment().show(supportFragmentManager, FragmentTag.DATE_PICKER.toString())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // タブレット判定(もし以下の詳細画面を表示させるための箱があればそれはタブレット)
        // この表示させるより前に自動的にAndroid側がタブレット判定をし表示するレイアウトを選択してくれている
//        if (container_detail != null) isTablet = true
        // 上記の書き方ではなく下の【FindViewById】を使用しているのは、
        // そもそも読み込まれるXmlファイル（スマホorタブレット）のどっちかが状況に応じて変化するため、
        // もしidがcontain_detailがそもそも存在しなかった場合にとってくることもできずに
        // エラーになってしまうため以下のように記述する
        if (findViewById<FrameLayout>(R.id.container_detail) != null) isTablet = true



        // ◆Float Action Button (fab)を押下した際の挙動を以下に書く(新規登録)
        fab.setOnClickListener { view ->
//            // defaultでは以下のようにボタンを押下したら、下からToastみたいなViewが表示したい
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            goEditScreen("", "", "",
                    Consts.NOT_COMPLETED,ModeInEdit.NEW_ENTRY)
        }
    }

    /**
     * タブレット時➡ Fragmentの挿入
     * スマホ時➡ 編集画面に遷移
     */
    private fun goEditScreen(title: String, deadLine: String,
                             taskDetail: String, isCompleted: Boolean,
                             mode: ModeInEdit) {
        if (isTablet) {
//            // フラグメントを管理するクラスを召喚
//            val fm = supportFragmentManager
//            // fragmentの出し入れを管理するもの
//            // fragmentの入れ替えのときはこっちを使用する
//            val fTransaction = fm.beginTransaction()
//            // fragmentを当アクティビティの用意したコンテナー（framelayout）に格納する
//            fTransaction.add(R.id.container_detail, EditFragment.newInstance("1", "1"))
//            // これで確定
//            fTransaction.commit()

            // 上記を一行で書くと・・・(tagを使用した理由➡どの状態の時にFragment入れ替えなのか明示的にする目的)
            supportFragmentManager.beginTransaction()
                    .add(R.id.container_detail,
                            EditFragment.newInstance(title, deadLine, taskDetail,isCompleted, mode),
                            FragmentTag.EDIT.toString())
                    .commit()



            return
        }

        var intent = Intent(this@MainActivity, EditActivity::class.java).apply {
            putExtra(IntentKeys.TITLE.name, title)
            putExtra(IntentKeys.DEADLINE.name, deadLine)
            putExtra(IntentKeys.TASK_DETAIL.name, taskDetail)
            putExtra(IntentKeys.IS_COMPLETED.name, isCompleted)
            putExtra(IntentKeys.MODE.name, mode)
        }

        startActivity(intent)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // menuInflaterはActivityが保持しているクラス
        // BasicActicityを選択するとdefaultで【menu_main.xml】が作成される
        menuInflater.inflate(R.menu.menu_main, menu)

        // ◆非表示にしたいオプションメニューを以下のようにしてしてやる
//        menu.findItem(R.id.menu_delete).isVisible = false
//        menu.findItem(R.id.menu_edit).isVisible = false
//        menu.findItem(R.id.menu_registration).isVisible = false
        //◆以下のように書き換え可能
        // ◆Fragmentサイドで表示/非表示は制御するとし、ここではあくまで生成のみ(EditActivityも同様)
        menu.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_registration).isVisible = false
        }

        // オプションメニューを表示する場合は【true】
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}
