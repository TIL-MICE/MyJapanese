package pri.weiqiang.myjapanese.mvp.presenter;
import android.util.Log;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.Word;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.WordsFragmentModelImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import rx.Subscriber;

/**
 * Created by weiqiang on 2018/3/16.
 */

public class WordsFragmentPresenterImpl extends BasePresenter<BaseView.WordsFragmentView> implements BasePresenter.WordsFragmentPresenter {

    private final String TAG = getClass().getSimpleName();

    BaseModel.WordsFragmentModel model;

    private String lesson;
    public WordsFragmentPresenterImpl(BaseView.WordsFragmentView view,String lesson) {
        super(view);
        this.lesson = lesson;
        model = new WordsFragmentModelImpl();
    }

    @Override
    public void initWordsFragment() {
        view.setRecyclerView();
        model.getData(new Subscriber<List<Word>>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: OK");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: OK");
            }

            @Override
            public void onNext(List<Word> list) {
                view.setData(list);
                Log.i(TAG, "onNext: OK");
            }
        },lesson);
    }
}
