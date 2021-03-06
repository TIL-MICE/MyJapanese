package pri.weiqiang.myjapanese.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.manager.SharedPreferenceManager;
import pri.weiqiang.myjapanese.mvp.bean.Book;
import pri.weiqiang.myjapanese.mvp.bean.Lesson;
import pri.weiqiang.myjapanese.mvp.presenter.BasePresenter;
import pri.weiqiang.myjapanese.mvp.presenter.LessonsFragmentPresenterImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import pri.weiqiang.myjapanese.ui.activity.MainActivity;
import pri.weiqiang.myjapanese.ui.adapter.LeftMenuAdapter;
import pri.weiqiang.myjapanese.ui.adapter.RightMenuAdapter;

/**
 * Created by weiqiang on 2018/3/16.
 */

public class LessonsFragment extends BaseFragment implements BaseView.LessonsFragmentView
        , LeftMenuAdapter.onItemSelectedListener {

    private static final String TAG = LessonsFragment.class.getSimpleName();
    @BindView(R.id.left_menu)
    RecyclerView mLeftMenu;//左侧菜单栏
    @BindView(R.id.right_menu)
    RecyclerView mRightMenu;//右侧菜单栏
    @BindView(R.id.tv_book)
    TextView headerView;
    @BindView(R.id.ll_right_menu_head)
    LinearLayout headerLayout;//右侧菜单栏最上面的菜单
    @BindView(R.id.main_layout)
    RelativeLayout mMainLayout;
    BasePresenter.LessonsFragmentPresenter presenter;
    private Book headMenu;
    private LeftMenuAdapter leftAdapter;
    private RightMenuAdapter rightAdapter;
    private List<Book> mBookList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动


    @Override
    protected int getViewId() {
        return R.layout.fragment_lessons;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        presenter = new LessonsFragmentPresenterImpl(this);
    }

    @Override
    protected void doAction() {
        presenter.initLessonsFragment();
    }

    @Override
    public void setData(List<Book> data) {
        //来自initAdapter
        mBookList = data;
        leftAdapter = new LeftMenuAdapter(getActivity(), data);
        rightAdapter = new RightMenuAdapter(getActivity(), data);

        rightAdapter.setOnItemClickListener(new RightMenuAdapter.OnItemClickListener() {
            @Override
            public void onClick(Lesson item) {
                Log.e(TAG,"lesson:"+item.getTitle());
                SharedPreferenceManager.getInstance().putString(Constants.CURRENT_LESSON,item.getTitle());
                ((MainActivity)getActivity()).switchWords(item.getTitle());
            }
        });
        mRightMenu.setAdapter(rightAdapter);
        mRightMenu.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mLeftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);
        initHeadView();
    }

    @Override
    public void initView() {
        mLeftMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRightMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mLeftMenu.setHasFixedSize(true);
        mRightMenu.setHasFixedSize(true);
        mRightMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e(TAG,"onScrolled start");
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;
                if (dy > 0) {
                    underView = mRightMenu.findChildViewUnder(headerLayout.getX(), headerLayout.getMeasuredHeight() + 1);
                } else {
                    underView = mRightMenu.findChildViewUnder(headerLayout.getX(), 0);
                }
                if (underView != null && underView.getContentDescription() != null) {
                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    Book menu = rightAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getName().equals(headMenu.getName())) {
                        if (dy > 0 && headerLayout.getTranslationY() <= 1 && headerLayout.getTranslationY() >= -1 * headerLayout.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else if (dy < 0 && headerLayout.getTranslationY() <= 0 && !leftClickType) {
                            headerView.setText(menu.getName());
                            int dealtY = underView.getBottom() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else {
                            headerLayout.setTranslationY(0);
                            headMenu = menu;
                            headerView.setText(headMenu.getName());
                            for (int i = 0; i < mBookList.size(); i++) {
                                if (mBookList.get(i) == headMenu) {
                                    leftAdapter.setSelectedNum(i);
                                    break;
                                }
                            }
                            if (leftClickType) leftClickType = false;
                            Log.e(TAG, "onScrolled: " + menu.getName());
                        }
                    }
                }
                Log.e(TAG,"onScrolled end");
            }
        });
    }

    private void showHeadView() {
        headerLayout.setTranslationY(0);
        View underView = mRightMenu.findChildViewUnder(headerView.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            Book menu = rightAdapter.getMenuOfMenuByPosition(position + 1);
            headMenu = menu;
            headerView.setText(headMenu.getName());
            for (int i = 0; i < mBookList.size(); i++) {
                if (mBookList.get(i) == headMenu) {
                    leftAdapter.setSelectedNum(i);
                    break;
                }
            }
        }
    }


    private void initHeadView() {
        headMenu = rightAdapter.getMenuOfMenuByPosition(0);
        headerLayout.setContentDescription("0");
        headerView.setText(headMenu.getName());
    }

    @Override
    public void onLeftItemSelected(int position, Book menu) {
        int sum = 0;
        Long start  = System.currentTimeMillis();
        for (int i = 0; i < position; i++) {
            sum += mBookList.get(i).getLessonList().size() + 1;
        }
        Long end = System.currentTimeMillis();
        Log.e(TAG,"onLeftItemSelected-time: "+(end-start));

        Long start2  = System.currentTimeMillis();
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRightMenu.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;
        Long end2 = System.currentTimeMillis();
        Log.e(TAG,"onLeftItemSelected-time: "+(end2-start2));
    }
}
