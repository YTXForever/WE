package time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yuh
 * @date 2019-06-30 07:39
 **/
public class DateUtil {

    public static long getForwardBoundry(long time, int interval) {
        int intervalInMillis = interval * 60 * 1000;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        if (time % intervalInMillis == 0) {
            if (c.get(Calendar.MINUTE) == 0) {
                return time;
            } else {
                c.add(Calendar.MINUTE, -interval);
                return c.getTime().getTime();
            }
        }
        int sub = c.get(Calendar.MINUTE) % interval;
        c.add(Calendar.MINUTE, -sub);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime().getTime();
    }

    public static long getPostBoundry(long time, int interval) {
        int intervalInMillis = interval * 60 * 1000;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        if (time % intervalInMillis == 0) {
            if (c.get(Calendar.MINUTE) == 0) {
                c.add(Calendar.MINUTE, interval);
                return c.getTime().getTime();
            } else {
                return time;
            }
        }
        int sub = c.get(Calendar.MINUTE) % interval;
        c.add(Calendar.MINUTE, interval - sub);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime().getTime();
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = simpleDateFormat.parse("20190630000001");
        System.out.println(new Date(getForwardBoundry(parse.getTime(), 5)));
        System.out.println(new Date(getPostBoundry(parse.getTime(), 5)));
    }
}
