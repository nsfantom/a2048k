package tm.nsfantom.a2048k.ui.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridLayout;

import timber.log.Timber;

/**
 * Created by user on 2/14/18.
 */

public class Linear2DLayout extends GridLayout {
    int cellSize = 0;
    int cellSizeDp = 48;

    public Linear2DLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(int x, int y) {
        this.setColumnCount(x);
        this.setRowCount(y);
        int w = this.getWidth() - (x << 2);
        int h = this.getHeight() - (y << 2);
        if (w > h) cellSize = h / y;
        else cellSize = w / x;
        Timber.e("w: %s, h: %s, cellSize: %s", w, h, cellSize);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
//                SquareTextView cellEntry = new SquareTextView(getContext(),null);
                CellEntry cellEntry = new CellEntry(getContext(), null);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(cellSize, cellSize);
                this.addView(cellEntry, i * x + j, params);
            }
        }
    }
}
