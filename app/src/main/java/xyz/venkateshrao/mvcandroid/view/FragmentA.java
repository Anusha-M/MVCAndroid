package xyz.venkateshrao.mvcandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shipdream.lib.android.mvc.controller.NavigationController;
import com.shipdream.lib.android.mvc.view.MvcFragment;


import javax.inject.Inject;

import xyz.venkateshrao.mvcandroid.R;
import xyz.venkateshrao.mvcandroid.controller.CounterController;

/**
 * Created by venkatesh on 16/9/15.
 */
public class FragmentA extends MvcFragment {

    @Inject
    private NavigationController navigationController;

    @Inject
    private CounterController counterController;

    private TextView tvDisplay;
    private Button bIncrement;
    private Button bDecrement;
    private Button bShowAdvancedView;

    @Override
    public void onViewReady(View view, Bundle savedInstanceState, Reason reason) {
        super.onViewReady(view, savedInstanceState, reason);

        tvDisplay = (TextView) view.findViewById(R.id.fragment_a_counterDisplay);
        bIncrement = (Button) view.findViewById(R.id.fragment_a_buttonIncrement);
        bDecrement = (Button) view.findViewById(R.id.fragment_a_buttonDecrement);
        bShowAdvancedView = (Button) view.findViewById(R.id.fragment_a_buttonShowAdvancedView);

        bIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterController.increment(view);
            }
        });

        bDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterController.decrement(view);
            }
        });

        bShowAdvancedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterController.goToAdvancedView(view);
            }
        });

        if (reason == Reason.FIRST_TIME) {
            FragmentA_SubFragment fragmentA_subFragment = new FragmentA_SubFragment();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_a_anotherFragmentContainer, fragmentA_subFragment).commit();
        }

        updateCountDisplay(counterController.getModel().getCount());
    }

    @Override
    protected void onPoppedOutToFront() {
        super.onPoppedOutToFront();
        updateCountDisplay(counterController.getModel().getCount());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_a;
    }

    private void onEvent(CounterController.EventC2V.OnCounterUpdated event) {
        updateCountDisplay(event.getCount());
    }

    private void updateCountDisplay(int count) {
        tvDisplay.setText(String.valueOf(count));
    }
}
