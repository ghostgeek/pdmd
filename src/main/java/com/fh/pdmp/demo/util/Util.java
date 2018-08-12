package pdmp.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/5 0005.
 */
public class Util {

    public static JdbcTemplate jdbcTemplate = new JdbcTemplate();//详细使用说明见Spring JDBCTemplate官方文档

    static {
        //jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/pdmd?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        // 创建JDBC模板
        // 这里也可以使用构造方法
        jdbcTemplate.setDataSource(dataSource);
    }

    /*
    * 将时间转换为时间戳
    */
    public static String dateToStamp(String s, String format) {
        String res = null;
        //"yyyy-MM-dd HH:mm:ss"
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts / 1000);
            return res;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(s+"000");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取当前时间（绝对秒）
     */
    public static long getCurrentTime() {
        return new Date().getTime() / 1000;
    }
}
