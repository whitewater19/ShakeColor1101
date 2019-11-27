package com.example.shakecolor1101;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

//3extends Service(選android.app)
//4紅,implements方法onBind
//5還要監聽服務何時發生,所以在extends Service後implements SensorEventListener
//6紅,implements方法onSensorChanged跟onAccuracyChanged
public class ShakeService extends Service implements SensorEventListener {
//7在類別裡按右鍵產生OverrideMethods,選onStartCommand

//8宣告(5)
    private SensorManager mSensorManager;//控制感應器
    private Sensor mAccelerometer;//加速度感應器
    private float mAccel; //除重力外的加速度
    private float mAccelCurrent; //當前加速度,包括重力
    private float mAccelLast; //最後的加速度,包括重力
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //棋子(辨識啟動什麼服務)
    public int onStartCommand(Intent intent, int flags, int startId) {
        //啟動服務                                    //ID
        //類似onCreate(啟動service前跟系統說我要做什麼事)
        //一開始的指令

//9(4)
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //找系統預設感應器
        mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_UI, new Handler());
        //註冊                                                        //DELAY延遲緩慢偵測   Handler類似任務,幫忙處理服務

//10改系統預設常數為START_STICKY(使用sensor時,intent會傳過來,START_STICKY是不保留intent)
        return START_STICKY;
        //常數
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //註冊Sensor後,當Sensor改變(數值),動作:Linearlayout變色
        //通過感應器獲得3個浮點型               //event:Sensor傳過來的(事件)
//11設3變數及程式(共7)
        float x = event.values[0];//x軸 左右移動(重力)
        float y = event.values[1];//y軸 前後移動
        float z = event.values[2];//x軸 垂直方向的加速度
        mAccelLast = mAccelCurrent;
        //最後的加速度  = 當前加速度
        mAccelCurrent = (float)Math.sqrt((double)(x*x+y*y+z*z));
        //平方根
        float delta = mAccelCurrent - mAccelLast;
        //計算變化後的加速度
        mAccel = mAccel * 0.9f + delta;
        //計算加速度的公式

        //得計算加速度結果後,要寫判斷式:(加速度大於多少會變色)
//12先回MainActivity指定square(LinearLayout)
//14寫判斷式(4)
        if (mAccel > 11) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            //255代表完全不透明

            MainActivity.square.setBackgroundColor(color);

        }
    }
//15回MainActivity啟動服務
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //加速

    }
}
