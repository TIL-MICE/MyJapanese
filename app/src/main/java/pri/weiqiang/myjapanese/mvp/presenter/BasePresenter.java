package pri.weiqiang.myjapanese.mvp.presenter;

import java.util.List;

import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.mvp.bean.PixivIllustBean;
import pri.weiqiang.myjapanese.rxbus.event.EventContainer;

public abstract class BasePresenter<T> {

    public final T view;

    public BasePresenter(T view) {
        this.view = view;
    }

    public interface MainActivityPresenter {
        void initMainActivity();

        void onRadioButtonChanged(int position);

        void onNavigationItemSelected(int id);

        void onBusEventInteraction(EventContainer eventContainer);

        void onMenuItemSelected(int id);

    }

    public interface GojuonTabFragmentPresenter {

        void initJPStartTabFragment();

    }

    public interface WordsFragmentPresenter {

        void initWordsFragment();

    }

    public interface LessonsFragmentPresenter {

        void initLessonsFragment();

    }

    public interface GojuonFragmentPresenter {

        void initJPStartFragment(int category);

    }

    public interface TranslateFragmentPresenter {

        void initTranslateFragment();

        void checkFromLanguate(int from);

        void checkToLanguage(int to);

        void checkImageViewClick(int id);

        void doTranslate();

    }

    public interface PixivIllustTabFragmentPresenter {
        void initPixivIllustTabFragment();
    }

    public interface PixivIllustFragmentPresenter {
        void initPixivIllustFragment(String mode);

        void reloadData(String mode);

        void onItemClick(PixivIllustBean bean);
    }

    public interface MemoryFragmentPresenter {

        void initMemoryFragment();

        void loadMore(int category);

        void setDate(int category);

    }

    public interface PuzzleActivityPresenter {

        void initPuzzleActivity();

        void loadData();

        void checkTypeSelect(int which);

        void checkAnswerSelect(int id, GojuonItem current, List<GojuonItem> items);

        void checkMenuSelect(int id);


    }


}
