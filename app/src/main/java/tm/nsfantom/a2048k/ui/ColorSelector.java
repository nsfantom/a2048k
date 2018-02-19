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

import tm.nsfantom.a2048k.AppController;
import tm.nsfantom.a2048k.R;
import tm.nsfantom.a2048k.util.DisplayUtil;
import tm.nsfantom.a2048k.util.PrefStorage;

/**
 * Created by user on 2/16/18.
 */

public class ColorSelector extends LinearLayout {
    View layout;
    int[] colorArray = PrefStorage.Defaults.getDefaultColors();
    int pixel = 0;
    private LinearLayout colorHolder;

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

        colorHolder = layout.findViewById(R.id.colorHolder);
        pixel = ((AppController) context.getApplicationContext()).getPrefStorage().restoreColorFilter();
        colorHolder.post(() -> {
            int num = 13;
            colorArray = new int[13];

            int[] colorRes = PrefStorage.Defaults.getDefaultColors();
            for (int i = 0; i < colorRes.length; i++) {
                colorArray[i] = getResources().getColor(colorRes[i]);
            }
            ColorDrawable c;
            for (int i = 0; i < num; i++) {
                int width = colorHolder.getWidth() / num;
                View v = new View(context);
//                int color = 240 - (int)((222f/num)*i);
//                colorArray[i] = DisplayUtil.getIntFromColor(color,color,color);
                c = new ColorDrawable(colorArray[i]);
                c.setColorFilter(pixel, PorterDuff.Mode.DARKEN);
                c.mutate();
                v.setBackground(c.getCurrent());
                colorHolder.addView(v, width, colorHolder.getHeight());
            }
        });

        sampleView.setOnTouchListener((v, event) -> {
            pixel = ((SampleView) v).drawTarget((int) event.getX(), (int) event.getY());
//            int r = pixel >> 16 & 0xff, g = pixel >> 8 & 0xff, b = pixel >> 0 & 0xff;
            ColorDrawable c;
            for (int i = 0; i < colorHolder.getChildCount(); i++) {
                c = new ColorDrawable(colorArray[i]);
                c.setColorFilter(pixel, PorterDuff.Mode.DARKEN);
                c.mutate();
                colorHolder.getChildAt(i).setBackground(c.getCurrent());
            }
            return true;
        });

    }

    public void setDefaultPixel(int pixel) {
        this.pixel = pixel;
        ColorDrawable c;
        for (int i = 0; i < colorArray.length; i++) {
            View v = colorHolder.getChildAt(i);
            c = new ColorDrawable(colorArray[i]);
            c.setColorFilter(pixel, PorterDuff.Mode.DARKEN);
            c.mutate();
            v.setBackground(c);
        }
    }

    public int getPixel() {
        return pixel;
    }

    public ColorSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
