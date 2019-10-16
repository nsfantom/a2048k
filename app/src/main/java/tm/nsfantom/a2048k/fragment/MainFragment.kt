package tm.nsfantom.a2048k.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import tm.nsfantom.a2048k.R

class MainFragment : Fragment() {
    private lateinit var listener: Listener

    interface Listener {
        fun onStartClicked()
        fun onSettingsClicked()
        fun onStatsClicked()
    }

    override fun onAttach(context: Context?) {
        if (activity !is Listener) {
            throw IllegalStateException("Activity must implement fragment Listener.")
        }
        super.onAttach(context)
        listener = activity as Listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStart.setOnClickListener({ listener.onStartClicked() })
        btnSettings.setOnClickListener({listener.onSettingsClicked()})
        btnStats.setOnClickListener({listener.onStatsClicked()})
    }

    companion object {
        internal fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
