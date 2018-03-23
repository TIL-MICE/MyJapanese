package pri.weiqiang.myjapanese.mvp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.manager.DBManager;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.utils.ResourceUtils;

public class PuzzleActivityModelImpl implements BaseModel.PuzzleActivityModel {

    @Override
    public String[] getOptions() {
        return new String[]{
                ResourceUtils.getString(MyApplication.getInstance(), R.string.hiragana_rome),
                ResourceUtils.getString(MyApplication.getInstance(), R.string.hiragana_katakana),
                ResourceUtils.getString(MyApplication.getInstance(), R.string.katakana_rome)
        };
    }

    @Override
    public List<GojuonItem> getItems() {

        List<GojuonItem> items = new ArrayList<>();
        items.addAll(DBManager.getInstance().getQingYinWithoutHeader());

        Collections.shuffle(items);

        return items;
    }
}
