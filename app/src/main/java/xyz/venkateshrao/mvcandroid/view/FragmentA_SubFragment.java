package xyz.venkateshrao.mvcandroid.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shipdream.lib.android.mvc.view.MvcFragment;

import javax.inject.Inject;

import xyz.venkateshrao.mvcandroid.R;
import xyz.venkateshrao.mvcandroid.controller.CounterController;

/**
 * Created by venkatesh on 16/9/15.
 */
public class FragmentA_SubFragment extends MvcFragment {

    @Inject
    private CounterController counterController;

    private TextView tvCountInEnglish;

    @Override
    public void onViewReady(View view, Bundle savedInstanceState, Reason reason) {
        super.onViewReady(view, savedInstanceState, reason);

        tvCountInEnglish = (TextView) view.findViewById(R.id.fragment_a_sub_countInEnglish);
        String text = counterController.convertNumberToEnglish(counterController.getModel().getCount());
        tvCountInEnglish.setText(text);
    }

    private void onEvent(CounterController.EventC2V.OnCounterUpdated event) {
        tvCountInEnglish.setText(event.getCountInEnglish());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_a_sub;
    }
}
