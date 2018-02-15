package tm.nsfantom.a2048k

import android.app.Application
import timber.log.Timber
import tm.nsfantom.a2048k.util.PrefStorage

/**
 * Created by user on 2/13/18.
 */

class AppController : Application() {

    val prefStorage: PrefStorage by lazy {
        PrefStorage(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
