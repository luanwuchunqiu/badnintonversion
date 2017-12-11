package com.myweather.app.badmintonversion;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import cn.bmob.v3.datatype.BmobDate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        BmobDate bmobDate = new BmobDate(new Date());
        System.out.println(bmobDate.getDate().split(" ")[0]+"================");
        Date date = new Date();
        String week = new SimpleDateFormat("EEEE").format(date);
        String localDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);

        System.out.println(bmobDate.getDate().split(" ")[0].equals(localDate));
        System.out.println(week+"\t"+localDate+"\t"+day);
        System.out.println(localDate.equals("2017-10-25"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bmobDateNotS = new Date();

            bmobDateNotS = simpleDateFormat.parse("2017-11-01");
        System.out.print(bmobDateNotS+"\n"+c.get(Calendar.WEEK_OF_YEAR));

        SimpleDateFormat sdfweek = new SimpleDateFormat("EEEE");
        Date mDate = simpleDateFormat.parse("2017-11-12");
        System.out.print(sdfweek.format(mDate));
        TreeSet set = new TreeSet();

        assertEquals(4, 2 + 2);
    }
}