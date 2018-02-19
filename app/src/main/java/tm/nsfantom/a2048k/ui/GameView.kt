package tm.nsfantom.a2048k.ui

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import tm.nsfantom.a2048k.AppController
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.util.Config

class GameView : LinearLayout {
    private inline fun <reified T> matrix2d(height: Int, width: Int, init: (Int, Int) -> Array<T>) = Array(height, { row -> init(row, width) })
    private lateinit var cellsMap: Array<Array<Cell>>
    private val emptyPoints = ArrayList<Point>()
    lateinit var gameHolder: Listener

    interface Listener {
        fun animScaleUp(cell: Cell)
        fun animMove(from: Cell, to: Cell, fromX: Int, toX: Int, fromY: Int, toY: Int)
        fun gameSaveScoreStats()
        fun gameAddScore(s: Int)
        fun gameShowGameOver()
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initGameView()
    }

    private fun initGameView() {
        Config.CELL_COUNT = (context.applicationContext as AppController).prefStorage.getCellCount()
        cellsMap = matrix2d(Config.CELL_COUNT, Config.CELL_COUNT, { _: Int, width: Int -> Array(width) { _ -> Cell(context) } })
        orientation = LinearLayout.VERTICAL
        setBackgroundResource(R.color.gameFrameBackground)

        setOnTouchListener(object : View.OnTouchListener {

            private var startX = 0f
            private var startY = 0f
            private var offsetX = 0f
            private var offsetY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                    }
                    MotionEvent.ACTION_UP -> {
                        offsetX = event.x - startX
                        offsetY = event.y - startY

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft()
                            } else if (offsetX > 5) {
                                swipeRight()
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp()
                            } else if (offsetY > 5) {
                                swipeDown()
                            }
                        }
                    }
                }
                return true
            }
        })
    }

    fun startDraw(w: Int, h: Int) {
        Config.CELL_SIZE = (Math.min(w, h) - 8) / Config.CELL_COUNT
        addCells(Config.CELL_SIZE, Config.CELL_SIZE)
        startGame()
    }

    private fun addCells(cellWidth: Int, cellHeight: Int) {
        for (y in 0 until Config.CELL_COUNT) {
            val line = LinearLayout(context)
            val lineLp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, cellHeight)
            addView(line, lineLp)
            for (x in 0 until Config.CELL_COUNT) {
                line.addView(cellsMap[x][y], cellWidth, cellHeight)
            }
        }
    }

    fun startGame() {
        gameHolder.gameSaveScoreStats()

        for (y in 0 until Config.CELL_COUNT) {
            for (x in 0 until Config.CELL_COUNT) {
                cellsMap[x][y].num = 0
            }
        }

        addRandomNum()
        addRandomNum()
    }

    private fun addRandomNum() {

        emptyPoints.clear()

        for (y in 0 until Config.CELL_COUNT) {
            for (x in 0 until Config.CELL_COUNT) {
                if (cellsMap[x][y].num <= 0) {
                    emptyPoints.add(Point(x, y))
                }
            }
        }

        if (emptyPoints.size > 0) {
            val p = emptyPoints.removeAt((Math.random() * emptyPoints.size).toInt())
            cellsMap[p.x][p.y].num = if (Math.random() > 0.1) 2 else 4
            gameHolder.animScaleUp(cellsMap[p.x][p.y])
        }
    }


    private fun swipeLeft() {

        var merge = false

        for (y in 0 until Config.CELL_COUNT) {
            var x = 0
            while (x < Config.CELL_COUNT) {

                for (x1 in x + 1 until Config.CELL_COUNT) {
                    if (cellsMap[x1][y].num > 0) {

                        if (cellsMap[x][y].num <= 0) {
                            gameHolder.animMove(cellsMap[x1][y], cellsMap[x][y], x1, x, y, y)

                            cellsMap[x][y].num = cellsMap[x1][y].num
                            cellsMap[x1][y].num = 0

                            x--
                            merge = true

                        } else if (cellsMap[x][y].equals(cellsMap[x1][y])) {
                            gameHolder.animMove(cellsMap[x1][y], cellsMap[x][y], x1, x, y, y)

                            cellsMap[x][y].num = cellsMap[x][y].num * 2
                            cellsMap[x1][y].num = 0

                            gameHolder.gameAddScore(cellsMap[x][y].num)
                            merge = true
                        }

                        break
                    }
                }
                x++
            }
        }

        if (merge) {
            addRandomNum()
            checkComplete()
        }
    }

    private fun swipeRight() {

        var merge = false

        for (y in 0 until Config.CELL_COUNT) {
            var x = Config.CELL_COUNT - 1
            while (x >= 0) {

                for (x1 in x - 1 downTo 0) {
                    if (cellsMap[x1][y].num > 0) {

                        if (cellsMap[x][y].num <= 0) {
                            gameHolder.animMove(cellsMap[x1][y], cellsMap[x][y], x1, x, y, y)
                            cellsMap[x][y].num = cellsMap[x1][y].num
                            cellsMap[x1][y].num = 0

                            x++
                            merge = true
                        } else if (cellsMap[x][y].equals(cellsMap[x1][y])) {
                            gameHolder.animMove(cellsMap[x1][y], cellsMap[x][y], x1, x, y, y)
                            cellsMap[x][y].num = cellsMap[x][y].num * 2
                            cellsMap[x1][y].num = 0
                            gameHolder.gameAddScore(cellsMap[x][y].num)
                            merge = true
                        }

                        break
                    }
                }
                x--
            }
        }

        if (merge) {
            addRandomNum()
            checkComplete()
        }
    }

    private fun swipeUp() {

        var merge = false

        for (x in 0 until Config.CELL_COUNT) {
            var y = 0
            while (y < Config.CELL_COUNT) {

                for (y1 in y + 1 until Config.CELL_COUNT) {
                    if (cellsMap[x][y1].num > 0) {

                        if (cellsMap[x][y].num <= 0) {
                            gameHolder.animMove(cellsMap[x][y1], cellsMap[x][y], x, x, y1, y)
                            cellsMap[x][y].num = cellsMap[x][y1].num
                            cellsMap[x][y1].num = 0

                            y--

                            merge = true
                        } else if (cellsMap[x][y].equals(cellsMap[x][y1])) {
                            gameHolder.animMove(cellsMap[x][y1], cellsMap[x][y], x, x, y1, y)
                            cellsMap[x][y].num = cellsMap[x][y].num * 2
                            cellsMap[x][y1].num = 0
                            gameHolder.gameAddScore(cellsMap[x][y].num)
                            merge = true
                        }

                        break

                    }
                }
                y++
            }
        }

        if (merge) {
            addRandomNum()
            checkComplete()
        }
    }

    private fun swipeDown() {

        var merge = false

        for (x in 0 until Config.CELL_COUNT) {
            var y = Config.CELL_COUNT - 1
            while (y >= 0) {

                for (y1 in y - 1 downTo 0) {
                    if (cellsMap[x][y1].num > 0) {

                        if (cellsMap[x][y].num <= 0) {
                            gameHolder.animMove(cellsMap[x][y1], cellsMap[x][y], x, x, y1, y)
                            cellsMap[x][y].num = cellsMap[x][y1].num
                            cellsMap[x][y1].num = 0

                            y++
                            merge = true
                        } else if (cellsMap[x][y].equals(cellsMap[x][y1])) {
                            gameHolder.animMove(cellsMap[x][y1], cellsMap[x][y], x, x, y1, y)
                            cellsMap[x][y].num = cellsMap[x][y].num * 2
                            cellsMap[x][y1].num = 0
                            gameHolder.gameAddScore(cellsMap[x][y].num)
                            merge = true
                        }

                        break
                    }
                }
                y--
            }
        }

        if (merge) {
            addRandomNum()
            checkComplete()
        }
    }

    private fun checkComplete() {

        var complete = true

        ALL@ for (y in 0 until Config.CELL_COUNT) {
            for (x in 0 until Config.CELL_COUNT) {
                if (cellsMap[x][y].num == 0 ||
                        x > 0 && cellsMap[x][y].equals(cellsMap[x - 1][y]) ||
                        x < Config.CELL_COUNT - 1 && cellsMap[x][y].equals(cellsMap[x + 1][y]) ||
                        y > 0 && cellsMap[x][y].equals(cellsMap[x][y - 1]) ||
                        y < Config.CELL_COUNT - 1 && cellsMap[x][y].equals(cellsMap[x][y + 1])) {

                    complete = false
                    break@ALL
                }
            }
        }

        if (complete) {
            gameHolder.gameShowGameOver()
        }
    }
}
