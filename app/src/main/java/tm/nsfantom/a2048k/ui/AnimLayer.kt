package tm.nsfantom.a2048k.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import tm.nsfantom.a2048k.util.Config
import java.util.*

class AnimLayer : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val cells = ArrayList<Cell>()

    fun animateMove(from: Cell, to: Cell, fromX: Int, toX: Int, fromY: Int, toY: Int) {

        val cell = getCell(from.num)

        val layoutParams = FrameLayout.LayoutParams(Config.CELL_SIZE, Config.CELL_SIZE)
        layoutParams.leftMargin = fromX * Config.CELL_SIZE
        layoutParams.topMargin = fromY * Config.CELL_SIZE
        cell.layoutParams = layoutParams

        if (to.num <= 0) {
            to.label.visibility = View.INVISIBLE
        }
        val translateAnimation = TranslateAnimation(
                0f, (Config.CELL_SIZE * (toX - fromX)).toFloat(),
                0f, (Config.CELL_SIZE * (toY - fromY)).toFloat()
        )
        translateAnimation.duration = 100
        translateAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                to.label.visibility = View.VISIBLE
                recycleCell(cell)
            }
        })
        cell.startAnimation(translateAnimation)
    }

    private fun getCell(num: Int): Cell {
        val cell: Cell
        if (cells.size > 0) {
            cell = cells.removeAt(0)
        } else {
            cell = Cell(context)
            addView(cell)
        }
        cell.visibility = View.VISIBLE
        cell.num = num
        return cell
    }

    private fun recycleCell(cell: Cell) {
        cell.visibility = View.INVISIBLE
        cell.animation = null
        cells.add(cell)
    }

    fun scaleUp(cell: Cell) {
        val scaleAnimation = ScaleAnimation(0.1f, 1f, 0.1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = 100
        cell.animation = null
        cell.label.startAnimation(scaleAnimation)
    }
}
