package pri.weiqiang.myjapanese.network.baidu.service;

import pri.weiqiang.myjapanese.mvp.bean.BaiduTranslateBean;
import rx.Subscriber;

public interface BaiduService {

    interface TranslateService {

        void translate(String q, String from, String to, Subscriber<BaiduTranslateBean> subscriber);

    }


}
