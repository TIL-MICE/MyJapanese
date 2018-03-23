package pri.weiqiang.myjapanese.mvp.model;

import java.util.List;

import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.manager.DBManager;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GojuonFragmentModelImpl implements BaseModel.JPStartFragmentModel {


    @Override
    public void getData(final int category, Subscriber<List<GojuonItem>> subscriber) {
        Observable.create(new Observable.OnSubscribe<List<GojuonItem>>() {

            @Override
            public void call(Subscriber<? super List<GojuonItem>> subscriber) {

                List<GojuonItem> list;

                subscriber.onStart();

                switch (category) {

                    case Constants.CATEGORY_QINGYIN:
                        list = DBManager.getInstance().getQingYin();
                        break;
                    case Constants.CATEGORY_ZHUOYIN:
                        list = DBManager.getInstance().getZhuoYin();
                        break;
                    case Constants.CATEGORY_AOYIN:
                        list = DBManager.getInstance().getAoYin();
                        break;
                    default:
                        list = null;
                        break;

                }

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
