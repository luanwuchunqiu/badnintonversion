package com.myweather.app.badmintonversion.view.fragment.main;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.myweather.app.badmintonversion.PopupActivity;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.application.BaseApplication;
import com.myweather.app.badmintonversion.dao.UserDataDaoImpl;
import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.entity.UserData;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.DataBuffer;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserDataService;
import com.myweather.app.badmintonversion.service.UartService;
import com.myweather.app.badmintonversion.view.activity.DeviceListActivity;
import com.myweather.app.badmintonversion.view.activity.SportsAnalysisActivity;

import org.apache.commons.math3.distribution.LogisticDistribution;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by zyt on 2017/9/7.
 */

public class SportsFragment extends Fragment implements View.OnClickListener {


    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    public static final String TAG = "nRFUART";
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;
    private UserDataService userDataService;
    private TextView deviceNameTextView;
        private int[] result;
    private String today;
    private UserLocal userLocal;

    private BluetoothDevice mDevice = null;
    private UartService mService = null;//创建服务
    private LinearLayout scanButton;
    private View view;
    private BluetoothAdapter mBtAdapter = null;//蓝牙适配器
    private LinearLayout centerlayout;


    private TextView gaoyuan;
    private TextView pingdang;
    private TextView pingchou;
    private TextView kousha;
    private TextView tiaoqiu;
    private TextView allCount;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sports,container,false);
        centerlayout = (LinearLayout) view.findViewById(R.id.center_layout) ;
        scanButton = (LinearLayout) view.findViewById(R.id.scan_button);
        deviceNameTextView = (TextView) view.findViewById(R.id.device_name);
        gaoyuan = (TextView) view.findViewById(R.id.gaoyuan_count);
        pingdang = (TextView) view.findViewById(R.id.pingdang_count);
        pingchou = (TextView) view.findViewById(R.id.pingchou_count);
        kousha = (TextView) view.findViewById(R.id.kousha_count);
        tiaoqiu = (TextView) view.findViewById(R.id.tiaoqiu_count);
        allCount = (TextView) view.findViewById(R.id.total_count);
        deviceNameTextView.setOnClickListener(this);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        centerlayout.setOnClickListener(this);
        scanButton.setOnClickListener(this);
        userDataService = new UserDataService();
        init();
        service_init();
        Animation alphaAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.scanbutton);


        scanButton.startAnimation(alphaAnimation);



        return view;
    }

    public void init(){
        Date date = new Date();
        //String week = new SimpleDateFormat("EEEE").format(date);
         today = new SimpleDateFormat("yyyy-MM-dd").format(date);

    }














    private void  service_init() {
        Intent bindIntent = new Intent(getActivity(), UartService.class);
        getActivity().bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//将该activity与UartService绑定
        //连接成功后将调用 mServiceConnection 的onServiceConnection函数

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        intentFilter.addAction(DataBuffer.SEND_RESULT);
        return intentFilter;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            mDevice = mService.getmDevice();

            Log.d("mServiceConnection", "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e("mServiceConnection", "Unable to initialize Bluetooth");
                getActivity().finish();
            }

        }

        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };


    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d("ChangeReceiver", "UART_CONNECT_MSG");
                        //连上蓝牙的回掉函数

                    }
                });
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        //断开链接的回掉
                        mService.close();
                        //setUiState();

                    }
                });
            }


            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
            }
            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            //String text = new String(txValue, "UTF-8");
                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                            //收到数据的回掉
                           /* listAdapter.add("["+currentDateTimeString+"] RX: "+byteArray2String(txValue));
                            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);*/

                        } catch (Exception e) {
                            Log.e("异常", e.toString());
                        }
                    }
                });
            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
                //不支持蓝牙设备的回掉
                mService.disconnect();
            }
            if(action.equals(DataBuffer.SEND_RESULT)){


                //final int result[] ;
                result = intent.getIntArrayExtra(DataBuffer.RESULT_DATA);

                        kousha.setText(result[4]+"");
                        gaoyuan.setText(result[3]+"");
                        tiaoqiu.setText(result[2]+"");
                        pingchou.setText(result[1]+"");
                        pingdang.setText(result[0]+"");
                        allCount.setText(result[4]+result[3]+result[2]+result[1]+result[0]+"");


                Log.d(TAG, "onReceive: "+Arrays.toString(result));


            }

        }
    };





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_SELECT_DEVICE:
                //When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);//得到从DeviceListActivity 所选择的设备
                    mService.setmDevice(mDevice);
                    Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);


                    mService.connect(deviceAddress);//连接设备
                   /* String a = "on";
                    byte[] value = null;
                    try {
                        value = a.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    mService.writeRXCharacteristic(value);*/


                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getActivity(), "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

                } else {
                    // User did not enable Bluetooth or an error occurred

                    Toast.makeText(getActivity(), "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:

                break;
        }
    }








    //字节数组转换为字符串
    public static String byteArray2String(byte[] b){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < b.length; i++) {
            stringBuilder.append(b[i]);
        }
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //分析
            case R.id.center_layout:{
                startActivity(new Intent(getActivity(), SportsAnalysisActivity.class));
            }break;
            case R.id.device_name:{


                String a = "on";
                byte[] value = null;
                try {
                    value = a.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mService.writeRXCharacteristic(value);
            }break;

            case R.id.scan_button:{

                if(!mService.isConnected()){
                if (!mBtAdapter.isEnabled()) {
                    Log.i("scan", "onClick - BT not enabled yet");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                else {
                    {

                        //Connect button pressed, open DeviceListActivity class, with popup windows that scan for devices

                        Intent newIntent = new Intent(getActivity(), DeviceListActivity.class);
                        startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
                    } /*else {
                        //Disconnect button pressed
                        if (mDevice!=null)
                        {
                            mService.disconnect();

                        }
                    }*/
                }
            }else{
                    showPopupMenu(v);
                }
            }

            break;
        }
    }
    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.device, popupMenu.getMenu());

        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.connect_device:{
                        if(!mService.isConnected()){
                            mService.connect(mDevice.getAddress());
                        }


                    }break;
                    case R.id.disconnect_device:{
                        if (mDevice!=null)
                        {
                            mService.disconnect();

                        }
                    }break;
                    case R.id.send_data:{
                        if(mService.isConnected()){
                            String a = "on";
                            byte[] value = null;
                            try {
                                value = a.getBytes("UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            mService.writeRXCharacteristic(value);
                        }
                    }break;
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(result!=null) {
            userLocal = BaseApplication.getUserLocal();
            final List<String> data = new ArrayList<String>();
            data.add(today);
            for (int i = 0; i < result.length; i++) {
                data.add(result[i] + "");
            }
            int count = 0;
            for(int i = 0;i<result.length;i++){count+=result[i];}
            //挥拍总次数
            data.add(count+"");
            //卡路里
            data.add(count*3+"");
            data.add(7+"");
           //进行数据的更新或者保存
            //如果能查出数据就进行更新否则进行保存
            userDataService.saveOrUpdate( userLocal.getObjectId(), data, new BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    Log.d(TAG, "getSuccess: 更新成功");
                    Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void getFailure(Exception e) {

                }
            });

          /*  userDataService.saveOrUpdate( "c4a730543b", data, new BaseListener() {
                @Override
                public void getSuccess(Object o) {

                }

                @Override
                public void getFailure(Exception e) {

                }
            });*/
        }

                Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {

        super.onResume();
        if(mDevice!=null)
            deviceNameTextView.setText(mDevice.getName());
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

}
