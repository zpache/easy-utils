package github.zpache.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @desc:
 * @author: zpache
 * @createTime: 2023/10/6 16:36
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 格式化日期
     * @param date
     * @param pattern
     */
    public static String formatDate(Date date, Object... pattern) {
        if (pattern != null && pattern.length > 0) {
            return DateFormatUtils.format(date, pattern[0].toString());
        }
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取中文周的名称
     * @param date
     */
    public static String getWeekName(Date date) {
        final String[] dayNames = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        return dayNames[dayOfWeek];
    }

    /**
     * 获取两个日期之间的天数
     * @param startDate 开始日期
     * @param endDate 截止日期
     * @return 天数
     */
    public static long getDays(Date startDate, Date endDate) {
        long beforeTime = startDate.getTime();
        long afterTime = endDate.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取两个日期之间的秒
     * @param beginDate 开始日期
     * @param endDate 截止日期
     * @return 秒
     */
    public static long getSeconds(Date beginDate, Date endDate) {
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        return (endTime - beginTime) / 1000;
    }

}
