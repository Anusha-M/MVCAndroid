package xyz.venkateshrao.mvcandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shipdream.lib.android.mvc.view.MvcFragment;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.venkateshrao.mvcandroid.R;
import xyz.venkateshrao.mvcandroid.controller.CounterController;

/**
 * Created by venkatesh on 16/9/15.
 */
public class FragmentB extends MvcFragment {
    @Inject
    private CounterController counterController;

    @Bind(R.id.fragment_b_counterDisplay)
    TextView tvDisplay;

    @Bind(R.id.fragment_b_buttonIncrement)
    Button bIncrement;

    @Bind(R.id.fragment_b_buttonDecrement)
    Button bDecrement;

    @Bind(R.id.fragment_b_buttonAutoIncrement)
    Button bAutoIncrement;

    private Handler handler;

    private ContinousCounter incrementCounter;
    private ContinousCounter decrementCounter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_b;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public void onViewReady(View view, Bundle savedInstanceState, Reason reason) {
        super.onViewReady(view, savedInstanceState, reason);
        ButterKnife.bind(this, view);

        bIncrement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startContinuousIncrement();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        stopContinuousIncrement();
                        break;
                }
                return false;
            }
        });

        bDecrement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startContinuousDecrement();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        stopContinuousDecrement();
                        break;
                }
                return false;
            }
        });

        bAutoIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CounterService.class);
                getActivity().startService(intent);
            }
        });

        updateCountDisplay(counterController.getModel().getCount());
    }


    private void startContinuousIncrement() {
        stopContinuousIncrement();
        incrementCounter = new ContinousCounter(true);
        incrementCounter.run();
    }

    private void startContinuousDecrement() {
        stopContinuousDecrement();
        decrementCounter = new ContinousCounter(false);
        decrementCounter.run();
    }

    private void stopContinuousIncrement() {
        if (incrementCounter != null) {
            incrementCounter.cancel();
        }
    }

    private void stopContinuousDecrement() {
        if (decrementCounter != null) {
            decrementCounter.cancel();
        }
    }

    @Override
    public boolean onBackButtonPressed() {
        counterController.goBackToBasicView(this);
        return true;
    }

    private void onEvent(CounterController.EventC2V.OnCounterUpdated event) {
        updateCountDisplay(event.getCount());
    }

    private void updateCountDisplay(int count) {
        tvDisplay.setText(String.valueOf(count));
    }

    private class ContinousCounter implements Runnable {
        private final boolean incrementing;
        private boolean canceled = false;
        private static final long INTERVAL = 200;

        public ContinousCounter(boolean incrementing) {
            this.incrementing = incrementing;
        }

        @Override
        public void run() {
            if (!canceled) {
                if (incrementing) {
                    counterController.increment(this);
                } else {
                    counterController.decrement(this);
                }
                handler.postDelayed(this, INTERVAL);
            }
        }
        private void cancel() {
            this.canceled = true;
        }
    }
}
