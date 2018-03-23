package pri.weiqiang.myjapanese.mvp.model;

import java.util.List;

import pri.weiqiang.myjapanese.manager.DBManager;
import pri.weiqiang.myjapanese.mvp.bean.Word;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WordsFragmentModelImpl implements BaseModel.WordsFragmentModel {


    @Override
    public void getData(Subscriber<List<Word>> subscriber,final String lesson) {
        Observable.create(new Observable.OnSubscribe<List<Word>>() {

            @Override
            public void call(Subscriber<? super List<Word>> subscriber) {

                List<Word> list;

                subscriber.onStart();

                list = DBManager.getInstance().queryWord(lesson);


                if (list == null) {
                    subscriber.onError(new Exception());
                } else {
                    subscriber.onNext(list);
                }

                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(subscriber);
    }
}
