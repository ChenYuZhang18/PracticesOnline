package net.lzzy.practicesonline.activities.constants;

import net.lzzy.practicesonline.activities.utlis.AppUtils;

/**
 * Created by lzzy_gxy on 2019/4/15.
 * Description:
 */
public class ApicContstants {

    private static final String IP= AppUtils.loadServerSetting(AppUtils.getContext()).first;
    private static final String PORT=AppUtils.loadServerSetting(AppUtils.getContext()).second;
    private static final String PROTOCOL="http://";
    /**
     * APT地址
     */
    public static final String URL_API=PROTOCOL.concat(IP).concat(":").concat(PORT);

}