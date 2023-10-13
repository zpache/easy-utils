package github.zpache.common.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @desc:
 * @author: zpache
 * @createTime: 2023/10/6 16:36
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String formatNumber(String pattern, Object number) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(number);
    }

    /**
     * 获取随机整型字符串
     * @param length 字符串长度
     */
    public static String getRandomInt(int length) {
        int[] array = new int[length];
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * 10);
            str.append(array[i]);
        }
        return str.toString();
    }

    /**
     * 获取随机字符串
     * @param length 字符串长度
     */
    public static String getRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 手机号隐位处理
     *
     * @param phone 手机号
     */
    public static String hidePhone(String phone) {
        if (isNotEmpty(phone)) {
            return phone.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3");
        }
        return "";
    }

    /**
     * 身份证号脱敏处理
     *
     * @param cardNo 身份证号
     */
    public static String hideCardNo(String cardNo) {
        if (isNotEmpty(cardNo)) {
            return cardNo.replaceAll("(\\d)(\\d{16})(\\d|[Xx])", "$1**************$3");
        }
        return "";
    }

    /**
     * 邮箱隐位处理
     *
     * @param email 邮箱
     */
    public static String hideEmail(String email) {
        if (isNotEmpty(email)) {
            String[] emailArray = email.split("@");
            if (emailArray[0].length() > 3) {
                String prefixEmail = emailArray[0].replaceAll("([a-zA-Z0-9_]{2})([a-zA-Z0-9_]*)([a-zA-Z0-9_])", "$1****$3");
                return prefixEmail + "@" + emailArray[1];
            } else {
                String prefixEmail = emailArray[0].replaceAll("([a-zA-Z0-9])([a-zA-Z0-9_]*)", "$1**");
                return prefixEmail + "@" + emailArray[1];
            }
        }
        return "";
    }


}
