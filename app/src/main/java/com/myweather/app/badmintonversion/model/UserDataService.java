package com.myweather.app.badmintonversion.model;

import android.provider.ContactsContract;
import android.util.Log;

import com.myweather.app.badmintonversion.application.BaseApplication;
import com.myweather.app.badmintonversion.dao.UserDataDao;
import com.myweather.app.badmintonversion.dao.UserDataDaoImpl;
import com.myweather.app.badmintonversion.dao.UserDataLocalDao;
import com.myweather.app.badmintonversion.dao.UserDataLocalDaoImpl;
import com.myweather.app.badmintonversion.entity.Moment;
import com.myweather.app.badmintonversion.entity.MomentItem;
import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.entity.UserData;
import com.myweather.app.badmintonversion.entity.UserDataLocal;
import com.myweather.app.badmintonversion.entity.UserDataSuper;
import com.myweather.app.badmintonversion.entity.UserItem;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by zyt on 2017/10/25.
 */

public class UserDataService {
    private UserDataDaoImpl userDataDao;
    private UserDataLocalDao userDataLocalDao;
    private UserLocal userLocal;
    {
        userDataDao = new UserDataDaoImpl();
        userDataLocalDao = new UserDataLocalDaoImpl();
        userLocal = BaseApplication.getUserLocal();
    }

   //将数据保存到云端
    public void save(String userId, List<String> data, final BaseListener baseListener){
        userDataDao.save(userId, data,baseListener);
    }
    //将数据保存或是更新到云端


    public void saveOrUpdate(String userId, List<String> data, final BaseListener baseListener){
        userDataDao.saveOrUpdate(userId,data,baseListener);
    }



    /**
     * 从云端读取UserData的数据
     * 最好的解决办法是让userData和userDataLocal实现继承同一个
     * */
/**
 * 个人的数据优先从本地数据库取，如果没有在从服务器取数据，
 * 如果是取所有人的数据那就直接从云端取数据
 * */


    /**
     * @return   从本地数据库中取得今天的一条数据如果今天没有数据返回null
     */
    public UserDataLocal getUserDataLocalLate(){
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(today);
        UserDataLocal userDataLocal = DataSupport.findLast(UserDataLocal.class);

        //查到了今天的数据
        if(userDataLocal.getDate().equals(todayString)){
            return userDataLocal;
        }
        else {return null;}

}

    /**
     * 通过userId查找到userData
     * @param userId
     * @param baseListener
     */
   public void findUserDataByUserId(String userId,BaseListener baseListener){
       userDataDao.findByUserId(userId,baseListener);
   }


   public UserDataSuper findUserDataToday(String userId){
       final Boolean[] flag = {false};
       final UserDataSuper userDataSuper = new UserDataSuper();
       userDataDao.findByUserId(userId, new BaseListener() {
           @Override
           public void getSuccess(Object o) {
               UserData userData = (UserData) o;
               Date today = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");
               String todayWeek = sdfWeek.format(today);
               String todayString = sdf.format(today);
               //如果有今天的数据
               if(userData.getUpdatedAt().split(" ")[0].equals(todayString)){
                   userDataSuper.setDate(todayString);
                   userDataSuper.setWeek(todayWeek);
                   String data = "";
                    List<String> userDataToday = userData.getData().get(userData.getData().size()-1);
                   for(int i =0;i<userDataToday.size()-1;i++){
                       data=data+userDataToday.get(i)+",";
                   }
                   data=data+userDataToday.get(userDataToday.size()-1);
                   userDataSuper.setData(data);
                   flag[0] = true;

               }else {flag[0]=false;}

           }

           @Override
           public void getFailure(Exception e) {
                flag[0]= false;
           }
       });
       if(flag[0]){
           return userDataSuper;
       }else {return null;}
   }


    /**
     * 得到一周的数据
     * @param userId
     */
    /**
     *
     * @param userId
     * @return 返回一周的数据 以数组的形式，周一的数据为UserDataSuper[0];周日的数据为UserDataSuper[6],如果没有数据则为null;
     * */
   public void getAWeekUserData(String userId, final BaseListener baselistener){
       final Boolean[] flag = {false};

       final UserDataSuper[] userDataSupers = new UserDataSuper[7];
       userDataDao.findByUserId(userId, new BaseListener() {
           @Override
           public void getSuccess(Object o) {

               Date today = new Date();
               Calendar cToday = Calendar.getInstance();cToday.setTime(today);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");
               UserData userData = (UserData) o;
               List<List<String>> userDatas = userData.getData();
               String lateDateString = userDatas.get(userDatas.size()-1).get(0);
               Date lateDate = new Date();
               try {
                  lateDate = sdf.parse(lateDateString);
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               Calendar cLateDate = Calendar.getInstance(); cLateDate.setTime(lateDate);
               //如果取得的数据是这一周的
               if(cLateDate.get(Calendar.WEEK_OF_YEAR)==(cToday.get(Calendar.WEEK_OF_YEAR))){
                   String week = sdfWeek.format(lateDate);
                   int size = userDatas.size();
                   if(size>=7){
                       List<List<String>> winDatas = new ArrayList<List<String>>();
                       for(int i=7;i>0;i--){
                           winDatas.add(userDatas.get(size-i));
                       }
                      for(int i =0;i<7;i++){
                          String theDateString = winDatas.get(i).get(0);
                          Date theDate = new Date();
                          try {
                              theDate = sdf.parse(theDateString);
                          } catch (ParseException e) {
                              e.printStackTrace();
                          }
                          Calendar cTheDate = Calendar.getInstance();cTheDate.setTime(theDate);
                          if(cTheDate.get(Calendar.WEEK_OF_YEAR)==(cToday.get(Calendar.WEEK_OF_YEAR))){
                              UserDataSuper userDataSuper = new UserDataSuper();
                              switch (sdfWeek.format(theDate)){
                                  case "星期一":{userDataSuper.setData(listString2String(winDatas.get(i)));
                                  userDataSupers[0] = userDataSuper;}break;
                                  case "星期二":{
                                      userDataSuper.setData(listString2String(winDatas.get(i)));
                                      userDataSupers[1] = userDataSuper;
                                  }break;
                                  case "星期三":{
                                      userDataSuper.setData(listString2String(winDatas.get(i)));
                                      userDataSupers[2] = userDataSuper;
                                  }break;
                                  case "星期四":{
                                      userDataSuper.setData(listString2String(winDatas.get(i)));
                                      userDataSupers[3] = userDataSuper;
                                  }break;
                                  case "星期五":{
                                      userDataSuper.setData(listString2String(winDatas.get(i)));
                                      userDataSupers[4] = userDataSuper;
                                  }break;
                                  case "星期六":{
                                      userDataSuper.setData(listString2String(winDatas.get(i)));
                                      userDataSupers[5] = userDataSuper;
                                  }break;
                                  case "星期日":{
                                      userDataSuper.setData(listString2String(winDatas.get(i)));
                                      userDataSupers[6] = userDataSuper;
                                  }break;
                              }

                          }
                      }

                       baselistener.getSuccess(userDataSupers);
                   }
                   //如果数据的数量小于7
                   else {
                       List<List<String>> winDatas = new ArrayList<List<String>>();
                       for(int i = size;i>0;i--){
                           winDatas.add(userDatas.get(size-i));
                       }
                       for(int i=0;i<size;i++){
                           String theDateString = winDatas.get(i).get(0);
                           Date theDate = new Date();
                           try {
                               theDate = sdf.parse(theDateString);
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                           Calendar cTheDate = Calendar.getInstance();cTheDate.setTime(theDate);
                           if(cTheDate.get(Calendar.WEEK_OF_YEAR)==(cToday.get(Calendar.WEEK_OF_YEAR))){
                               UserDataSuper userDataSuper = new UserDataSuper();
                               switch (sdfWeek.format(theDate)){
                                   case "星期一":{userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[0] = userDataSuper;}break;
                                   case "星期二":{
                                       userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[1] = userDataSuper;
                                   }break;
                                   case "星期三":{
                                       userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[2] = userDataSuper;
                                   }break;
                                   case "星期四":{
                                       userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[3] = userDataSuper;
                                   }break;
                                   case "星期五":{
                                       userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[4] = userDataSuper;
                                   }break;
                                   case "星期六":{
                                       userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[5] = userDataSuper;
                                   }break;
                                   case "星期日":{
                                       userDataSuper.setData(listString2String(winDatas.get(i)));
                                       userDataSupers[6] = userDataSuper;
                                   }break;
                               }
                           }
                       }
                       baselistener.getSuccess(userDataSupers);

                   }


               }else{
                   flag[0] = false;
                   baselistener.getFailure(null);
               }



           }

           @Override
           public void getFailure(Exception e) {
               flag[0] = false;
               baselistener.getFailure(e);
           }
       });


   }

    /**
     * 得到排好序的UserItem,然后可以直接呈现给用户
     * @param condition 选择要排序的属性
     *
     * @return 返回List<UserItem> 数据供ListView使用
     */
   public void getUserItemList(final String condition, final BaseListener baselistener){
       final List<UserItem> userItemList = new ArrayList<>();
       final TreeSet<UserItem> userItemSet = new TreeSet<>();
       userDataDao.getUserListByOrderCondition(condition, new BaseListener() {
           @Override
           public void getSuccess(Object o) {
               final List<UserData> userDataList = (List<UserData>) o;
               for(int i = 0;i<userDataList.size();i++){
                    userDataDao.getUserItemByUserData(userDataList.get(i), condition, new BaseListener() {
                     @Override
                     public void getSuccess(Object o) {

                         userItemList.add((UserItem) o);

                         if(userItemList.size() == userDataList.size()){
                             Collections.sort(userItemList);
                             baselistener.getSuccess(userItemList);
                         }
                        /* userItemSet.add((UserItem) o);
                         if(userItemSet.size()==userDataList.size()){
                             for(UserItem u:userItemSet)
                             {userItemList.add(u);}
                             baselistener.getSuccess(userItemList);
                            }*/

                     }

                     @Override
                     public void getFailure(Exception e) {

                     }
                 });


               }


           }

           @Override
           public void getFailure(Exception e) {
                    baselistener.getFailure(e);
           }
       });

   }

public String listString2String(List<String> listString){
    String data = "";

                   for(int i =0;i<listString.size()-1;i++){
        data=data+listString.get(i)+",";
    }
    data=data+listString.get(listString.size()-1);
    return data;
}

    /**
     * 从本地
     * 得到最近的数据，以便得出本周数据看、和本月数据
     */
    public UserDataSuper getRecentlyData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        Calendar todayCalendar = Calendar.getInstance();todayCalendar.setTime(todayDate);
        UserDataSuper userDataSuper = new UserDataSuper();
        UserDataLocal userDataLocal
         = DataSupport.findLast(UserDataLocal.class);
        //在本地查询出了数据
        if(userDataLocal!=null){
            String dateString = userDataLocal.getDate();
            Date date = new Date();
            try {
                 date = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();calendar.setTime(date);
            //如果是这周的数据
            if(calendar.get(Calendar.WEEK_OF_YEAR)==todayCalendar.get(Calendar.WEEK_OF_YEAR)){


                userDataSuper.setThisWeekData4Count(Integer.parseInt(userDataLocal.getThisWeekData().split(",")[0]));
                userDataSuper.setThisWeekData4Cal(Integer.parseInt(userDataLocal.getThisWeekData().split(",")[1]));
                userDataSuper.setThisWeekData4Time(Double.parseDouble(userDataLocal.getThisWeekData().split(",")[2]));
            }
            else {userDataSuper.setThisWeekData4Count(0);
                userDataSuper.setThisWeekData4Cal(0);
                userDataSuper.setThisWeekData4Time(0.0);}
            if(calendar.get(Calendar.MONTH)==todayCalendar.get(Calendar.MONTH)){


                userDataSuper.setThisMonthData4Count(Integer.parseInt(userDataLocal.getThisMonthData().split(",")[0]));
                userDataSuper.setThisMonthData4Cal(Integer.parseInt(userDataLocal.getThisMonthData().split(",")[1]));
                userDataSuper.setThisMonthData4Time(Double.parseDouble(userDataLocal.getThisMonthData().split(",")[2]));
            }
            else {userDataSuper.setThisMonthData4Count(0);
                userDataSuper.setThisMonthData4Cal(0);
                userDataSuper.setThisMonthData4Time(0.0);}
            if(calendar.get(Calendar.YEAR)==todayCalendar.get(Calendar.YEAR)){


                userDataSuper.setAllData4Count(Integer.parseInt(userDataLocal.getAllData().split(",")[0]));
                userDataSuper.setAllData4Cal(Integer.parseInt(userDataLocal.getAllData().split(",")[1]));
                userDataSuper.setAllData4Time(Double.parseDouble(userDataLocal.getAllData().split(",")[2]));
            }
            else {userDataSuper.setAllData4Count(0);
                userDataSuper.setAllData4Cal(0);
                userDataSuper.setAllData4Time(0.0);}
            return userDataSuper;


        }
        else return null;

    }

    /**
     *从服务器段采取的数据
     */
    public void getRecentlyDataFromServer(String userId, final BaseListener baseListener){
        final UserDataSuper userDataSuper = new UserDataSuper();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        final Calendar todayCalendar = Calendar.getInstance();todayCalendar.setTime(todayDate);
        BmobQuery<UserData> query = new BmobQuery();
        query.addWhereEqualTo("userId",userId);
        query.findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {
                if(e==null){
                    UserData userData = list.get(list.size()-1);
                    String dateString = userData.getUpdatedAt().split(" ")[0];
                    Date date = new Date();
                    try {
                        date = sdf.parse(dateString);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();calendar.setTime(date);
                    //如果是这周的数据
                    if(calendar.get(Calendar.WEEK_OF_YEAR)==todayCalendar.get(Calendar.WEEK_OF_YEAR)){


                        userDataSuper.setThisWeekData4Count(userData.getThisWeekData4Count());
                        userDataSuper.setThisWeekData4Cal(userData.getThisWeekData4Cal());
                        userDataSuper.setThisWeekData4Time(userData.getThisWeekData4Time());
                    }
                    else {userDataSuper.setThisWeekData4Count(0);
                        userDataSuper.setThisWeekData4Cal(0);
                        userDataSuper.setThisWeekData4Time(0.0);}
                    if(calendar.get(Calendar.MONTH)==todayCalendar.get(Calendar.MONTH)){


                        userDataSuper.setThisMonthData4Count(userData.getThisMonthData4Count());
                        userDataSuper.setThisMonthData4Cal(userData.getThisMonthData4Cal());
                        userDataSuper.setThisMonthData4Time(userData.getThisMonthData4Time());
                    }
                    else {userDataSuper.setThisMonthData4Count(0);
                        userDataSuper.setThisMonthData4Cal(0);
                        userDataSuper.setThisMonthData4Time(0.0);}
                    if(calendar.get(Calendar.YEAR)==todayCalendar.get(Calendar.YEAR)){


                        userDataSuper.setAllData4Count(userData.getAllData4Count());
                        userDataSuper.setAllData4Cal(userData.getAllData4Cal());
                        userDataSuper.setAllData4Time(userData.getAllData4Time());
                    }
                    else {userDataSuper.setAllData4Count(0);
                        userDataSuper.setAllData4Cal(0);
                        userDataSuper.setAllData4Time(0.0);}

                    baseListener.getSuccess(userDataSuper);
                }
                else {
                    baseListener.getFailure(e);
                }

            }
        });


    }


    /**
     * 从服务端找到所有的朋友圈信息
     */
    public void getMomentList(final BaseListener baseListener){
        final BmobQuery<Moment> query = new BmobQuery();

        query.findObjects(new FindListener<Moment>() {
            @Override
            public void done(final List<Moment> list, final BmobException e) {
                if(e==null){
                    final List<MomentItem> momentItemlist =  new ArrayList<MomentItem>();
                    for(final Moment moment:list){
                        String userId = moment.getUserId();
                        getUserById(userId, new BaseListener() {
                            @Override
                            public void getSuccess(Object o) {
                                User user = (User) o;
                                MomentItem momentItem = new MomentItem();
                                momentItem.setUsername(user.getUsername());
                                if(user.getUserPhoto()!=null){
                                    momentItem.setUserPhotoUrl(user.getUserPhoto().getUrl());
                                }else{momentItem.setUserPhotoUrl(null);}
                                List<BmobFile> photos = moment.getPhotos();
                                List<String> photosUrls = new ArrayList<String>();
                                if(photos!=null){
                                    for(BmobFile bmobFile:photos){
                                        photosUrls.add(bmobFile.getUrl()+"");
                                    }
                                }
                                momentItem.setPhotosUrls(photosUrls);
                                momentItem.setContent(moment.getMomentText());
                                momentItemlist.add(momentItem);
                                if(momentItemlist.size() == list.size())
                                baseListener.getSuccess(momentItemlist);
                            }

                            @Override
                            public void getFailure(Exception e) {
                                baseListener.getFailure(e);
                                Log.d("F1", "getFailure: "+e.toString());

                            }
                        });


                    }
                    }
                else{
                    baseListener.getFailure(e);
                    Log.d("F2", "getFailure: "+e.toString());
                }

            }
        });

    }

    /**
     *
     */
    public void getUserById(String userId, final BaseListener baseListener){
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(userId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    baseListener.getSuccess(user);
                }
                else{
                    baseListener.getFailure(e);
                }

            }
        });
    }



}
