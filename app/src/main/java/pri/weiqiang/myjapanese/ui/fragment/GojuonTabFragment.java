package pri.weiqiang.myjapanese.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.ui.adapter.GojuonTabPagerAdapter;
import pri.weiqiang.myjapanese.mvp.bean.GojuonTab;
import pri.weiqiang.myjapanese.mvp.presenter.BasePresenter;
import pri.weiqiang.myjapanese.mvp.presenter.GojuonTabFragmentPresenterImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;

public class GojuonTabFragment extends BaseFragment implements BaseView.JPStartTabFragmentView {


    private static final String TAG = GojuonTabFragment.class.getSimpleName();
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private BasePresenter.GojuonTabFragmentPresenter presenter;


    @Override
    protected int getViewId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {

        presenter = new GojuonTabFragmentPresenterImpl(this);
        setHasOptionsMenu(false);
        initTabLayout();
        initViewPager();

    }

    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViewPager() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MyApplication.CURRENT_ITEM = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected void doAction() {

        presenter.initJPStartTabFragment();

    }

    @Override
    public void setData(List<GojuonTab> data) {
        //JPStartTabPagerAdapter中获得JPStartFragment
        Log.e(TAG,"JPStartTabPagerAdapter中获得JPStartFragment");
        mViewPager.setAdapter(new GojuonTabPagerAdapter(getChildFragmentManager(), data));

    }

    @Override
    public void scrollViewPager(int position) {
        mViewPager.setCurrentItem(position, true);
    }
}
