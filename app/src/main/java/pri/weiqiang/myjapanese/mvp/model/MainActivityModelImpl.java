package pri.weiqiang.myjapanese.mvp.model;

import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.mvp.bean.BannerItem;

public class MainActivityModelImpl implements BaseModel.MainActivityModel {


    @Override
    public List<BannerItem> getData() {

        List<BannerItem> list = new ArrayList<>();
        list.add(new BannerItem(R.drawable.banner01, ""));
        list.add(new BannerItem(R.drawable.banner02, ""));
        list.add(new BannerItem(R.drawable.banner03, ""));

        return list;
    }
}
