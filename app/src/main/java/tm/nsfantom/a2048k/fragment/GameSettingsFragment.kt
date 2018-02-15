package tm.nsfantom.a2048k.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_gamesettings.*
import tm.nsfantom.a2048k.AppController
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.util.Config

/**
 * Created by user on 2/13/18.
 */

class GameSettingsFragment : Fragment() {
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
        return inflater!!.inflate(R.layout.fragment_gamesettings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sizeSelector.minValue=3
        sizeSelector.maxValue=6
        sizeSelector.value = (activity.application as AppController).prefStorage.getCellCount()
        btnBack.setOnClickListener {
            (activity.application as AppController).prefStorage.saveCellCount(sizeSelector.value)
            Config.CELL_COUNT = sizeSelector.value
            listener.onBackToMain() }
    }

    companion object {
        internal fun newInstance(): GameSettingsFragment {
            return GameSettingsFragment()
        }
    }
}
