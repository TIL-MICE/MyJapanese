package pri.weiqiang.myjapanese.mvp.presenter;

import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.GojuonTabFragmentModelImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;

public class GojuonTabFragmentPresenterImpl extends BasePresenter<BaseView.JPStartTabFragmentView> implements BasePresenter.GojuonTabFragmentPresenter {

    BaseModel.JPStartTabFragmentModel model;

    public GojuonTabFragmentPresenterImpl(BaseView.JPStartTabFragmentView view) {
        super(view);
        model = new GojuonTabFragmentModelImpl();
    }

    @Override
    public void initJPStartTabFragment() {
        view.setData(model.getData());
        view.scrollViewPager(MyApplication.CURRENT_ITEM);
    }
}
