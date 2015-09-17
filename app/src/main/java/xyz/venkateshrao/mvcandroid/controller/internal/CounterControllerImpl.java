package xyz.venkateshrao.mvcandroid.controller.internal;

import com.shipdream.lib.android.mvc.controller.NavigationController;
import com.shipdream.lib.android.mvc.controller.internal.BaseControllerImpl;

import javax.inject.Inject;

import xyz.venkateshrao.mvcandroid.controller.CounterController;
import xyz.venkateshrao.mvcandroid.model.CounterModel;

/**
 * Created by venkatesh on 16/9/15.
 */
public class CounterControllerImpl extends BaseControllerImpl<CounterModel> implements CounterController {

    @Inject
    NavigationController navigationController;

    @Override
    protected Class<CounterModel> getModelClassType() {
        return CounterModel.class;
    }

    @Override
    public void increment(Object sender) {
        int count = getModel().getCount();
        getModel().setCount(++count);
        postC2VEvent(new EventC2V.OnCounterUpdated(sender, count, convertNumberToEnglish(count)));
    }

    @Override
    public void decrement(Object sender) {
        int count = getModel().getCount();
        getModel().setCount(--count);
        postC2VEvent(new EventC2V.OnCounterUpdated(sender, count, convertNumberToEnglish(count)));
    }

    @Override
    public String convertNumberToEnglish(int number) {
        if (number < -3) {
            return "Less than negative three";
        } else  if (number == -3) {
            return "Negative three";
        } else  if (number == -2) {
            return "Negative two";
        } else  if (number == -1) {
            return "Negative one";
        } else if (number == 0) {
            return "Zero";
        } else if (number == 1) {
            return "One";
        } else if (number == 2) {
            return "Two";
        } else if (number == 3) {
            return "Three";
        } else {
            return "Greater than three";
        }
    }

    @Override
    public void goToAdvancedView(Object sender) {
        navigationController.navigateTo(sender, "LocationB");
    }

    @Override
    public void goBackToBasicView(Object sender) {
        navigationController.navigateBack(sender);
    }
}
