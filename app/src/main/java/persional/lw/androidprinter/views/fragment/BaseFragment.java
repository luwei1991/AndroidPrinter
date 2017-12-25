package persional.lw.androidprinter.views.fragment;


import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 陆伟 on 2017/12/14.
 */

public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
