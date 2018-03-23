package pri.weiqiang.myjapanese.network.youdao.service;

import pri.weiqiang.myjapanese.mvp.bean.YoudaoTranslateBean;
import rx.Subscriber;

public interface YoudaoService {

    interface YoudaoTranslateService {

        void translate(String q, Subscriber<YoudaoTranslateBean> subscriber);
    }

}
