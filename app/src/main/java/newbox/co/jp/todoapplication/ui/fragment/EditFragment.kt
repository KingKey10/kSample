package newbox.co.jp.todoapplication.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_edit.*
import newbox.co.jp.todoapplication.R
import newbox.co.jp.todoapplication.common.IntentKeys
import newbox.co.jp.todoapplication.common.ModeInEdit
import newbox.co.jp.todoapplication.models.TodoModel


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var title: String? = ""
    private var deadLine: String? = ""
    private var taskDetail:String? = ""
    private var isCompleted:Boolean = false
    private var mode:ModeInEdit? = null

    private var mListener: OnFragmentInteractionListener? = null
    /**
     * 当クラスの【newInstance()】の引数で受け取り格納した値を取り出す処理
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ◆下の【argments】はソース下方にある【newInstance()】内のargsのこと
        // ◆【以下Javaで書くと。。。】
        // Bundle bundle = getArguments();
//        String url = bundle.getString("KEY_NAME ");
        if (arguments != null) {
            title = arguments.getString(ARG_TITLE)
            deadLine = arguments.getString(ARG_DEADLINE)
            taskDetail = arguments.getString(ARG_TASK_DETAIL)
            isCompleted = arguments.getBoolean(ARG_IS_COMPLETED)
            mode = arguments.getSerializable(ARG_MODE) as ModeInEdit
        }
    }

    /**
     * FragmentのViewの生成完了時
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_edit, container, false)
        // ◆AttachするActivityがオプションメニューを持っていたら受け取る
        setHasOptionsMenu(true)
        return view
    }


    /**
     * onCreateViewにてAttachされるActivityのオプションメニューを受け取るように設定したため、
     * ここで表示・非表示の処理を加えてやる
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        // 編集画面では完了ボタンのみが表示されていればいい
        menu!!.apply {
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_registration).isVisible = true
            findItem(R.id.menu_delete).isVisible = false
        }
    }

    /**
     * オプションメニューボタン選択時の処理
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // optionMENUのチェックボタン押下時の処理を記載
        // オプションメニューの【完了ボタン】押下時、DB登録処理を記載する
        if(item!!.itemId == R.id.menu_registration) recodeToDB(mode!!) // 編集モードか、新規モードによって処理を分けるメソッド

        return super.onOptionsItemSelected(item)
    }

    /**
     * 入力チェック
     * 編集モード➡DB UPDATE処理
     * 新規モード➡DB Insert処理
     */
    private fun recodeToDB(mode: ModeInEdit) {
        // 入力チェック
        if (!isChkedInput()) return
        // 登録分岐
        when(mode) {
            ModeInEdit.EDIT -> updateTodo() // 更新処理
            ModeInEdit.NEW_ENTRY -> insertTodo() // 新規登録
        }

    }

    /**
     * 入力チェック処理
     */
    private fun isChkedInput(): Boolean {

        // タイトル部未入力時、エラー表示
        if (inputTitleText.text.toString() == ""){
            rapInputTitleText.error = getText(R.string.error)
            return  NOT_CHECK_OK
        }
        // 期限部未入力時エラー表示
        if (inputDateText.text.toString() == "") {
            rapInputDateText.error = getText(R.string.error)
            return NOT_CHECK_OK
        }
        rapInputTitleText.error = null
        rapInputDateText.error = null

        return IS_CHECK_OK
    }

    /**
     * Todo更新処理
     */
    private fun updateTodo() {

    }

    /**
     * ToDo新規登録処理
     */
    private fun insertTodo() {
        Realm.getDefaultInstance().apply {
            beginTransaction()
            createObject(TodoModel::class.java).apply {
                title = inputTitleText.text.toString()
                deadLine = inputDateText.text.toString()
                taskDetail = inputDetailText.text.toString()
                isCompleted = checkBox.isChecked // チェックがあればTrue、なければfalseが返却
            }
            commitTransaction()
        }
//        val realm = Realm.getDefaultInstance()
//        realm.beginTransaction()
//        realm.createObject(TodoModel::class.java).apply {
//            title = inputTitleText.text.toString()
//            deadLine = inputDateText.text.toString()
//            taskDetail = inputDetailText.text.toString()
//            isCompleted = checkBox.isChecked // チェックがあればTrue、なければfalseが返却
//        }
//        realm.commitTransaction()
    }

    /**
     * ActivityのView・FragmentのViewの両方の生成が終わっている状態
     * Viewへの操作はここで行うべき！！
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 新規か編集モードに応じて、チェックボックスの出しわけをする
        updateUI(mode!!)
        // カレンダーボタン押下時➡Activity側でDatepickerFragment(自作)をひらいてもらう
        // Fragmentから他Fragmentの操作は行ってはいけない
        // ★（Fragmentは使いまわしのため、疎結合に保つ必要があるため）
        // Fragment同士が呼び出し合うと密結合になり、常に一緒でないといけなくなる（密結合）それはNG！！
        imageButtonDate.setOnClickListener {
            mListener!!.onDatePickerLaunched()
        }
    }

    /**
     * 自動生成メソッド（Activityや他のFragmetとやり取りをするためのもの
     * 下のonAttach, onDettachでActivityと必要な時
     * くっついたり離れたりできる構造にFragmentはなっている
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onDatePickerLaunched()
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * 新規投稿か編集モードかで描画内容変更処理
     */
    private fun updateUI(mode: ModeInEdit) {
        when(mode) {
            ModeInEdit.NEW_ENTRY ->{
                // 新規EDIT時には完了チェックBOXは表示させない
                checkBox.visibility = View.INVISIBLE
            }
            ModeInEdit.EDIT -> {
                // 編集時には完了チェックBOXを表示させる
                checkBox.visibility = View.VISIBLE
            }
        }

    }

//
//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        if (mListener != null) {
//            mListener!!.onFragmentInteraction(uri)
//        }
//    }


    /**
     * companion objectの解説
     *  【特徴】
     *  ●シングルトン
     *  ●クラスの中でのみ生成できるもの(外部でできるのはObject)
     *
     *  ➡private val ＝ javaでいう【private static final】と同意
     *      ➡ このクラスが何個Instanceが生成されようと一つのみ存在しこのクラスでしか参照できないもの
     *
     *  ➡ fun＝この中で何も修飾子をつけずに作成したメソッドは他クラスから
     *      【クラス名.メソッド名】で呼び出し可能
     *
     *      ➡つまりjavaの【public static ～】と同意
     */
    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_TITLE = IntentKeys.TITLE.name
        private val ARG_DEADLINE = IntentKeys.DEADLINE.name
        private val ARG_TASK_DETAIL = IntentKeys.TASK_DETAIL.name
        private val ARG_IS_COMPLETED = IntentKeys.IS_COMPLETED.name
        private val ARG_MODE = IntentKeys.MODE.name

        // 完了フラグ
        private val IS_CHECK_OK = true
        private val NOT_CHECK_OK = false

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: ここの引数でインスタンス化した際に確実に値を洩れることなく渡すようにしている

        // ◆Budleに値を渡すようにすることで画面が何らかの理由で落ちてもこの値は守られ、再描画できるようになる
        // ※➡再描画する際、Fragmentは引数なしのコンストラクタを呼ぶ、、
        //      ＝コンストラクタの引数で値を渡していたら（このとき引数なしのも明示的に書く）、
        //          再描画時に値はないものとみなされ反映されない
        //      ＝引数をBundleに確実に渡すためにもnewInstanceなどの独自メソッドを実装し、そこで値を渡すのがいい

        // ※➡Intentで渡す手法もあるが、この方法は以下の点において、やるべきでない
        //      ◆Intent用のKey名を渡すたびに書かなくてはいけない
//                    ➡ newInstanceの場合、引数で渡すため意識しなくてよい
        //      ◆渡す側のクラスを確認し、渡すデータの型の意識もしなくてはいけない
        //            ➡ newInstanceの場合、引数に型指定をしている為、渡す側は値の型を意識しなくても良くなる
        //      ◆値の渡し洩れがあってもエラーにならず気が付きにくい、バグの温床
        //            ➡ newInstanceの場合、引数で足りなければエラーがすぐに書く確認できるのでバグを防ぐことができる

        fun newInstance(title: String, deadLine: String, taskDetail: String,
                        isCompleted: Boolean, mode: ModeInEdit): EditFragment {
            val fragment = EditFragment()
            // ◆【args】を上ソースの【onCreate（）】のargmentsで使用している
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DEADLINE, deadLine)
            args.putString(ARG_TASK_DETAIL, taskDetail)
            args.putBoolean(ARG_IS_COMPLETED, isCompleted)
            args.putSerializable(ARG_MODE, mode)

            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
