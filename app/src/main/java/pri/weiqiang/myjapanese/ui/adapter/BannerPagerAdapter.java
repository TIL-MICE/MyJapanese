package pri.weiqiang.myjapanese.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import pri.weiqiang.myjapanese.ui.fragment.BannerFragment;
import pri.weiqiang.myjapanese.mvp.bean.BannerItem;

public class BannerPagerAdapter extends BasePagerAdapter<BannerItem> {

    public BannerPagerAdapter(FragmentManager fm, List<BannerItem> list) {
        super(fm, list);
    }

    @Override
    protected Fragment getFragment(BannerItem bannerItem) {
        return BannerFragment.newInstance(bannerItem);
    }

    @Override
    protected CharSequence getTitle(BannerItem bannerItem) {
        return bannerItem.getTitle();
    }
}
