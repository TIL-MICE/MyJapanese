package pri.weiqiang.myjapanese.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import pri.weiqiang.myjapanese.ui.fragment.PixivIllustFragment;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustTab;


public class PixivIllustTabPagerAdapter extends BasePagerAdapter<PixivIllustTab> {

    public PixivIllustTabPagerAdapter(FragmentManager fm, List<PixivIllustTab> list) {
        super(fm, list);
    }

    @Override
    protected Fragment getFragment(PixivIllustTab pixivIllustTab) {
        return PixivIllustFragment.newInstance(pixivIllustTab.getMode());
    }

    @Override
    protected CharSequence getTitle(PixivIllustTab pixivIllustTab) {
        return pixivIllustTab.getTitle();
    }
}
