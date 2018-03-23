package pri.weiqiang.myjapanese.network.pixiv.service;

import okhttp3.ResponseBody;
import rx.Subscriber;

public interface PixivService {

    interface IllustService {
        void getIllusts(String mode, Subscriber<ResponseBody> subscriber);

        void getIllust(int id, Subscriber<ResponseBody> subscriber);

    }

}
