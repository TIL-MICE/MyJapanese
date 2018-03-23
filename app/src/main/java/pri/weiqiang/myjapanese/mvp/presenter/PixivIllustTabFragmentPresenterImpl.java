package pri.weiqiang.myjapanese.mvp.presenter;

import android.content.DialogInterface;

import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.manager.SharedPreferenceManager;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.PixivIllustTabFragmentModelImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;

public class PixivIllustTabFragmentPresenterImpl extends BasePresenter<BaseView.PixivIllustTabFragmentView> implements BasePresenter.PixivIllustTabFragmentPresenter {


    BaseModel.PixivIllustTabFragmentModel model;

    public PixivIllustTabFragmentPresenterImpl(BaseView.PixivIllustTabFragmentView view) {
        super(view);
        model = new PixivIllustTabFragmentModelImpl();
    }

    @Override
    public void initPixivIllustTabFragment() {

        if (MyApplication.ISWIFI) {

            view.setData(model.getData());


        } else {

            if (SharedPreferenceManager.getInstance().getBoolean(Constants.FLAG_TIPS_WIFI, true)) {

                view.showAlertDialog(pri.weiqiang.myjapanese.R.string.warn,
                        pri.weiqiang.myjapanese.R.string.warn_no_wifi, pri.weiqiang.myjapanese.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferenceManager.getInstance().putBoolean(Constants.FLAG_TIPS_WIFI, false);
                                SharedPreferenceManager.getInstance().putBoolean(Constants.ALLOW_CONNECT_WITHOUT_WIFI, true);
                                dialog.dismiss();
                                view.setData(model.getData());
                            }
                        }, pri.weiqiang.myjapanese.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferenceManager.getInstance().putBoolean(Constants.FLAG_TIPS_WIFI, false);
                                SharedPreferenceManager.getInstance().putBoolean(Constants.ALLOW_CONNECT_WITHOUT_WIFI, false);
                                dialog.dismiss();
                                view.showMsg(pri.weiqiang.myjapanese.R.string.loading_disallow_pixiv);
                            }
                        });

            } else {

                if (SharedPreferenceManager.getInstance().getBoolean(Constants.ALLOW_CONNECT_WITHOUT_WIFI, false)) {

                    view.setData(model.getData());

                } else {
                    view.showMsg(pri.weiqiang.myjapanese.R.string.loading_disallow_pixiv);
                }

            }

        }


    }
}
