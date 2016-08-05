package com.zhanghao.wcx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;



public class WelcomeStartAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        /*
        new Handler(new Handler.Callback() {
            //处理接收到消息的方法
            @Override
            public boolean handleMessage(Message msg) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));//所需参数分别为上下文对象和类对象
                return false;
            }
        }).sendEmptyMessageDelayed(0, 3000);//延时三秒
        */
        Timer timer=new Timer();
        timer.schedule(new Task(),3000);
    }
    class Task extends TimerTask{
        @Override
        public void run() {
          startActivity(new Intent(WelcomeStartAct.this,MainActivity.class));
        }
    }
}
