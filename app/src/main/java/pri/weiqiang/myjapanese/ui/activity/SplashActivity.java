package pri.weiqiang.myjapanese.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import abc.abc.abc.AdManager;
import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SplashViewSettings;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;
import abc.abc.abc.nm.sp.SpotRequestListener;

import butterknife.BindView;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.manager.DBManager;
import pri.weiqiang.myjapanese.manager.GifManager;
import pri.weiqiang.myjapanese.manager.SoundPoolManager;
import pri.weiqiang.myjapanese.utils.PermissionHelper;
import pri.weiqiang.myjapanese.utils.ResourceUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.tv_tips)
    TextView mTextView;
    private PermissionHelper mPermissionHelper;
    Context mContext;
    @Override
    protected int getViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariable(@Nullable Bundle savedInstanceState) {

        }

    @Override
    protected void doAction() {
        //加载有米广告
        mContext = SplashActivity.this;


        //预加载数据库，音频与GIF资源（GIF资源觉得可以放在后边）
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onStart();
                subscriber.onNext(ResourceUtils.getString(SplashActivity.this, R.string.loading_data));
                DBManager.getInstance().init();
                SoundPoolManager.getInstance().init();
                GifManager.getInstance().init();
                subscriber.onNext(ResourceUtils.getString(SplashActivity.this, R.string.loading_data_success));
                subscriber.onCompleted();

            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                          //由有米广告来负责跳转，否则，未跳转至MainActivity.class就闪退了
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText(ResourceUtils.getString(SplashActivity.this, R.string.loading_data_error));
                    }

                    @Override
                    public void onNext(String s) {
                        mTextView.setText(s);
                    }
                });
        // 当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            // 如果系统版本低于23，直接跑应用的逻辑
            Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
            runApp();
        } else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
                runApp();
            } else {
                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(mContext).onDestroy();
    }

    private void hideState() {

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(mContext).init("4e431d57328d8acd", "1193a1c50762b8eb", true);
        preloadAd();
        setupSplashAd(); // 如果需要首次展示开屏，请注释掉本句代码
    }

    /**
     * 预加载广告
     */
    private void preloadAd() {
        // 注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
        SpotManager.getInstance(mContext).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
                Log.e(TAG,"请求插播广告成功");
                //				// 应用安装后首次展示开屏会因为本地没有数据而跳过
                //              // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
//                				setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode) {
                Log.e(TAG,("请求插播广告失败，errorCode: %s"+errorCode));
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
//                        showShortToast("网络异常");
                        Toast.makeText(mContext,"网络异常",Toast.LENGTH_SHORT);
                        break;
                    case ErrorCode.NON_AD:
//                        showShortToast("暂无视频广告");
                        Toast.makeText(mContext,"暂无视频广告",Toast.LENGTH_SHORT);
                        break;
                    default:
//                        showShortToast("请稍后再试");
                        Toast.makeText(mContext,"请稍后再试",Toast.LENGTH_SHORT);
                        break;
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 创建开屏容器
        final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);

        // 对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        //		// 设置是否展示失败自动跳转，默认自动跳转
        //		splashViewSettings.setAutoJumpToTargetWhenShowFailed(false);
        // 设置跳转的窗口类
        splashViewSettings.setTargetClass(MainActivity.class);
        // 设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);

        // 展示开屏广告
        SpotManager.getInstance(mContext)
                .showSplash(mContext, splashViewSettings, new SpotListener() {

                    @Override
                    public void onShowSuccess() {
                        Log.e(TAG,"开屏展示成功");
                    }

                    @Override
                    public void onShowFailed(int errorCode) {
                        Log.e(TAG,("开屏展示失败"));
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                Log.e(TAG,("网络异常"));
                                break;
                            case ErrorCode.NON_AD:
                                Log.e(TAG,("暂无开屏广告"));
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                Log.e(TAG,("开屏资源还没准备好"));
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                Log.e(TAG,("开屏展示间隔限制"));
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                Log.e(TAG,("开屏控件处在不可见状态"));
                                break;
                            default:
                                Log.e(TAG,("errorCode: %d"+errorCode));
                                break;
                        }
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onSpotClosed() {
                        Log.e(TAG,"开屏被关闭");
                    }

                    @Override
                    public void onSpotClicked(boolean isWebPage) {
                        Log.e(TAG,"开屏被点击");
//                        Log.e(TAG,"是否是网页广告？%s", isWebPage ? "是" : "不是");
                    }
                });
    }
}
