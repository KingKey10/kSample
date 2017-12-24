package newbox.co.jp.todoapplication.models

import io.realm.RealmObject

/**
 * Created by toshiba on 2017/11/25.
 */
open class TodoModel: RealmObject() {
    var title: String = ""
    // yyyy/MM/dd
    var deadLine: String =""
    var memo: String =""
    var doneFlg: Boolean = false

}