package pri.weiqiang.myjapanese;

import org.junit.Test;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.BaiduTranslateBean;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustBean;
import pri.weiqiang.myjapanese.mvp.bean.YoudaoTranslateBean;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.PixivIllustFragmentModelImpl;
import pri.weiqiang.myjapanese.network.baidu.BaiduTranslateApi;
import pri.weiqiang.myjapanese.network.baidu.service.BaiduService;
import pri.weiqiang.myjapanese.network.baidu.service.BaiduTranslateServiceImpl;
import pri.weiqiang.myjapanese.network.pixiv.PixivIllustApi;
import pri.weiqiang.myjapanese.network.youdao.service.YoudaoService;
import pri.weiqiang.myjapanese.network.youdao.service.YoudaoTranslateServiceImpl;
import rx.Subscriber;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testRamdom() {

        System.out.print("\t");

    }


    @Test
    public void testBaiduTranslate() {

        BaiduService.TranslateService service = new BaiduTranslateServiceImpl();
        service.translate("可愛い", BaiduTranslateApi.JP, BaiduTranslateApi.ZH, new Subscriber<BaiduTranslateBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(BaiduTranslateBean baiduTranslateBean) {
                System.out.print(baiduTranslateBean.toString());
            }
        });

    }

    @Test
    public void testYoudaoTranslate() {

        YoudaoService.YoudaoTranslateService service = new YoudaoTranslateServiceImpl();
        service.translate("可愛い", new Subscriber<YoudaoTranslateBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(YoudaoTranslateBean youdaoTranslateBean) {
                System.out.print(youdaoTranslateBean.toString());
            }
        });

    }

    @Test
    public void testPixiv() {

        BaseModel.PixivIllustFragmentModel model = new PixivIllustFragmentModelImpl();
        model.getIllusts(PixivIllustApi.MODE_MONTHLY, new Subscriber<List<PixivIllustBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<PixivIllustBean> pixivIllustBeen) {
                for (PixivIllustBean bean : pixivIllustBeen) {

                    System.out.println(bean.toString());
                }
            }
        });

        System.out.println("OK");

    }


}