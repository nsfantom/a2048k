package tm.nsfantom.a2048k.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_stats.*
import tm.nsfantom.a2048k.AppController
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.model.StatItem
import tm.nsfantom.a2048k.ui.SimpleArrayAdapter

class GameStatsFragment : Fragment() {
    private lateinit var listener: Listener
    private var direction = Direction.SCORE_UP

    interface Listener {
        fun onBackToMain()
    }

    override fun onAttach(context: Context?) {
        if (activity !is Listener) {
            throw IllegalStateException("Activity must implement fragment Listener.")
        }
        super.onAttach(context)
        listener = activity as Listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack.setOnClickListener { listener.onBackToMain() }
        val values: ArrayList<StatItem> = (activity?.application as AppController).prefStorage.getResults()
        values.sortByDescending { e -> e.result }
        for (i in 0 until values.size)
            values[i].rank = i + 1
        val adapter = SimpleArrayAdapter(context!!, values)
        (lvResults as ListView).adapter = adapter
        btnResult.setOnClickListener({
            if (direction == Direction.SCORE_UP)
                adapter.updateData(sortByDirection(Direction.SCORE_DOWN, values))
            else adapter.updateData(sortByDirection(Direction.SCORE_UP, values))
        })
        btnDate.setOnClickListener({
            if (direction == Direction.DATE_UP)
                adapter.updateData(sortByDirection(Direction.DATE_DOWN, values))
            else adapter.updateData(sortByDirection(Direction.DATE_UP, values))
        })
    }

    companion object {
        internal fun newInstance(): GameStatsFragment {
            return GameStatsFragment()
        }
    }

    private fun sortByDirection(direction: Direction, values: ArrayList<StatItem>): ArrayList<StatItem> {
        when (direction) {
            Direction.SCORE_UP -> {
                values.sortBy { e -> e.date }
                values.sortByDescending { e -> e.result }
            }
            Direction.SCORE_DOWN -> values.sortBy { e -> e.result }
            Direction.DATE_UP -> values.sortByDescending { e -> e.date }
            Direction.DATE_DOWN -> values.sortBy { e -> e.date }
        }
        this.direction = direction
        return values
    }

    enum class Direction {
        SCORE_UP, SCORE_DOWN, DATE_UP, DATE_DOWN
    }
}
