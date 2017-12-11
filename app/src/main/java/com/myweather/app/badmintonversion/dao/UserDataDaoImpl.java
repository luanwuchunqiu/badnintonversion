package com.myweather.app.badmintonversion.dao;

import android.app.DownloadManager;
import android.util.Log;

import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.entity.UserData;
import com.myweather.app.badmintonversion.entity.UserDataLocal;
import com.myweather.app.badmintonversion.entity.UserItem;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zyt on 2017/10/25.
 */

public class UserDataDaoImpl implements UserDataDao {
/**
 * 第一次保存要除了上传到云端数据之外还要创建本地数据库
 *
 * */

    @Override
    public void save(String userId, List<String> data, final BaseListener baseListener) {

        UserDataLocalDao userDataLocalDao = new UserDataLocalDaoImpl();
        //第一次保存
        List<String> thisWeek=new ArrayList<String>();
        List<String> thisMonth = new ArrayList<String>();
        List<String> allData = new ArrayList<String>();
        UserData userData = new UserData();
        userData.setUserId(userId);
        List<List<String>> lists = new ArrayList<List<String>>();
        lists.add(data);

        String initData1 = lists.get(lists.size()-1).get(6);
        String initData2 = lists.get(lists.size()-1).get(7);
        String initData3 = lists.get(lists.size()-1).get(8);

        thisWeek.add(initData1);thisWeek.add(initData2);thisWeek.add(initData3);
        userData.setThisWeekData4Count(Integer.parseInt(initData1));
        userData.setThisWeekData4Cal(Integer.parseInt(initData2));
        userData.setThisWeekData4Time(Double.parseDouble(initData3));

        thisMonth.add(initData1);thisMonth.add(initData2);thisMonth.add(initData3);
        userData.setThisMonthData4Count(Integer.parseInt(initData1));
        userData.setThisMonthData4Cal(Integer.parseInt(initData2));
        userData.setThisMonthData4Time(Double.parseDouble(initData3));

        allData.add(initData1);allData.add(initData2);allData.add(initData3);
        userData.setAllData4Count(Integer.parseInt(initData1));
        userData.setAllData4Cal(Integer.parseInt(initData2));
        userData.setAllData4Time(Double.parseDouble(initData3));

        userData.setData(lists);
        userData.setAllData(allData);
        userData.setThisWeekData(thisWeek);
        userData.setThisMonthData(thisMonth);


        String localData = "";
        for(int i = 0;i<data.size()-1;i++){
            localData = localData+data.get(i)+",";
        }
        localData= localData+data.get(data.size()-1);
        String localThisWeek = thisWeek.get(0)+","+thisWeek.get(1)+","+thisWeek.get(2);
        String localThisMonth = thisMonth.get(0)+","+thisMonth.get(1)+","+thisMonth.get(2);
        String localAllData = allData.get(0)+","+allData.get(1)+","+allData.get(2);
        /**
         * 第一次 将数据保存至本地
         * */
        userDataLocalDao.save(userId,data.get(0),localData,localThisWeek,localThisMonth,localAllData);

        userData.save(new SaveListener<String>() {
            //添加数据返回的 s 为数据的Id
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    baseListener.getSuccess(s);


                }
                else{
                    baseListener.getFailure(e);
                }

            }
        });
    }
/**
 * 如果存在记录就更新记录，如果不存在记录就保存记录
 * */
    @Override
    public void saveOrUpdate(final String userId, final List<String> data, final BaseListener baseListener) {




        findByUserId(userId, new BaseListener() {
            @Override
            public void getSuccess(Object o) {
                UserData userData = (UserData) o;
                update(userData,data,baseListener);

           /*     //日期的字符串格式为 “2017-10-25”
                Date date = new Date();
                //今天是周几
                final String week = new SimpleDateFormat("EEEE").format(date);
                final String localDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

                final Calendar c = Calendar.getInstance();

                c.setTime(date);
                //今天是几号
                final int day = c.get(Calendar.DATE);
                UserData userData = (UserData) o;
                //如果日期是今天就把最后一行数据替换掉，如果日期不是今天就把数据添加进去



                //得到数据user的数据
                List<List<String>> userBmobData = userData.getData();




                String bmobDate = userBmobData.get(userData.getData().size()-1).get(0);
                SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
                Date bmobDateNotS = new Date();

                try {
                    bmobDateNotS = sdf.parse(bmobDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar bmobC = Calendar.getInstance();
                bmobC.setTime(bmobDateNotS);

                if(localDate.equals(bmobDate)){
                    List<String> removeData = userBmobData.remove(userData.getData().size()-1);

                    userBmobData.add(data);
                    List<String> thisWeeks = new ArrayList<String>();
                    List<String> thisMonth = new ArrayList<String>();
                    List<String> allData = new ArrayList<String>();

                    Integer addData1 = Integer.parseInt(userBmobData.get(userBmobData.size()-1).get(6))-Integer.parseInt(removeData.get(6));
                    Integer addData2 = Integer.parseInt(userBmobData.get(userBmobData.size()-1).get(7))-Integer.parseInt(removeData.get(7));
                    Integer addData3 = Integer.parseInt(userBmobData.get(userBmobData.size()-1).get(8))-Integer.parseInt(removeData.get(8));


                        String beforeData1 = thisWeeks.get(0);
                        String beforeData2 = thisWeeks.get(1);
                        String beforeData3 = thisWeeks.get(2);
                        thisWeeks.set(0,addData1+Integer.parseInt(beforeData1)+"");
                        thisWeeks.set(1,addData2+Integer.parseInt(beforeData2)+"");
                        thisWeeks.set(2,addData3+Integer.parseInt(beforeData3)+"");








                        String beforeDataM1 = thisMonth.get(0);
                        String beforeDataM2 = thisMonth.get(1);
                        String beforeDataM3 = thisMonth.get(2);
                        thisWeeks.set(0,addData1+Integer.parseInt(beforeDataM1)+"");
                        thisWeeks.set(1,addData2+Integer.parseInt(beforeDataM2)+"");
                        thisWeeks.set(2,addData3+Integer.parseInt(beforeDataM3)+"");


                    //allData保存
                    String beforeDataA1 = allData.get(0);
                    String beforeDataA2 = allData.get(1);
                    String beforeDataA3 = allData.get(2);
                    allData.set(0,addData1+Integer.parseInt(beforeDataA1)+"");
                    allData.set(1,addData2+Integer.parseInt(beforeDataA2)+"");
                    allData.set(2,addData3+Integer.parseInt(beforeDataA3)+"");
                    final UserData userDataUp = new UserData();
                    userDataUp.setData(userBmobData);
                    userDataUp.setThisWeekData(thisWeeks);
                    userDataUp.setThisMonthData(thisMonth);
                    userDataUp.setAllData(allData);
                    //更新thisweek,thismonth,all的数据


                    userDataUp.update(userData.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                baseListener.getSuccess(userDataUp);
                                Log.i("bmob","更新成功");
                            }else{
                                baseListener.getFailure(e);
                                Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }
                else{
                    userBmobData.add(data);
                    List<String> thisWeeks = new ArrayList<String>();
                    List<String> thisMonth = new ArrayList<String>();
                    List<String> allData = new ArrayList<String>();
                    String addData1 = userBmobData.get(userBmobData.size()-1).get(6);
                    String addData2 = userBmobData.get(userBmobData.size()-1).get(7);
                    String addData3 = userBmobData.get(userBmobData.size()-1).get(8);


                    //当今天是周一或今天与上次保存的时间不是一天
                    if(week.equals("星期一")||(c.get(Calendar.WEEK_OF_YEAR)!=bmobC.get(Calendar.WEEK_OF_YEAR))){
                    thisWeeks.set(0,addData1);
                    thisWeeks.set(1,addData2);
                    thisWeeks.set(2,addData3);}
                    else {
                        String beforeData1 = thisWeeks.get(0);
                        String beforeData2 = thisWeeks.get(1);
                        String beforeData3 = thisWeeks.get(2);
                        thisWeeks.set(0,Integer.parseInt(addData1)+Integer.parseInt(beforeData1)+"");
                        thisWeeks.set(1,Integer.parseInt(addData1)+Integer.parseInt(beforeData2)+"");
                        thisWeeks.set(2,Integer.parseInt(addData1)+Integer.parseInt(beforeData3)+"");

                    }


                    //如果今天是一号或者今天与上次的时间不是一个月
                    if(day==1||(c.get(Calendar.MONTH)!=bmobC.get(Calendar.MONTH))){
                        thisMonth.set(0,addData1);
                        thisMonth.set(1,addData2);
                        thisMonth.set(2,addData3);}
                    else {
                        String beforeData1 = thisMonth.get(0);
                        String beforeData2 = thisMonth.get(1);
                        String beforeData3 = thisMonth.get(2);
                        thisWeeks.set(0,Integer.parseInt(addData1)+Integer.parseInt(beforeData1)+"");
                        thisWeeks.set(1,Integer.parseInt(addData1)+Integer.parseInt(beforeData2)+"");
                        thisWeeks.set(2,Integer.parseInt(addData1)+Integer.parseInt(beforeData3)+"");

                    }
                    //allData保存
                    String beforeData1 = allData.get(0);
                    String beforeData2 = allData.get(1);
                    String beforeData3 = allData.get(2);
                    allData.set(0,Integer.parseInt(addData1)+Integer.parseInt(beforeData1)+"");
                    allData.set(1,Integer.parseInt(addData1)+Integer.parseInt(beforeData2)+"");
                    allData.set(2,Integer.parseInt(addData1)+Integer.parseInt(beforeData3)+"");









                    final UserData userDataUp = new UserData();
                    userDataUp.setData(userBmobData);
                    userDataUp.setThisWeekData(thisWeeks);
                    userDataUp.setThisMonthData(thisMonth);
                    userDataUp.setAllData(allData);
                    userDataUp.update(userData.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                baseListener.getSuccess(userDataUp);
                                Log.i("bmob","更新成功");
                            }else{
                                baseListener.getFailure(e);
                                Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }*/


            }

            @Override
            public void getFailure(Exception e) {
                save(userId,data,baseListener);

            }
        });

    }

    @Override
    public void delete(String userId) {

    }
/**
 * 更新数据
 * */
    @Override
    public void update(UserData userData,List<String> data,final BaseListener baseListener) {
        UserDataLocalDao userDataLocalDao = new UserDataLocalDaoImpl();

        //日期的字符串格式为 “2017-10-25”
        Date date = new Date();
        //今天是周几
        final String week = new SimpleDateFormat("EEEE").format(date);
        final String localDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        final Calendar c = Calendar.getInstance();

        c.setTime(date);
        //今天是几号
        final int day = c.get(Calendar.DATE);

        //如果日期是今天就把最后一行数据替换掉，如果日期不是今天就把数据添加进去



        //得到数据user的数据
        List<List<String>> userBmobData = userData.getData();



        /**
         * updateAt 的日期为Bmob类型通过 BmobDate.getdate() 得到的字符串格式为"yyyy-MM-dd HH:mm:ss"
         * 因此通过截取前面的年月日的字符串进行比较即可
         * */
        /*String bmobDate = userData.getUpdatedAt().split(" ")[0];*/
        //数据库的第一次改动
        String bmobDate = userBmobData.get(userData.getData().size()-1).get(0);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
        Date bmobDateNotS = new Date();

        try {
            bmobDateNotS = sdf.parse(bmobDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar bmobC = Calendar.getInstance();
        bmobC.setTime(bmobDateNotS);
        //如果是今天的数据就更新
        if(localDate.equals(bmobDate)){
            List<String> removeData = userBmobData.remove(userData.getData().size()-1);

            userBmobData.add(data);
            List<String> thisWeeks = new ArrayList<String>();
            List<String> thisMonth = new ArrayList<String>();
            List<String> allData = new ArrayList<String>();
            thisWeeks = userData.getThisWeekData();
            thisMonth = userData.getThisMonthData();
            allData = userData.getAllData();

            Integer addData1 = Integer.parseInt(userBmobData.get(userBmobData.size()-1).get(6))-Integer.parseInt(removeData.get(6));
            Integer addData2 = Integer.parseInt(userBmobData.get(userBmobData.size()-1).get(7))-Integer.parseInt(removeData.get(7));
            Double addData3 = Double.parseDouble(userBmobData.get(userBmobData.size()-1).get(8))-Double.parseDouble(removeData.get(8));


            String beforeData1 = thisWeeks.get(0);
            String beforeData2 = thisWeeks.get(1);
            String beforeData3 = thisWeeks.get(2);
            thisWeeks.set(0,addData1+Integer.parseInt(beforeData1)+"");
            thisWeeks.set(1,addData2+Integer.parseInt(beforeData2)+"");
            thisWeeks.set(2,addData3+Double.parseDouble(beforeData3)+"");
            /**
             * 第一次数据库更新thisWeek
             *
             * */
            Integer thisWeek4Count = addData1+Integer.parseInt(beforeData1);
            Integer thisWeek4Cal = addData2+Integer.parseInt(beforeData2);
            Double thisWeek4Time = addData3+Double.parseDouble(beforeData3);








            String beforeDataM1 = thisMonth.get(0);
            String beforeDataM2 = thisMonth.get(1);
            String beforeDataM3 = thisMonth.get(2);
            thisMonth.set(0,addData1+Integer.parseInt(beforeDataM1)+"");
            thisMonth.set(1,addData2+Integer.parseInt(beforeDataM2)+"");
            thisMonth.set(2,addData3+Double.parseDouble(beforeDataM3)+"");

            Integer thisMonth4Count = addData1+Integer.parseInt(beforeDataM1);
            Integer thisMonth4Cal = addData2+Integer.parseInt(beforeDataM2);
            Double thisMonth4Time = addData3+Double.parseDouble(beforeDataM3);


            //allData保存
            String beforeDataA1 = allData.get(0);
            String beforeDataA2 = allData.get(1);
            String beforeDataA3 = allData.get(2);
            allData.set(0,addData1+Integer.parseInt(beforeDataA1)+"");
            allData.set(1,addData2+Integer.parseInt(beforeDataA2)+"");
            allData.set(2,addData3+Double.parseDouble(beforeDataA3)+"");
            Integer allData4Count = addData1+Integer.parseInt(beforeDataA1);
            Integer allData4Cal = addData2+Integer.parseInt(beforeDataA2);
            Double allData4Time = addData3+Double.parseDouble(beforeDataA3);


            final UserData userDataUp = new UserData();
            userDataUp.setData(userBmobData);
            userDataUp.setThisWeekData(thisWeeks);
            userDataUp.setThisMonthData(thisMonth);
            userDataUp.setAllData(allData);

            //设置数据
            userDataUp.setThisMonthData4Cal(thisMonth4Cal);
            userDataUp.setThisMonthData4Count(thisMonth4Count);
            userDataUp.setThisMonthData4Time(thisMonth4Time);

            userDataUp.setThisWeekData4Cal(thisWeek4Cal);
            userDataUp.setThisWeekData4Count(thisWeek4Count);
            userDataUp.setThisWeekData4Time(thisWeek4Time);

            userDataUp.setAllData4Cal(allData4Cal);
            userDataUp.setAllData4Count(allData4Count);
            userDataUp.setAllData4Time(allData4Time);

            //更新thisweek,thismonth,all的数据

            String localData = "";
            for(int i = 0;i<data.size()-1;i++){
                localData = localData+data.get(i)+",";
            }
            localData = localData+data.get(data.size()-1);
            String localThisWeek = thisWeeks.get(0)+","+thisWeeks.get(1)+","+thisWeeks.get(2);
            String localThisMonth = thisMonth.get(0)+","+thisMonth.get(1)+","+thisMonth.get(2);
            String localAllData = allData.get(0)+","+allData.get(1)+","+allData.get(2);


            UserDataLocal userDataLocal = DataSupport.findLast(UserDataLocal.class);
            if(userDataLocal==null){
                userDataLocal = new UserDataLocal();
                userDataLocal.setDate(localDate);
            }
            userDataLocal.setUserId(userData.getUserId());
            userDataLocal.setData(localData);
            userDataLocal.setAllData(localAllData);
            userDataLocal.setThisWeekData(localThisWeek);
            userDataLocal.setThisMonthData(localThisMonth);
            userDataLocal.save();



            userDataUp.update(userData.getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        baseListener.getSuccess(userDataUp);
                        Log.i("bmob","更新成功");
                    }else{
                        baseListener.getFailure(e);
                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });

        }
        //否则就保存
        else{
            userBmobData.add(data);
            List<String> thisWeeks = new ArrayList<String>();
            List<String> thisMonth = new ArrayList<String>();
            List<String> allData = new ArrayList<String>();
            thisWeeks = userData.getThisWeekData();
            thisMonth = userData.getThisMonthData();
            allData = userData.getAllData();

            String addData1 = userBmobData.get(userBmobData.size()-1).get(6);
            String addData2 = userBmobData.get(userBmobData.size()-1).get(7);
            String addData3 = userBmobData.get(userBmobData.size()-1).get(8);


            //当今天是周一或今天与上次保存的时间不是一周
            if(week.equals("星期一")||(c.get(Calendar.WEEK_OF_YEAR)!=bmobC.get(Calendar.WEEK_OF_YEAR))){
                thisWeeks.set(0,addData1);
                thisWeeks.set(1,addData2);
                thisWeeks.set(2,addData3);}
            else {
                String beforeData1 = thisWeeks.get(0);
                String beforeData2 = thisWeeks.get(1);
                String beforeData3 = thisWeeks.get(2);
                thisWeeks.set(0,Integer.parseInt(addData1)+Integer.parseInt(beforeData1)+"");
                thisWeeks.set(1,Integer.parseInt(addData2)+Integer.parseInt(beforeData2)+"");
                thisWeeks.set(2,Double.parseDouble(addData3)+Double.parseDouble(beforeData3)+"");

            }


            //如果今天是一号或者今天与上次的时间不是一个月
            if(day==1||(c.get(Calendar.MONTH)!=bmobC.get(Calendar.MONTH))){
                thisMonth.set(0,addData1);
                thisMonth.set(1,addData2);
                thisMonth.set(2,addData3);}
            else {
                String beforeData1 = thisMonth.get(0);
                String beforeData2 = thisMonth.get(1);
                String beforeData3 = thisMonth.get(2);
                thisMonth.set(0,Integer.parseInt(addData1)+Integer.parseInt(beforeData1)+"");
                thisMonth.set(1,Integer.parseInt(addData2)+Integer.parseInt(beforeData2)+"");
                thisMonth.set(2,Double.parseDouble(addData3)+Double.parseDouble(beforeData3)+"");

            }
            //allData保存
            String beforeData1 = allData.get(0);
            String beforeData2 = allData.get(1);
            String beforeData3 = allData.get(2);
            allData.set(0,Integer.parseInt(addData1)+Integer.parseInt(beforeData1)+"");
            allData.set(1,Integer.parseInt(addData2)+Integer.parseInt(beforeData2)+"");
            allData.set(2,Double.parseDouble(addData3)+Double.parseDouble(beforeData3)+"");


            String localData = "";
            for(int i = 0;i<data.size()-1;i++){
                localData = localData+data.get(i)+",";
            }
            localData = localData+data.get(data.size()-1);
            String localThisWeek = thisWeeks.get(0)+","+thisWeeks.get(1)+","+thisWeeks.get(2);
            String localThisMonth = thisMonth.get(0)+","+thisMonth.get(1)+","+thisMonth.get(2);
            String localAllData = allData.get(0)+","+allData.get(1)+","+allData.get(2);
            userDataLocalDao.save(userData.getUserId(),localDate,localData,localThisWeek,localThisMonth,localAllData);







            final UserData userDataUp = new UserData();
            userDataUp.setData(userBmobData);
            userDataUp.setThisWeekData(thisWeeks);
            userDataUp.setThisMonthData(thisMonth);
            userDataUp.setAllData(allData);
            userDataUp.update(userData.getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        baseListener.getSuccess(userDataUp);
                        Log.i("bmob","更新成功");
                    }else{
                        baseListener.getFailure(e);
                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });

        }
    }

    @Override
    public void findByUserId(String userId, final BaseListener baseListener) {

        BmobQuery<UserData> query = new BmobQuery<UserData>();
        query.addWhereEqualTo("userId",userId);

        query.findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {
                if(e==null){baseListener.getSuccess(list.get(0));

                }else {
                    baseListener.getFailure(e);
                    Log.d("base", e.toString());
                }

            }
        });


    }
/**
 * 通过登录的userId查出UserData实例
 * */
    @Override
    public void getUserData(String userId, BaseListener baseListener) {

        UserData userData = new UserData();
        BmobQuery<UserData> query = new BmobQuery<UserData>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {

            }
        });

    }

    /**
     * 查出所有的数据并进行排序 排序的条件是condition
     * @param condition 排序所需的条件 前面加上-号是表示 降序
     * @param baseListener
     */
    public void getUserListByOrderCondition(final String condition, final BaseListener baseListener){
        String bql = "select * from UserData ";
        final BmobQuery<UserData> query = new BmobQuery<UserData>();
        query.doSQLQuery(bql, new SQLQueryListener<UserData>() {
            @Override
            public void done(BmobQueryResult<UserData> bmobQueryResult, BmobException e) {
                if(e==null){
                   // query.order("-"+condition);
                    baseListener.getSuccess(bmobQueryResult.getResults());

                }

            }
        });
     /*   query.order("-"+condition).findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {
                if(e==null){
                    baseListener.getSuccess(list);
                    Log.d("getUserList", "done: "+list+"+"+list.size());
                }else{

                    baseListener.getFailure(e);
                }
            }
        });*/


    }

    public void getUserItemByUserData(final UserData userData, final String condition, final BaseListener baselistener){
        final UserItem userItem = new UserItem();

        BmobQuery<User> query = new BmobQuery<>();

        query.getObject(userData.getUserId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    if(user.getUserPhoto()!=null)
                    { userItem.setUserPhoto(user.getUserPhoto().getUrl());}
                    userItem.setUserName(user.getUsername());
                    switch (condition){
                        case "thisWeekData4Count":{userItem.setUserData(userData.getThisWeekData4Count()+"");}break;
                        case "thisWeekData4Cal":{userItem.setUserData(userData.getThisWeekData4Cal()+"");}break;
                        case "thisWeekData4Time":{userItem.setUserData(userData.getThisWeekData4Time()+"");}break;
                        case "thisMonthData4Count":{userItem.setUserData(userData.getThisMonthData4Count()+"");}break;
                        case "thisMonthData4Cal":{userItem.setUserData(userData.getThisMonthData4Cal()+"");}break;
                        case "thisMonthData4Time":{userItem.setUserData(userData.getThisMonthData4Time()+"");}break;
                        case "allData4Count":{userItem.setUserData(userData.getAllData4Count()+"");}break;
                        case "allData4Cal":{userItem.setUserData(userData.getAllData4Cal()+"");}break;
                        case "allData4Time":{userItem.setUserData(userData.getAllData4Time()+"");}break;

                    }
                    baselistener.getSuccess(userItem);

                }
                else {

                }

            }
        });


    }




}
