package pri.weiqiang.myjapanese.utils;

import android.content.Context;

public class ResourceUtils {

    private ResourceUtils() {
        throw new RuntimeException("♪(^∇^*)");
    }

    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static float getDimension(Context context, int resId) {

        return context.getResources().getDimension(resId);

    }


}
