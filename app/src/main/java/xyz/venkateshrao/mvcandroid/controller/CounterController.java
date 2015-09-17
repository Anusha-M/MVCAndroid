package xyz.venkateshrao.mvcandroid.controller;

import com.shipdream.lib.android.mvc.controller.BaseController;
import com.shipdream.lib.android.mvc.event.BaseEventC2V;

import xyz.venkateshrao.mvcandroid.model.CounterModel;

/**
 * Created by venkatesh on 16/9/15.
 */
public interface CounterController extends BaseController<CounterModel> {

    void increment(Object sender);

    void decrement(Object sender);

    String convertNumberToEnglish(int number);

    void goToAdvancedView(Object sender);

    void goBackToBasicView(Object sender);

    interface EventC2V {

        class OnCounterUpdated extends BaseEventC2V {
            private final int count;
            private final String countInEnglish;

            public OnCounterUpdated(Object sender, int count, String countInEnglish) {
                super(sender);
                this.count = count;
                this.countInEnglish = countInEnglish;
            }

            public int getCount() {
                return count;
            }

            public String getCountInEnglish() {
                return countInEnglish;
            }
        }

    }
}
