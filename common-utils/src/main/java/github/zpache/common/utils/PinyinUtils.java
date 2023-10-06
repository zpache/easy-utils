package github.zpache.common.utils;

import github.zpache.common.utils.dto.Pinyin;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc: 中文转拼音实体类
 * @author: wang bing
 * @createTime: 2022/11/17 19:05
 */
public class PinyinUtils {

    private static Logger logger = LoggerFactory.getLogger(PinyinUtils.class);

    public static Pinyin chinese2Pinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder fullPinyin = new StringBuilder();
        StringBuilder simplePinyin = new StringBuilder();
        char[] chineseCharArray = chinese.toCharArray();
        for (char aChineseCharArray : chineseCharArray) {
            String[] str = null;
            try {
                str = PinyinHelper.toHanyuPinyinStringArray(aChineseCharArray, format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                logger.error("chinese convert pinyin exception. {}", e.getMessage());
            }
            if (null != str) {
                fullPinyin.append(str[0]);
                simplePinyin.append(str[0].charAt(0));
            }
            if (null == str) {
                String regex = "^[0-9]*[a-zA-Z]*+$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(String.valueOf(aChineseCharArray));
                if (matcher.find()) {
                    fullPinyin.append(aChineseCharArray);
                    simplePinyin.append(aChineseCharArray);
                }
            }
        }
        return new Pinyin(fullPinyin.length() > 20 ? fullPinyin.substring(0, 20) : fullPinyin.toString(),
                simplePinyin.length() > 10 ? simplePinyin.substring(0, 10) : simplePinyin.toString());
    }
}
