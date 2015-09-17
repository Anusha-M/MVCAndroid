package xyz.venkateshrao.mvcandroid.view;


import com.shipdream.lib.android.mvc.view.MvcActivity;
import com.shipdream.lib.android.mvc.view.MvcFragment;

public class MainActivity extends MvcActivity {


    @Override
    protected Class<? extends MvcFragment> mapNavigationFragment(String locationId) {
        switch (locationId) {
            case "LocationA":
                return FragmentA.class;
            case "LocationB":
                return FragmentB.class;
            default:
                return null;
        }
    }

    @Override
    protected Class<? extends DelegateFragment> getDelegateFragmentClass() {
        return ContainerFragment.class;
    }


    public static class ContainerFragment extends DelegateFragment {

        @Override
        protected void onStartUp() {
            getNavigationController().navigateTo(this, "LocationA");
        }
    }
}
