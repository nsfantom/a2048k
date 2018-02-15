package tm.nsfantom.a2048k.ui.other;

import android.content.Context;
import android.support.annotation.Nullable;

import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created by user on 2/14/18.
 */

public class SquareTextView extends AppCompatTextView {


    public SquareTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > heightMeasureSpec)
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        else super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
