package pri.weiqiang.myjapanese.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.mvp.bean.Word;
import pri.weiqiang.myjapanese.mvp.presenter.BasePresenter;
import pri.weiqiang.myjapanese.mvp.presenter.WordsFragmentPresenterImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import pri.weiqiang.myjapanese.ui.adapter.WordsRecyclerAdapter;

/**
 * Created by weiqiang on 2018/3/16.
 */

public class WordsFragment extends BaseFragment implements BaseView.WordsFragmentView{

    private static final String TAG = WordsFragment.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_more)
    FloatingActionButton mFabMore;
    BasePresenter.WordsFragmentPresenter presenter;
    WordsRecyclerAdapter adapter;
    String lesson;

    public static WordsFragment newInstance(String lesson) {

        Bundle argument = new Bundle();
        argument.putString(Constants.FLAG_LESSON, lesson);

        WordsFragment fragment = new WordsFragment();
        fragment.setArguments(argument);

        return fragment;

    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_words;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        lesson = getArguments().getString(Constants.FLAG_LESSON);
        Log.e(TAG,"lesson:"+lesson);
        presenter = new WordsFragmentPresenterImpl(this,lesson);
    }

    @Override
    protected void doAction() {
        presenter.initWordsFragment();
    }

    @Override
    public void setData(List<Word> data) {
        adapter = new WordsRecyclerAdapter(getContext(),data);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
