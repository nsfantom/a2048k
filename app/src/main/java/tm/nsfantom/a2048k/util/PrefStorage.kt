package tm.nsfantom.a2048k.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by nsfantom on 1/3/18.
 */

class PrefStorage(private val context: Context) {
    private val KEY_BEST_SCORE = "bestScore"
    private val KEY_CELL_COUNT = "cellCount"

    private var sharedPreferences: SharedPreferences? = null

    fun getBestScore(): Int {
        return prefs.getInt(KEY_BEST_SCORE, 0)
    }

    fun getCellCount():Int {
        return prefs.getInt(KEY_CELL_COUNT, 4)
    }


    private val prefs: SharedPreferences
        get() {
            if (sharedPreferences == null)
                sharedPreferences = context.getSharedPreferences(PrefStorage::class.java.simpleName, Context.MODE_PRIVATE)
            return sharedPreferences!!
        }

    fun saveBestScore(bestScore: Int) {
        prefs.edit().putInt(KEY_BEST_SCORE, bestScore).apply()
    }

    fun saveCellCount(count: Int){
        prefs.edit().putInt(KEY_CELL_COUNT,count).apply()
    }
}
