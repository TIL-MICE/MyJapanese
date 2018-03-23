package pri.weiqiang.myjapanese.mvp.model;

import java.util.List;

import pri.weiqiang.myjapanese.manager.DBManager;
import pri.weiqiang.myjapanese.mvp.bean.Book;
import pri.weiqiang.myjapanese.mvp.bean.Lesson;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LessonsFragmentModelImpl implements BaseModel.LessonsFragmentModel {


    @Override
    public void getData(Subscriber<List<Book>> subscriber) {
        Observable.create(new Observable.OnSubscribe<List<Book>>() {

            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {

                List<Book> list;

                subscriber.onStart();

                list = DBManager.getInstance().getBooks();


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
