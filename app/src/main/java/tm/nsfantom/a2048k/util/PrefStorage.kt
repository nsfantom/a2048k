package tm.nsfantom.a2048k.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tm.nsfantom.a2048k.model.StatItem


class PrefStorage(private val context: Context) {
    private val KEY_BEST_SCORE = "bestScore"
    private val KEY_CELL_COUNT = "cellCount"
    private val KEY_STATS = "statistics"

    private var sharedPreferences: SharedPreferences? = null

    fun getBestScore(): Int {
        return prefs.getInt(KEY_BEST_SCORE, 0)
    }

    fun getCellCount(): Int {
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

    fun saveCellCount(count: Int) {
        prefs.edit().putInt(KEY_CELL_COUNT, count).apply()
    }

    fun saveResults(record: StatItem) {
        val records = ArrayList<StatItem>()
        records.addAll(getResults())
        records.add(record)
        records.sortBy { e -> e.date }
        records.sortByDescending { e -> e.result }
        val json = Gson().toJson(records.take(10))
        prefs.edit().putString(KEY_STATS, json).apply()
    }

    fun getResults(): ArrayList<StatItem> {
        val json = prefs.getString(KEY_STATS, "")
        val founderListType = object : TypeToken<ArrayList<StatItem>>() {}.type
        if (json.isEmpty()) return ArrayList()
        return Gson().fromJson(json, founderListType)
    }
}
