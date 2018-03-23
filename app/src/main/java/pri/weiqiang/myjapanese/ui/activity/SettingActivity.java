package pri.weiqiang.myjapanese.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.ui.fragment.SettingFragment;
import pri.weiqiang.myjapanese.rxbus.RxBus;
import pri.weiqiang.myjapanese.rxbus.event.EventContainer;
import pri.weiqiang.myjapanese.rxbus.event.SettingEvent;
import rx.Subscription;
import rx.functions.Action1;


public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.layout_root)
    CoordinatorLayout mRootLayout;

    boolean registered = false;

    Subscription subscription;

    @Override
    protected int getViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {

        if (!registered) {

            subscription = RxBus.getDefault().toObserverable(EventContainer.class).subscribe(new Action1<EventContainer>() {
                @Override
                public void call(EventContainer eventContainer) {

                    if (eventContainer.getType() == EventContainer.TYPE_SETTING) {

                        SettingEvent event = (SettingEvent) eventContainer.getEvent();
                        showSnackBar(mRootLayout, event.getMsg());
                    }


                }
            });

            registered = true;

        }


        initToolbar();
    }

    @Override
    protected void doAction() {

        getFragmentManager().beginTransaction().replace(R.id.content, new SettingFragment()).commit();

    }

    private void initToolbar() {

        mToolbar.setTitle(R.string.setting);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
