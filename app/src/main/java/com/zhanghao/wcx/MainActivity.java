package com.zhanghao.wcx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import  com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.ViewUtils;
import zhanghao.fragment.FragmentHome;
import zhanghao.fragment.FragmentMy;
import zhanghao.fragment.FragmentSearch;
import zhanghao.fragment.FragmentTuan;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;
    @ViewInject(R.id.main_home)
    private RadioButton main_home;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        fragmentManager=getSupportFragmentManager();//初始化
        main_home.setChecked(true);//第一个底部按钮被选中
        group.setOnCheckedChangeListener(this);//设置监听

        ChangeFragment(new FragmentHome(),false);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        switch (checkId){
            case R.id.main_home:
                ChangeFragment(new FragmentHome(),true);
                break;
            case R.id.main_tuan:
                ChangeFragment(new FragmentTuan(),true);
                break;
            case R.id.main_search:
                ChangeFragment(new FragmentSearch(),true);
                break;
            case R.id.main_my:
                ChangeFragment(new FragmentMy(),true);
                break;
            default:break;
        }
    }
    //切换不同的Fragment
    public void ChangeFragment(Fragment fragment,boolean isFirst){
        //开启事务

        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        if(!isFirst){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
