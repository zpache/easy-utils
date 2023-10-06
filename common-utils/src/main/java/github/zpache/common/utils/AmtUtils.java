package github.zpache.common.utils;

import java.math.BigDecimal;

/**
 * @desc: 数字金额转中文大写金额
 * @author: wang bing
 * @createTime: 2022/11/17 19:05
 */
public class AmtUtils {

    private static final String[] digits = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] radices = new String[]{"", "拾", "佰", "仟"};
    private static final String[] bigRadices = new String[]{"", "万", "亿", "万"};
    private static final String[] decimals = new String[]{"角", "分"};

    public static String convert2Chinese(BigDecimal amt) {
        String currencyDigits = StringUtils.formatNumber("0.00", amt);
        String integral;
        String decimal;
        StringBuilder outputCharacters;
        String d;
        currencyDigits = currencyDigits.replace("/,/g", "");
        currencyDigits = currencyDigits.replace("/^0+/", "");
        String[] parts = currencyDigits.split("\\.");
        if (parts.length > 1) {
            integral = parts[0];
            decimal = parts[1];
            if (decimal.length() > 2) {
                long dd = Math.round(Double.parseDouble("0." + decimal) * 100.0D);
                decimal = Long.toString(dd);
            }
        } else {
            integral = parts[0];
            decimal = "0";
        }

        outputCharacters = new StringBuilder();
        int i;
        if (Double.parseDouble(integral) > 0.0D) {
            int zeroCount = 0;

            for(i = 0; i < integral.length(); ++i) {
                int p = integral.length() - i - 1;
                d = integral.substring(i, i + 1);
                int quotient = p / 4;
                int modulus = p % 4;
                if ("0".equals(d)) {
                    ++zeroCount;
                } else {
                    if (zeroCount > 0) {
                        outputCharacters.append(digits[0]);
                    }

                    zeroCount = 0;
                    outputCharacters.append(digits[Integer.parseInt(d)]).append(radices[modulus]);
                }

                if (modulus == 0 && zeroCount < 4) {
                    outputCharacters.append(bigRadices[quotient]);
                }
            }
            outputCharacters.append("圆");
        }
        if (Double.parseDouble(decimal) > 0.0D) {
            for(i = 0; i < decimal.length(); ++i) {
                d = decimal.substring(i, i + 1);
                if (!"0".equals(d)) {
                    outputCharacters.append(digits[Integer.parseInt(d)]).append(decimals[i]);
                } else if (i == 0) {
                    outputCharacters.append("零");
                }
            }
        }

        if ("".equals(outputCharacters.toString())) {
            outputCharacters = new StringBuilder("零圆");
        }
        if ("00".equals(decimal)) {
            outputCharacters.append("整");
        }
        outputCharacters.insert(0, "");
        return outputCharacters.toString();
    }

}
