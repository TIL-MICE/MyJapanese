package pri.weiqiang.myjapanese.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.utils.ActivityUtils;


public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.btn_author)
    Button mBtnAuthor;


    @Override
    protected int getViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {

        initToolbar();
        initButton();
    }

    private void initToolbar() {

        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initButton() {

        mBtnAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openUrl(Constants.URL_AUTHOR);
            }
        });

    }


    @Override
    protected void doAction() {

    }
}
