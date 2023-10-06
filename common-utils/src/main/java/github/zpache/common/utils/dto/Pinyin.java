package github.zpache.common.utils.dto;

/**
 * @desc: 汉语拼音实体
 * @author: wang bing
 * @createTime: 2022/11/17 19:33
 */
public class Pinyin {

    private String fullPinyin;
    private String simplePinyin;

    public Pinyin(String fullPinyin, String simplePinyin) {
        this.fullPinyin = fullPinyin;
        this.simplePinyin = simplePinyin;
    }

    public String getFullPinyin() {
        return fullPinyin;
    }

    public void setFullPinyin(String fullPinyin) {
        this.fullPinyin = fullPinyin;
    }

    public String getSimplePinyin() {
        return simplePinyin;
    }

    public void setSimplePinyin(String simplePinyin) {
        this.simplePinyin = simplePinyin;
    }
}
