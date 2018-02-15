package tm.nsfantom.a2048k.ui.other

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.cell_entry.view.*
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.util.DisplayUtil

/**
 * Created by user on 2/13/18.
 */

class CellEntry(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var textColor: Int = 0
    private var cellColor: Int = 0
    private var cellText: String? = null

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CellEntry, 0, 0)
        inflate(context, R.layout.cell_entry, this)
        try {
            cellText = a.getString(R.styleable.CellEntry_cellText)
            textColor = a.getColor(R.styleable.CellEntry_textColor, Color.WHITE)
            cellColor = a.getColor(R.styleable.CellEntry_cellColor, Color.GRAY)
        } finally {
            a.recycle()
        }


    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        tvText.setTextColor(textColor)
        tvText.text = cellText
        tvText.setBackgroundColor(cellColor)
        tvText.gravity = Gravity.CENTER
        tvText.width = DisplayUtil.dpToPx(resources, 32)
        tvText.height = DisplayUtil.dpToPx(resources, 32)
    }
}
