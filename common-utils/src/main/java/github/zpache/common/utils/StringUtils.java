package github.zpache.common.utils;

import java.text.DecimalFormat;

/**
 * @desc:
 * @author: wangbing
 * @createTime: 2023/10/6 16:36
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String formatNumber(String pattern, Object number) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(number);
    }
}
