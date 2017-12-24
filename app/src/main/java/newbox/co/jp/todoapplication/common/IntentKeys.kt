package newbox.co.jp.todoapplication.common

/**
 * Created by toshiba on 2017/12/07.
 */

enum class IntentKeys {
    TITLE, DEADLINE, TASK_DETAIL, IS_COMPLETED, MODE
}

/**
 * 新規作成か編集かの判定用FLG
 */
enum class ModeInEdit {
    NEW_ENTRY, EDIT
}

/**
 * Fragment入れ替えの際にその箱に入っているのは何か(どの状態か)を明示的にしたFLG
 */
enum class FragmentTag {
    MASTER, DETAIL, EDIT, DATE_PICKER
}
