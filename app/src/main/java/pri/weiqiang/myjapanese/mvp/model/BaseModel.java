package pri.weiqiang.myjapanese.mvp.model;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.BaiduTranslateBean;
import pri.weiqiang.myjapanese.mvp.bean.BannerItem;
import pri.weiqiang.myjapanese.mvp.bean.Book;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.mvp.bean.GojuonTab;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustBean;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustTab;
import pri.weiqiang.myjapanese.mvp.bean.TranslateSpinnerItem;
import pri.weiqiang.myjapanese.mvp.bean.Word;
import rx.Subscriber;


public interface BaseModel<T> {

    List<T> getData();


    interface MainActivityModel extends BaseModel<BannerItem> {
    }


    interface JPStartTabFragmentModel extends BaseModel<GojuonTab> {
    }

    interface JPStartFragmentModel {
        void getData(int category, Subscriber<List<GojuonItem>> subscriber);
    }

    interface WordsFragmentModel {
        void getData(Subscriber<List<Word>> subscriber,String lesson);
    }

    interface LessonsFragmentModel {
        void getData(Subscriber<List<Book>> subscriber);
    }

    interface TranslateFragmentModel {

        List<TranslateSpinnerItem> getFromList();

        List<TranslateSpinnerItem> getToList();

        void translate(String q, String from, String to, Subscriber<BaiduTranslateBean> subscriber);

    }

    interface PixivIllustFragmentModel {

        void getIllusts(String mode, Subscriber<List<PixivIllustBean>> subscriber);

        String[] getOptions();

    }

    interface PixivIllustTabFragmentModel extends BaseModel<PixivIllustTab> {
    }

    interface MemoryFragmentModel {

        List<GojuonItem> getQingYinWithoutHeader();

        List<GojuonItem> getZhuoYinWithoutHeader();

        List<GojuonItem> getAoYinWithoutHeader();


    }

    interface PuzzleActivityModel {
        String[] getOptions();

        List<GojuonItem> getItems();
    }

}
