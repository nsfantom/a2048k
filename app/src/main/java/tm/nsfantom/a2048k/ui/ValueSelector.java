package tm.nsfantom.a2048k.ui;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import tm.nsfantom.a2048k.R;

public class ValueSelector extends ConstraintLayout {

    private boolean plusButtonIsPressed = false;
    private boolean minusButtonIsPressed = false;
    private final long REPEAT_INTERVAL_MS = 100l;

    private TextView valueTextView;
    private TextView tvValueLabel;

    Handler handler = new Handler();

    public ValueSelector(Context context) {
        super(context);
        init(context);
    }

    public ValueSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ValueSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View rootView = inflate(context, R.layout.value_selector, this);
        valueTextView = (TextView) rootView.findViewById(R.id.etValue);
        tvValueLabel = (TextView) rootView.findViewById(R.id.tvValueLabel);

        View minusButton = rootView.findViewById(R.id.btnMinus);
        View plusButton = rootView.findViewById(R.id.btnPlus);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValue(); //we'll define this method later
            }
        });

        minusButton.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View arg0) {
                        minusButtonIsPressed = true;
                        handler.post(new AutoDecrementer());
                        return false;
                    }
                }
        );

        minusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    minusButtonIsPressed = false;
                }
                return false;
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValue();
            }
        });

        plusButton.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View arg0) {
                        plusButtonIsPressed = true;
                        handler.post(new AutoIncrementer());
                        return false;
                    }
                }
        );

        plusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    plusButtonIsPressed = false;
                }
                return false;
            }
        });

    }

    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue() {
        return Integer.valueOf(valueTextView.getText().toString());
    }

    public void setValue(int newValue) {
        int value = newValue;
        if (newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }

        valueTextView.setText(String.valueOf(value));
    }

    private void incrementValue() {
        int currentVal = Integer.valueOf(valueTextView.getText().toString());
        if (currentVal  < maxValue) {
            valueTextView.setText(String.valueOf(currentVal + 1));
        }
    }

    private void decrementValue() {
        int currentVal = Integer.valueOf(valueTextView.getText().toString());
        if (currentVal  > minValue) {
            valueTextView.setText(String.valueOf(currentVal - 1));
        }
    }

    private class AutoIncrementer implements Runnable {
        @Override
        public void run() {
            if(plusButtonIsPressed){
                incrementValue();
                handler.postDelayed( new AutoIncrementer(), REPEAT_INTERVAL_MS);
            }
        }
    }
    private class AutoDecrementer implements Runnable {
        @Override
        public void run() {
            if(minusButtonIsPressed){
                decrementValue();
                handler.postDelayed(new AutoDecrementer(), REPEAT_INTERVAL_MS);
            }
        }
    }

    public void setLabelText(String text) {
        tvValueLabel.setText(text);
    }
}
