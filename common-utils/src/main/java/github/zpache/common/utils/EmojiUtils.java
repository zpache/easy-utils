package github.zpache.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @desc: emoji表情
 * @author: wang bing
 * @createTime: 2022/11/17 20:21
 */
public class EmojiUtils {

    /**
     * 将emoji表情替换为*
     * @param source
     */
    public static String filter(String source) {
        return StringUtils.isNotBlank(source) ? source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*") : source;
    }
}
