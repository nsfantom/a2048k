package tm.nsfantom.a2048k.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_game.*
import tm.nsfantom.a2048k.AppController
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.model.StatItem
import tm.nsfantom.a2048k.ui.Cell
import tm.nsfantom.a2048k.ui.GameView

class GameFragment : Fragment(), GameView.Listener {

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
        gameView.gameHolder = this
        gameView.post({ gameView.startDraw(gameView.width, gameView.height) })
    }

    private fun showScore() {
        (tvScore as TextView).text = score.toString()
    }

    private fun saveBestScore(bestScore: Int) {
        (activity.application as AppController).prefStorage.saveBestScore(bestScore)
        tvBestScore.text = bestScore.toString()
    }

    private fun getCurrentScore(): Int {
        return tvScore.text.toString().toInt()
    }

    private fun getBestScore(): Int {
        return (activity.application as AppController).prefStorage.getBestScore()
    }

    private fun clearScore() {
        score = 0
        showScore()
        tvBestScore.text = getBestScore().toString()
    }

    override fun animScaleUp(cell: Cell) {
        animLayer.scaleUp(cell)
    }

    override fun animMove(from: Cell, to: Cell, fromX: Int, toX: Int, fromY: Int, toY: Int) {
        animLayer.animateMove(from, to, fromX, toX, fromY, toY)
    }

    override fun gameSaveScoreStats() {
        if (!tvScore.text.toString().isEmpty()) {
            val result: Long = tvScore.text.toString().toLong()
            if (result > 0) {
                val currentMillis = System.currentTimeMillis()
                val statItem = StatItem(result, currentMillis)
                (activity.application as AppController).prefStorage.saveResults(statItem)
            }
        }
        clearScore()
    }

    override fun gameAddScore(s: Int) {
        score += s
        showScore()
        val maxScore = Math.max(score, getBestScore())
        saveBestScore(maxScore)
    }

    override fun gameShowGameOver() {
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.game_over_title))
                .setMessage(getString(R.string.game_over_message, getCurrentScore()))
                .setPositiveButton(getString(R.string.btn_restart)) { _, _ -> gameView.startGame() }
                .show()
        gameSaveScoreStats()
    }
}
