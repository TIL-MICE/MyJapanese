package pri.weiqiang.myjapanese.network.youdao.service;

import pri.weiqiang.myjapanese.mvp.bean.YoudaoTranslateBean;
import pri.weiqiang.myjapanese.network.Api;
import pri.weiqiang.myjapanese.network.youdao.YoudaoTranslateApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YoudaoTranslateServiceImpl implements YoudaoService.YoudaoTranslateService {

    private static Retrofit instance = null;

    public static synchronized Retrofit getInstance() {

        if (instance == null) {

            instance = new Retrofit.Builder()
                    .baseUrl(Api.YOUDAO_TRANSLATE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return instance;
    }


    @Override
    public void translate(String q, Subscriber<YoudaoTranslateBean> subscriber) {

        getInstance().create(YoudaoTranslateApi.class)
                .request(Api.YOUDAO_TRANSLATE_KEYFROM, Api.YOUDAO_TRANSLATE_APIKEY, YoudaoTranslateApi.TYPE,
                        YoudaoTranslateApi.DOCTYPE_JSON, YoudaoTranslateApi.VERSION, q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }
}
