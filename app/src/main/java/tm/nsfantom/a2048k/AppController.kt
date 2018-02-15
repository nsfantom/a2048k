package tm.nsfantom.a2048k

import android.app.Application
import timber.log.Timber

/**
 * Created by user on 2/13/18.
 */

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
