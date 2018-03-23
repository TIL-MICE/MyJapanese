package pri.weiqiang.myjapanese.network.baidu.service;

import com.blankj.utilcode.utils.EncryptUtils;

import pri.weiqiang.myjapanese.mvp.bean.BaiduTranslateBean;
import pri.weiqiang.myjapanese.network.Api;
import pri.weiqiang.myjapanese.network.baidu.BaiduTranslateApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BaiduTranslateServiceImpl implements BaiduService.TranslateService {

    private static Retrofit instance = null;

    public static synchronized Retrofit getInstance() {

        if (instance == null) {

            instance = new Retrofit.Builder()
                    .baseUrl(Api.BAIDU_TRANSLATE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return instance;
    }


    @Override
    public void translate(String q, String from, String to, Subscriber<BaiduTranslateBean> subscriber) {

        int salt = (int) (Math.random() * 99999);
        String sign = EncryptUtils.encryptMD5ToString(Api.BAIDU_TRANSLATE_APPID + q + salt + Api.BAIDU_TRANSLATE_SECRETKEY).toLowerCase();

        getInstance().create(BaiduTranslateApi.class)
                .request(q, from, to, Api.BAIDU_TRANSLATE_APPID, salt, sign)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscriber);

    }
}
