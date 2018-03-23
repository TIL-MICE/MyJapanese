package pri.weiqiang.myjapanese.mvp.presenter;
import android.util.Log;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.Book;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.LessonsFragmentModelImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import rx.Subscriber;

/**
 * Created by weiqiang on 2018/3/16.
 */

public class LessonsFragmentPresenterImpl extends BasePresenter<BaseView.LessonsFragmentView> implements BasePresenter.LessonsFragmentPresenter {

    private final String TAG = getClass().getSimpleName();

    BaseModel.LessonsFragmentModel model;

    public LessonsFragmentPresenterImpl(BaseView.LessonsFragmentView view) {
        super(view);
        model = new LessonsFragmentModelImpl();
    }

    @Override
    public void initLessonsFragment() {
        view.initView();
        model.getData(new Subscriber<List<Book>>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: OK");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: OK");
            }

            @Override
            public void onNext(List<Book> list) {
                view.setData(list);
                Log.i(TAG, "onNext: OK");
            }
        });
    }
}
