package pri.weiqiang.myjapanese.mvp.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustBean;
import pri.weiqiang.myjapanese.network.Api;
import pri.weiqiang.myjapanese.network.pixiv.service.PixivIllustServiceImpl;
import pri.weiqiang.myjapanese.network.pixiv.service.PixivService;
import pri.weiqiang.myjapanese.utils.ResourceUtils;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PixivIllustFragmentModelImpl implements BaseModel.PixivIllustFragmentModel {


    PixivService.IllustService service;

    public PixivIllustFragmentModelImpl() {
        service = new PixivIllustServiceImpl();
    }

    @Override
    public void getIllusts(final String mode, Subscriber<List<PixivIllustBean>> subscriber) {

        Observable.create(new Observable.OnSubscribe<List<PixivIllustBean>>() {
            @Override
            public void call(final Subscriber<? super List<PixivIllustBean>> subscriber) {
                subscriber.onStart();
                service.getIllusts(mode, new Subscriber<ResponseBody>() {

                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        List<PixivIllustBean> list = handleResponseBody(responseBody);
                        subscriber.onNext(list);

                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    @Override
    public String[] getOptions() {

        return new String[]{ResourceUtils.getString(MyApplication.getInstance(), R.string.source_img),
                ResourceUtils.getString(MyApplication.getInstance(), R.string.thumbnail),
                ResourceUtils.getString(MyApplication.getInstance(), R.string.share)};

    }

    private static List<PixivIllustBean> handleResponseBody(ResponseBody responseBody) {

        List<PixivIllustBean> list = new ArrayList<>();

        Document document = null;

        try {
            document = Jsoup.parse(responseBody.string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        responseBody.close();

        if (document != null) {

            Elements elements = document.getElementsByClass("ranking-item");

            for (int i = 0; i < elements.size(); i++) {

                final PixivIllustBean bean = new PixivIllustBean();

                Element element = elements.get(i);

                bean.setId(Integer.parseInt(element.attr("data-id")));
                bean.setTitle(element.attr("data-title"));
                bean.setAuthor(element.attr("data-user-name"));
                bean.setDate(element.attr("data-date"));

                Element link_element = element.getElementsByClass("ranking-image-item").first().select("a").first();
                if (link_element != null) {
                    bean.setLink(Api.PIXIV_URL + "/" + link_element.attr("href"));
                }

                Element thumbnail_element = element.getElementsByClass("_layout-thumbnail").first().select("img").first();

                if (thumbnail_element != null) {
                    bean.setImg_240x480(thumbnail_element.attr("data-src"));

                    if (bean.getImg_240x480() != null) {

                        bean.setImg_600x600(bean.getImg_240x480().replace("/c/240x480/", "/c/600x600/"));
                        bean.setImg_1200x1200(bean.getImg_240x480().replace("/c/240x480/", "/c/1200x1200/"));
                        bean.setImg_original(bean.getImg_240x480().replace("/c/240x480/img-master/", "/img-original/").replace("_master1200", ""));

                    }
                }

                list.add(bean);
            }
        }

        return list;
    }


}
