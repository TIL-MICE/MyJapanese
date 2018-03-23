package pri.weiqiang.myjapanese.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.ui.adapter.GojuonRecyclerAdapter;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.manager.GifManager;
import pri.weiqiang.myjapanese.manager.SoundPoolManager;
import pri.weiqiang.myjapanese.mvp.bean.GojuonGif;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.mvp.presenter.BasePresenter;
import pri.weiqiang.myjapanese.mvp.presenter.GojuonFragmentPresenterImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import pri.weiqiang.myjapanese.utils.ResourceUtils;
import pri.weiqiang.myjapanese.widget.dialog.ImageDialog;

public class GojuonFragment extends BaseFragment implements BaseView.JPStartFragmentView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    BasePresenter.GojuonFragmentPresenter presenter;

    GojuonRecyclerAdapter adapter;

    int category_yin = 0;

    @Override
    protected int getViewId() {
        return R.layout.fragment_gojuon;
    }

    public static GojuonFragment newInstance(int type) {

        Bundle argument = new Bundle();
        argument.putInt(Constants.CATEGORY_YIN, type);

        GojuonFragment fragment = new GojuonFragment();
        fragment.setArguments(argument);

        return fragment;

    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {

        category_yin = getArguments().getInt(Constants.CATEGORY_YIN);

        presenter = new GojuonFragmentPresenterImpl(this);

    }


    @Override
    protected void doAction() {
        presenter.initJPStartFragment(category_yin);
    }

    @Override
    public void setData(List<GojuonItem> data) {

        adapter = new GojuonRecyclerAdapter(data);
        adapter.setOnItemClickListener(new GojuonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(GojuonItem item) {
                SoundPoolManager.getInstance().play(item.getRome());
            }
        });

        adapter.setOnItemLongClickListener(new GojuonRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(GojuonItem item) {

                GojuonGif gif = GifManager.getInstance().getJPGif(item.getRome());
                if (gif != null) {
                    if (MyApplication.TYPE_MING == Constants.TYPE_HIRAGANA) {
                        new ImageDialog.Builder(getContext())
                                .setResId(gif.getHiragana())
                                .override((int) ResourceUtils.getDimension(getContext(), R.dimen.dialog_width),
                                        (int) ResourceUtils.getDimension(getContext(), R.dimen.dialog_height))
                                .create()
                                .show();
                    } else {
                        new ImageDialog.Builder(getContext())
                                .setResId(gif.getKatakana())
                                .override((int) ResourceUtils.getDimension(getContext(), R.dimen.dialog_width),
                                        (int) ResourceUtils.getDimension(getContext(), R.dimen.dialog_height))
                                .create()
                                .show();
                    }
                }


            }
        });

        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void setRecyclerView(int type) {

        switch (type) {
            case Constants.CATEGORY_QINGYIN:
                RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), Constants.COLUMN_QINGYIN);
                mRecyclerView.setLayoutManager(layoutManager1);
            case Constants.CATEGORY_ZHUOYIN:
                RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getContext(), Constants.COLUMN_ZHUOYIN);
                mRecyclerView.setLayoutManager(layoutManager2);
                break;
            case Constants.CATEGORY_AOYIN:
                RecyclerView.LayoutManager layoutManager3 = new GridLayoutManager(getContext(), Constants.COLUMN_AOYIN);
                mRecyclerView.setLayoutManager(layoutManager3);
                break;
            default:
                break;
        }

    }
}
