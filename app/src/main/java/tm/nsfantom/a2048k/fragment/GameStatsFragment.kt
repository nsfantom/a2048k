package tm.nsfantom.a2048k.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tm.nsfantom.a2048k.AppController
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.util.Config
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_stats.*
import tm.nsfantom.a2048k.model.StatItem
import tm.nsfantom.a2048k.ui.SimpleArrayAdapter


/**
 * Created by user on 2/13/18.
 */

class GameStatsFragment : Fragment() {
    private lateinit var listener: Listener

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack.setOnClickListener { listener.onBackToMain() }
        val values: ArrayList<StatItem> = (activity.application as AppController).prefStorage.getResults()
        val adapter = SimpleArrayAdapter(context, values)
        (lvResults as ListView).adapter = adapter
    }

    companion object {
        internal fun newInstance(): GameStatsFragment {
            return GameStatsFragment()
        }
    }
}
