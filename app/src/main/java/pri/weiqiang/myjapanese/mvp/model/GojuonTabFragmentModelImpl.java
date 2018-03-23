package pri.weiqiang.myjapanese.mvp.model;

import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.mvp.bean.GojuonTab;
import pri.weiqiang.myjapanese.utils.ResourceUtils;

public class GojuonTabFragmentModelImpl implements BaseModel.JPStartTabFragmentModel {

    @Override
    public List<GojuonTab> getData() {

        List<GojuonTab> list = new ArrayList<>();
        String qingyin = ResourceUtils.getString(MyApplication.getInstance(), pri.weiqiang.myjapanese.R.string.qingyin);
        String zhuoyin = ResourceUtils.getString(MyApplication.getInstance(), pri.weiqiang.myjapanese.R.string.zhuoyin);
        String aoyin = ResourceUtils.getString(MyApplication.getInstance(), pri.weiqiang.myjapanese.R.string.aoyin);

        list.add(new GojuonTab(Constants.CATEGORY_QINGYIN, qingyin));
        list.add(new GojuonTab(Constants.CATEGORY_ZHUOYIN, zhuoyin));
        list.add(new GojuonTab(Constants.CATEGORY_AOYIN, aoyin));

        return list;
    }
}
