package pri.weiqiang.myjapanese.mvp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pri.weiqiang.myjapanese.manager.DBManager;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;

public class MemoryFragmentModelImpl implements BaseModel.MemoryFragmentModel {


    @Override
    public List<GojuonItem> getQingYinWithoutHeader() {

        List<GojuonItem> list = new ArrayList<>();
        list.addAll(DBManager.getInstance().getQingYinWithoutHeader());

        Collections.shuffle(list);

        return list;

    }

    @Override
    public List<GojuonItem> getZhuoYinWithoutHeader() {

        List<GojuonItem> list = new ArrayList<>();
        list.addAll(DBManager.getInstance().getZhuoYinWithoutHeader());

        Collections.shuffle(list);

        return list;
    }

    @Override
    public List<GojuonItem> getAoYinWithoutHeader() {

        List<GojuonItem> list = new ArrayList<>();
        list.addAll(DBManager.getInstance().getAoYinWithoutHeader());

        Collections.shuffle(list);

        return list;
    }
}
