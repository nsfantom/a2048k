package tm.nsfantom.a2048k.ui

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import tm.nsfantom.a2048k.AppController
import tm.nsfantom.a2048k.R

class Cell : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    //    private var array = intArrayOf(R.color.color_0, R.color.color_2, R.color.color_4, R.color.color_8,
//            R.color.color_16, R.color.color_32, R.color.color_64, R.color.color_128,
//            R.color.color_256, R.color.color_512, R.color.color_1024, R.color.color_2048,
//            R.color.color_00)
//    private var array = PrefStorage.getDefaultColors()
    private var pixel = (context.applicationContext as AppController).prefStorage.restoreColorFilter()
    var num = 0
        set(num) {
            field = num

            if (num <= 0) {
                label.text = ""
            } else {
                label.text = num.toString()
            }

//            when (num) {
//                0 -> label.setBackgroundResource(array[0])
//                2 -> label.setBackgroundResource(array[1])
//                4 -> label.setBackgroundResource(array[2])
//                8 -> label.setBackgroundResource(array[3])
//                16 -> label.setBackgroundResource(array[4])
//                32 -> label.setBackgroundResource(array[5])
//                64 -> label.setBackgroundResource(array[6])
//                128 -> label.setBackgroundResource(array[7])
//                256 -> label.setBackgroundResource(array[8])
//                512 -> label.setBackgroundResource(array[9])
//                1024 -> label.setBackgroundResource(array[10])
//                2048 -> label.setBackgroundResource(array[11])
//                else -> label.setBackgroundResource(array[12])
//            }
            when (num) {
                0 -> label.setBackgroundResource(R.color.color_0)
                2 -> label.setBackgroundResource(R.color.color_2)
                4 -> label.setBackgroundResource(R.color.color_4)
                8 -> label.setBackgroundResource(R.color.color_8)
                16 -> label.setBackgroundResource(R.color.color_16)
                32 -> label.setBackgroundResource(R.color.color_32)
                64 -> label.setBackgroundResource(R.color.color_64)
                128 -> label.setBackgroundResource(R.color.color_128)
                256 -> label.setBackgroundResource(R.color.color_256)
                512 -> label.setBackgroundResource(R.color.color_512)
                1024 -> label.setBackgroundResource(R.color.color_1024)
                2048 -> label.setBackgroundResource(R.color.color_2048)
                else -> label.setBackgroundResource(R.color.color_00)
            }
            if (pixel != 0)
                label.background.setColorFilter(pixel, PorterDuff.Mode.DARKEN)
        }

    lateinit var label: TextView
    private lateinit var background: View
    private fun initView() {

        var lp: FrameLayout.LayoutParams?

        background = View(context)
        lp = FrameLayout.LayoutParams(-1, -1)
        lp.setMargins(8, 8, 0, 0)
        background.setBackgroundResource(R.color.semiTransparent)
        addView(background, lp)

        label = TextView(context)
        label.textSize = 28f
        label.gravity = Gravity.CENTER

        lp = FrameLayout.LayoutParams(-1, -1)
        lp.setMargins(8, 8, 0, 0)
        addView(label, lp)

    }

    fun equals(o: Cell): Boolean {
        return num == o.num
    }

    fun copy(): Cell {
        val c = Cell(context)
        c.num = num
        return c
    }
}