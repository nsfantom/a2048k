package tm.nsfantom.a2048k.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import timber.log.Timber;
import tm.nsfantom.a2048k.R;
import tm.nsfantom.a2048k.util.DisplayUtil;

/**
 * Created by user on 2/16/18.
 */

public class ColorSelector extends LinearLayout{
    View layout;
    int[] colorArray;
    int[] selectedArray;

    public ColorSelector(Context context) {
        super(context);
    }

    public ColorSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        setOrientation(VERTICAL);
        layout = layoutInflater.inflate(R.layout.color_selector, this, true);
        final SampleView sampleView = new SampleView(context);
        addView(sampleView, LayoutParams.MATCH_PARENT, DisplayUtil.dpToPx(getResources(), 16));
        final LinearLayout colorHolder = layout.findViewById(R.id.colorHolder);
        colorHolder.post(() -> {
            int num = 13;
            colorArray = new int[13];
            for (int i = 0; i < num; i++) {
                int width = colorHolder.getWidth() / num;
                View v = new View(context);
                int color = 240 - (int)((222f/num)*i);
                colorArray[i] = DisplayUtil.getIntFromColor(color,color,color);
                v.setBackgroundColor(colorArray[i]);
                colorHolder.addView(v, width, colorHolder.getHeight());

            }
        });

        sampleView.setOnTouchListener((v, event) -> {
            int pixel = ((SampleView) v).drawTarget((int) event.getX(), (int) event.getY());
//            int r = pixel >> 16 & 0xff, g = pixel >> 8 & 0xff, b = pixel >> 0 & 0xff;
            ColorDrawable c;
            selectedArray = colorArray;
            for (int i = 0; i < colorHolder.getChildCount(); i++) {
                c = new ColorDrawable(colorArray[i]);
                c.setColorFilter(pixel, PorterDuff.Mode.OVERLAY);
                c.mutate();
                selectedArray[i] = c.getColor();
                Timber.e("color: %s",c.getColor());
                colorHolder.getChildAt(i).setBackground(c.getCurrent());
            }
            return true;
        });

    }



    public ColorSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

//        layout.tvHeader.setTextColor(textColor);
//        layout.tvHeader.setText(headerText);
//        layout.tvHeader.setBackgroundColor(mainColor);
//        layout.btnAdd.setTextColor(textColor);
//        layout.btnAdd.setBackgroundColor(secondColor);
//        layout.tvFooter.setTextColor(textColor);
//        layout.tvFooter.setText(footerText);
//        layout.tvFooter.setBackgroundColor(mainColor);
//        layout.listHolder.setBackgroundColor(holderColor);
//
//
//        if (listener != null)
//            layout.btnAdd.setOnClickListener(v -> listener.onAdd());
    }

    private static class SampleView extends View {

        final int[] colorArray = new int[]{Color.WHITE, Color.RED, Color.YELLOW, Color.CYAN, Color.GREEN, Color.BLUE, Color.BLACK};
        private Paint paint;
        private Shader shader;
        private Point point = null;
        private int pixel = 0;
        private Rect rectGrad;
        private Paint paintPicker;

        // CONSTRUCTOR
        public SampleView(Context context) {
            super(context);
            setFocusable(true);
            paint = new Paint();
            rectGrad = new Rect();
            paintPicker = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            shader = new LinearGradient(0, 0, canvas.getWidth(), 0,
                    colorArray, null, Shader.TileMode.MIRROR);  // CLAMP MIRROR REPEAT
            paint.setShader(shader);
            rectGrad.set(0, 1, canvas.getWidth(), canvas.getHeight());
            canvas.drawRect(rectGrad, paint);
            if (point != null) {
                paintPicker.setColor(Color.BLACK);
                canvas.drawLine(point.x - 5, 0, point.x + 5, 0, paintPicker);
            }
        }

        public int drawTarget(int x, int y) {
            Rect rect = new Rect();
            if (rectGrad.contains(x, y)) {
                setDrawingCacheEnabled(true);
                buildDrawingCache();
                pixel = getDrawingCache().getPixel(x, y);
                destroyDrawingCache();
                setDrawingCacheEnabled(false);
                point = new Point(x, y);
                invalidate();
                return pixel;
            }
            return pixel;
        }
    }


}
