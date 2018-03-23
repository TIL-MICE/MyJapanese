package pri.weiqiang.myjapanese.mvp.presenter;

import android.util.Log;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.GojuonFragmentModelImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import rx.Subscriber;

public class GojuonFragmentPresenterImpl extends BasePresenter<BaseView.JPStartFragmentView> implements BasePresenter.GojuonFragmentPresenter {

    private final String TAG = getClass().getSimpleName();

    BaseModel.JPStartFragmentModel model;

    public GojuonFragmentPresenterImpl(BaseView.JPStartFragmentView view) {
        super(view);

        model = new GojuonFragmentModelImpl();
    }

    @Override
    public void initJPStartFragment(int type) {

        view.setRecyclerView(type);
        model.getData(type, new Subscriber<List<GojuonItem>>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: OK");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: OK");
            }

            @Override
            public void onNext(List<GojuonItem> list) {
                view.setData(list);
                Log.i(TAG, "onNext: OK");
            }
        });

    }
}
