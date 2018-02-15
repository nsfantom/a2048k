package tm.nsfantom.a2048k.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_game.*
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.ui.AnimLayer
import tm.nsfantom.a2048k.ui.GameView
import tm.nsfantom.a2048k.util.Config

/**
 * Created by user on 2/13/18.
 */

class GameFragment : Fragment(), GameView.Listener {
    override fun getGameHolder(): GameFragment {
        return this
    }

    companion object {
        fun newInstance(): GameFragment {
            return GameFragment()
        }
    }

    private lateinit var listener: Listener
    private var score = 0

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
        return inflater?.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container.setBackgroundResource(R.color.defaultBackground)
        btnNewGame.setOnClickListener({ gameView.startGame() })
        gameView.listener = this
        gameView.post({ gameView.startDraw(gameView.width, gameView.height) })
    }

    fun clearScore() {
        score = 0
        showScore()
        showBestScore()
    }

    fun showScore() {
        (tvScore as TextView).text = score.toString()
    }

    fun addScore(s: Int) {
        score += s
        showScore()

        val maxScore = Math.max(score, getBestScore())
        saveBestScore(maxScore)
    }

    fun saveBestScore(bestScore: Int) {
        val e = activity.getPreferences(Context.MODE_PRIVATE).edit()
        e.putInt(Config.KEY_BEST_SCORE, bestScore)
        e.apply()
        showBestScore(bestScore)
    }

    fun getBestScore(): Int {
        return activity.getPreferences(Context.MODE_PRIVATE).getInt(Config.KEY_BEST_SCORE, 0)
    }

    fun showBestScore() {
        tvBestScore.text = getBestScore().toString()
    }

    fun showBestScore(score: Int) {
        tvBestScore.text = score.toString()
    }

    fun getAnimLayer(): AnimLayer {
        return animLayer
    }
}
