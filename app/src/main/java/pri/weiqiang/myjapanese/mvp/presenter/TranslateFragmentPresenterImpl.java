package pri.weiqiang.myjapanese.mvp.presenter;

import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.manager.ClipboardManager;
import pri.weiqiang.myjapanese.mvp.bean.BaiduTranslateBean;
import pri.weiqiang.myjapanese.mvp.model.BaseModel;
import pri.weiqiang.myjapanese.mvp.model.TranslateFragmentModelImpl;
import pri.weiqiang.myjapanese.mvp.view.BaseView;
import pri.weiqiang.myjapanese.network.baidu.BaiduTranslateApi;
import pri.weiqiang.myjapanese.utils.StringUtils;
import rx.Subscriber;

public class TranslateFragmentPresenterImpl extends BasePresenter<BaseView.TranslateFragmentView> implements BasePresenter.TranslateFragmentPresenter {

    BaseModel.TranslateFragmentModel model;

    public TranslateFragmentPresenterImpl(BaseView.TranslateFragmentView view) {
        super(view);
        model = new TranslateFragmentModelImpl();
    }

    @Override
    public void initTranslateFragment() {

        view.setFromSpinner(model.getFromList());
        view.setToSpinner(model.getToList());

    }

    @Override
    public void checkFromLanguate(int from) {
        MyApplication.FROM_LAN = from;
    }

    @Override
    public void checkToLanguage(int to) {
        MyApplication.TO_LAN = to;
    }

    @Override
    public void checkImageViewClick(int id) {

        switch (id) {

            case pri.weiqiang.myjapanese.R.id.iv_src_copy:

                String srcText = view.getSrcText();
                if (!StringUtils.isNullOrEmpty(srcText)) {
                    ClipboardManager.getInstance().setText("label", view.getSrcText());
                    view.showMsg(pri.weiqiang.myjapanese.R.string.copy_successfully);
                }
                break;
            case pri.weiqiang.myjapanese.R.id.iv_src_paste:
                view.setSrcEditText(ClipboardManager.getInstance().getText());
                break;
            case pri.weiqiang.myjapanese.R.id.iv_src_clear:
                view.setSrcEditText("");
                break;
            case pri.weiqiang.myjapanese.R.id.iv_dst_copy:

                String dstText = view.getDstText();
                if (!StringUtils.isNullOrEmpty(dstText)) {
                    ClipboardManager.getInstance().setText("label", view.getDstText());
                    view.showMsg(pri.weiqiang.myjapanese.R.string.copy_successfully);
                }
                break;
            case pri.weiqiang.myjapanese.R.id.iv_dst_clear:
                view.setDstTextView("");
                break;
            default:
                break;
        }

    }

    @Override
    public void doTranslate() {

        String srcText = view.getSrcText();

        if (!StringUtils.isNullOrEmpty(srcText)) {

            String from;

            switch (MyApplication.FROM_LAN) {

                case 0:
                    from = BaiduTranslateApi.AUTO;
                    break;
                case 1:
                    from = BaiduTranslateApi.ZH;
                    break;
                case 2:
                    from = BaiduTranslateApi.EN;
                    break;
                case 3:
                    from = BaiduTranslateApi.JP;
                    break;
                default:
                    from = BaiduTranslateApi.AUTO;
                    break;

            }

            String to;

            switch (MyApplication.TO_LAN) {

                case 0:
                    to = BaiduTranslateApi.ZH;
                    break;
                case 1:
                    to = BaiduTranslateApi.EN;
                    break;
                case 2:
                    to = BaiduTranslateApi.JP;
                    break;
                default:
                    to = BaiduTranslateApi.ZH;
                    break;

            }

            model.translate(view.getSrcText(), from, to, new Subscriber<BaiduTranslateBean>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    view.showMsg(pri.weiqiang.myjapanese.R.string.translate_error);
                    e.printStackTrace();
                }

                @Override
                public void onNext(BaiduTranslateBean baiduTranslateBean) {
                    if (baiduTranslateBean.getError_code() == null) {
                        view.setDstTextView(baiduTranslateBean.getTrans_result()[0].getDst());
                    } else {
                        view.showMsg(baiduTranslateBean.getError_msg());
                    }
                }
            });
        }


    }
}
