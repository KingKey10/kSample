package newbox.co.jp.todoapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.fragment_edit.*
import newbox.co.jp.todoapplication.R
import newbox.co.jp.todoapplication.common.FragmentTag
import newbox.co.jp.todoapplication.common.IntentKeys
import newbox.co.jp.todoapplication.common.ModeInEdit
import newbox.co.jp.todoapplication.ui.fragment.DatePickerFragment
import newbox.co.jp.todoapplication.ui.fragment.EditFragment

class EditActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener,
                        DatePickerFragment.OnDataSetListener{

    /**
     * 何処からのコールバックか明示的にするために以下の在りかの記載を行う
     * ★重要★この部分はDatePickerFragmnet内で呼ばれ、その中で実行される
     * DatePickerFragment.OnDataSetListener#onDateSelected
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
        // DatePickerFragmentを開く処理
        DatePickerFragment().show(supportFragmentManager, FragmentTag.DATE_PICKER.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        // 戻るボタン処理
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp) // ICONの設定
            setNavigationOnClickListener { //戻るボタン押下時処理
                finish() // 画面の終了
            }
        }
        // このアプリの仕様上、MainActivityからこのActivityへスマホ版のみは遷移してくるため、
        // そのときMainActivityから渡される値を受け取る必要がある
        val bundle = intent.extras
        val title = bundle.getString(IntentKeys.TITLE.name)
        val taskDetail = bundle.getString(IntentKeys.TASK_DETAIL.name)
        val deadLine = bundle.getString(IntentKeys.DEADLINE.name)
        val isCompleted = bundle.getBoolean(IntentKeys.IS_COMPLETED.name)
        val mode = bundle.getSerializable(IntentKeys.MODE.name) as ModeInEdit

        // 上記受け取りをFragmentを生成しそれに値を渡す
        supportFragmentManager.beginTransaction()
                .add(R.id.container_detail, EditFragment.newInstance(title, deadLine, taskDetail, isCompleted, mode),
                        FragmentTag.EDIT.toString())
                //tagを使用した理由➡どの状態の時にFragment入れ替えなのか明示的にする目的)
                .commit()

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 実際のメニューICONの表示/ 非表示処理はFragment側で行うが、
        // メニューの組み立て（inflate）はActivityで行う必要がある
        menuInflater.inflate(R.menu.menu_main, menu)
        menu!!.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_registration).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
        }

        //メニューボタン表示(fragment側で何を表示させるか指定をするため、
        // メニュー自体は表示すると（true）とする必要がある)
        return true
    }





}
