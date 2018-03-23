package pri.weiqiang.myjapanese.mvp.view;

import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.BannerItem;
import pri.weiqiang.myjapanese.mvp.bean.Book;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.mvp.bean.GojuonTab;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustBean;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustTab;
import pri.weiqiang.myjapanese.mvp.bean.TranslateSpinnerItem;
import pri.weiqiang.myjapanese.mvp.bean.Word;

public interface BaseView<T> {

    void setData(List<T> data);

    interface MainActivityView {
        void setViewPager(List<BannerItem> data);

        void openDrawer();

        void closeDrawer();

        void switchWords(String lesson);

        void switchLessons();

        void switchGojuon();

        void switchMemory();

        void switchTranslate();

        void switchGame();

        void switchPixivIllust();

        void switchAbout();

        void switchSetting();

        void startPhotoViewActivity(Bundle bundle);

        void startPuzzleActivity();

        void startSupperzzleActivity();

        void showAlertDialog(int titleId, int messageId,
                             int positiveTextId, DialogInterface.OnClickListener positiveButtonListener,
                             int negativeTextId, DialogInterface.OnClickListener negativeButtonListener);
    }

    interface JPStartTabFragmentView extends BaseView<GojuonTab> {
        void scrollViewPager(int position);
    }

    interface JPStartFragmentView extends BaseView<GojuonItem> {

        void setRecyclerView(int type);

    }

    interface WordsFragmentView extends BaseView<Word> {

        void setRecyclerView();

    }

    interface LessonsFragmentView extends BaseView<Book> {

        void initView();

    }
    interface TranslateFragmentView {
        void showMsg(String msg);

        void showMsg(int msg);

        String getSrcText();

        void setSrcEditText(String text);

        String getDstText();

        void setDstTextView(String text);

        void setFromSpinner(List<TranslateSpinnerItem> list);

        void setToSpinner(List<TranslateSpinnerItem> list);
    }

    interface PixivIllustTabFragmentView extends BaseView<PixivIllustTab> {
        void showAlertDialog(int titleId, int messageId,
                             int positiveTextId, DialogInterface.OnClickListener positiveButtonListener,
                             int negativeTextId, DialogInterface.OnClickListener negativeButtonListener);

        void showMsg(String msg);

        void showMsg(int msg);
    }

    interface PixivIllustFragmentView extends BaseView<PixivIllustBean> {

        void showMsg(int msg);

        void showMsg(String msg);

        void showProgress();

        void hideProgress();

        void showOptionsDialog(String[] options, DialogInterface.OnClickListener listener);

        void showImg(String url, int id);

    }

    interface MemoryFragmentView extends BaseView<GojuonItem> {
        void showMsg(int msg);

        void showMsg(String msg);

        void hideFabMenu();

    }

    interface PuzzleActivityView {
        void setData(GojuonItem current, List<GojuonItem> jams);

        void showSelectDialog(String[] selection);

        void showResultDialog(int title, String msg, int icon,
                              int pbt, DialogInterface.OnClickListener pbl,
                              int nbt, DialogInterface.OnClickListener nbl);

        void showDialog(int icon, int title, String msg);

        void setTitle(String title);

        void setTitle(int title);

        void addCount();

        void clearCount();

        void showMsg(int msg);

        void showMsg(String msg);
    }


}
