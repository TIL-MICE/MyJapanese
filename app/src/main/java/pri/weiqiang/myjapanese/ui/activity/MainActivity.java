package pri.weiqiang.myjapanese.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.manager.SharedPreferenceManager;
import pri.weiqiang.myjapanese.ui.adapter.BannerPagerAdapter;
import pri.weiqiang.myjapanese.ui.fragment.GameFragment;
import pri.weiqiang.myjapanese.ui.fragment.GojuonTabFragment;
import pri.weiqiang.myjapanese.ui.fragment.LessonsFragment;
import pri.weiqiang.myjapanese.ui.fragment.MemoryFragment;
import pri.weiqiang.myjapanese.ui.fragment.PixivIllustTabFragment;
import pri.weiqiang.myjapanese.ui.fragment.TranslateFragment;
import pri.weiqiang.myjapanese.manager.ActivityManager;
import pri.weiqiang.myjapanese.mvp.bean.BannerItem;
import pri.weiqiang.myjapanese.mvp.presenter.BasePresenter;
import pri.weiqiang.myjapanese.mvp.presenter.MainActivityPresenterImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import pri.weiqiang.myjapanese.rxbus.RxBus;
import pri.weiqiang.myjapanese.rxbus.event.EventContainer;
import pri.weiqiang.myjapanese.ui.fragment.WordsFragment;
import pri.weiqiang.myjapanese.utils.ResourceUtils;
import me.pwcong.radiobuttonview.view.RadioButtonView;
import me.relex.circleindicator.CircleIndicator;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements BaseView.MainActivityView {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    Menu menuItem;

    ViewPager mBannerViewPager;
    CircleIndicator mCircleIndicator;

    RadioButtonView mRadioButtonView;
    Subscription busSubscription;
    Subscription bannerSubscription;

    private long mExitTime;
    private BasePresenter.MainActivityPresenter presenter;

    boolean registered = false;


    private LessonsFragment mLesssonsFragment;
    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {

        presenter = new MainActivityPresenterImpl(this);

        if (!registered) {

            busSubscription = RxBus.getDefault().toObserverable(EventContainer.class).subscribe(new Action1<EventContainer>() {
                @Override
                public void call(EventContainer eventContainer) {
                    presenter.onBusEventInteraction(eventContainer);
                }
            });

            registered = true;

        }

        initToolbar();
        initRadioButtonView();
        initDrawerLayout();
        initNavigationView();
        initBanner();
        //无效
//        mToolbar.setTitle(SharedPreferenceManager.getInstance().getString(Constants.CURRENT_LESSON, Constants.DEFAULT_LESSON));

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Log.i(TAG, "initToolbar: OK");
    }

    private void initRadioButtonView() {

        mRadioButtonView = (RadioButtonView) getLayoutInflater().inflate(R.layout.button_radio, mToolbar, false);
        mRadioButtonView.setOptions(getRadioButtonOptions());
        mRadioButtonView.setOnRadioButtonChangedListener(new RadioButtonView.OnRadioButtonChangedListener() {
            @Override
            public void onRadioButtonChanged(String s, int i) {
                presenter.onRadioButtonChanged(i);
            }
        });

        Toolbar.LayoutParams params = new Toolbar.LayoutParams((int) (ResourceUtils.getDimension(MainActivity.this, R.dimen.radio_button_width)),
                ViewGroup.LayoutParams.MATCH_PARENT, GravityCompat.END);
        mToolbar.addView(mRadioButtonView, params);

        Log.i(TAG, "initRadioButtonView: OK");
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Log.i(TAG, "initDrawerLayout: OK");
    }

    private void initNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "因为这里，初始化了getItemId():");
                presenter.onNavigationItemSelected(item.getItemId());
                return true;
            }
        });
        Log.i(TAG, "仅改变选择状态，初始化Fragment在doAction()中的presenter.initMainActivity();");
        mNavigationView.setCheckedItem(R.id.item_word);

        Log.i(TAG, "initNavigationView: OK");
    }

    private void initBanner() {

        View view = mNavigationView.getHeaderView(0);

        mBannerViewPager = (ViewPager) view.findViewById(R.id.banner_view_pager);
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_more, menu);
//        menuItem = menu.findItem(R.id.item_vocab);
//        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onMenuItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void doAction() {

        presenter.initMainActivity();

        Log.i(TAG, "完成初始化第一个Fragment doAction: OK");
    }


    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                showSnackBar(mToolbar, R.string.one_more_press_to_exit);

                mExitTime = System.currentTimeMillis();

            } else {
                ActivityManager.getInstance().finishAll();
            }
        }
    }


    @Override
    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
        Log.i(TAG, "openDrawer: OK");
    }

    @Override
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Log.i(TAG, "closeDrawer: OK");
    }

    @Override
    public void switchWords(String lesson) {

//        mToolbar.setTitle(lesson);

        mRadioButtonView.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, WordsFragment.newInstance(lesson)).commit();

        Log.i(TAG, "switchWords: OK");
        mToolbar.setTitle(lesson);
    }

    @Override
    public void switchLessons() {

        mToolbar.setTitle(R.string.drawer_word);

        mRadioButtonView.setVisibility(View.GONE);

        if (mLesssonsFragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new LessonsFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().show(mLesssonsFragment).commit();
        }

        Log.i(TAG, "switchLessons: OK");
    }

    @Override
    public void switchGojuon() {

        mToolbar.setTitle(R.string.jp_start);

        mRadioButtonView.setVisibility(View.VISIBLE);
        //
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new GojuonTabFragment()).commit();

        Log.i(TAG, "switchGojuon: OK");
    }

    @Override
    public void switchMemory() {

        mToolbar.setTitle(R.string.memory);

        mRadioButtonView.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new MemoryFragment()).commit();

        Log.i(TAG, "switchMemory: OK");
    }

    @Override
    public void switchTranslate() {

        mToolbar.setTitle(R.string.translate);

        mRadioButtonView.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new TranslateFragment()).commit();

        Log.i(TAG, "switchTranslate: OK");
    }

    @Override
    public void switchGame() {
        mToolbar.setTitle(R.string.game);

        mRadioButtonView.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new GameFragment()).commit();

        Log.i(TAG, "switchGame: OK");
    }

    @Override
    public void switchPixivIllust() {

        mToolbar.setTitle(R.string.pixiv_illust);

        mRadioButtonView.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new PixivIllustTabFragment()).commit();

        Log.i(TAG, "switchPixivIllust: OK");
    }

    @Override
    public void switchSetting() {

        startActivity(new Intent(MainActivity.this, SettingActivity.class));

        Log.i(TAG, "switchSetting: OK");
    }

    @Override
    public void startPhotoViewActivity(Bundle bundle) {

        Intent intent = new Intent(MainActivity.this, PhotoViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void startPuzzleActivity() {

        Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
        startActivity(intent);

    }

    @Override
    public void startSupperzzleActivity() {
        Intent intent = new Intent(MainActivity.this, SupperzzleActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAlertDialog(int titleId, int messageId,
                                int positiveTextId, DialogInterface.OnClickListener positiveButtonListener,
                                int negativeTextId, DialogInterface.OnClickListener negativeButtonListener) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(positiveTextId, positiveButtonListener)
                .setNegativeButton(negativeTextId, negativeButtonListener)
                .setIcon(R.drawable.ic_lightbulb_outline_black_24dp)
                .create()
                .show();

    }

    @Override
    public void switchAbout() {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));

        Log.i(TAG, "switchAbout: OK");
    }

    private ArrayList<String> getRadioButtonOptions() {

        ArrayList<String> options = new ArrayList<>();
        options.add(ResourceUtils.getString(MainActivity.this, R.string.hiragana));
        options.add(ResourceUtils.getString(MainActivity.this, R.string.katakana));
        return options;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (busSubscription.isUnsubscribed()) {
            busSubscription.unsubscribe();
        }
        if (bannerSubscription.isUnsubscribed()) {
            bannerSubscription.unsubscribe();
        }


    }

    @Override
    public void setViewPager(final List<BannerItem> data) {

        if (bannerSubscription != null && bannerSubscription.isUnsubscribed()) {
            bannerSubscription.unsubscribe();
        }

        mBannerViewPager.setAdapter(new BannerPagerAdapter(getSupportFragmentManager(), data));
        mCircleIndicator.setViewPager(mBannerViewPager);

        bannerSubscription = Observable.timer(10, 10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        int next = (mBannerViewPager.getCurrentItem() + 1) % data.size();
                        mBannerViewPager.setCurrentItem(next);

                    }
                });

    }
}
