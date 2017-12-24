package newbox.co.jp.todoapplication

import android.app.Application
import io.realm.Realm

/**
 * Created by toshiba on 2017/11/25.
 */
class AppConfig: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}