package utils;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by p on 2016/11/14.
 */
public class Format_Times {

    public StringBuilder mFormatBuilder;
    public Formatter mFormatter;

    public Format_Times(){
        //转成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }
    //把毫秒转换成 时：分：秒  的形式
    public String changeHaoMiaoToString(int haoMiao){
        int total_second = haoMiao/1000;
        int seconds = total_second % 60;
        int minutes = (total_second/60) % 60;
        int hours = total_second/3600;
        mFormatBuilder.setLength(0);
        if(hours > 0){
            return mFormatter.format("%d:%02d:%02d",hours,minutes,seconds).toString();
        }else {
            return mFormatter.format("%02d:%02d",minutes,seconds).toString();
        }

    }
}
