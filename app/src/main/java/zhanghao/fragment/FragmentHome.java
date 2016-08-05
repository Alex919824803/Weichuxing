package zhanghao.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhanghao.wcx.R;

import java.io.IOException;
import java.util.List;

import Utils.SharedUtils;

import static android.R.id.list;
import static android.R.id.shareText;

public class FragmentHome extends Fragment implements LocationListener {
    @ViewInject(R.id.index_top_city)
    private TextView topCity;
    private String cityName;
    private LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_index, null);
        ViewUtils.inject(this, view);
        //获取数据并且显示
        topCity.setText(SharedUtils.getCityName(getActivity()));
        return view;
    }

    private void checkGPSIsOpen() {
        //获取当前的locationManager对象
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isOpen) {
            //进入gps设置页面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCALE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }
        //开始
        startLocation();
    }

    private void startLocation() {
        PackageManager pkm = getActivity().getPackageManager();
        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                == pkm.checkPermission("android.permission.RECORD_AUDIO", "com.qdtevc.teld.app"));
        if (has_permission) {
            //调用需要的权限可能被用户拒绝，所以这时调用该方法会出现异常
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);


        }else {
            // showToast("没有权限");
            Toast.makeText(getActivity(), "没有设置权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //检查当前gps模块状态
        checkGPSIsOpen();
    }

    //接受并且处理消息
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what==1){
                topCity.setText(cityName);
            }
            return false;
        }
    });

    //获取对应位置的经纬度并且定位城市
    private  void  updateWithNewLocation(Location location){
        double lat=0.0,lng=0.0;
        if (location !=null ){
            lat=location.getLatitude();
            lng=location.getLongitude();
            Log.i("TAG","经度是："+lat+",纬度是"+lng);
        }else{
            cityName="无法获取城市信息";
        }
        //通过经纬度获取地址，由于地址会有多个，这个与经纬度的精确度有关，本示实例中定义了最大的返回数2，即在集合对象中有两个值
        List<Address> list=null;
        Geocoder ge=new Geocoder(getActivity());
        try{
            list=ge.getFromLocation(lat,lng,2);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                Address address=list.get(i);
                cityName=address.getLocality();//获取城市
            }
        }
        //发送空消息
        handler.sendEmptyMessage(1);
    }

    //位置信息更改执行的方法
    @Override
    public void onLocationChanged(Location location) {
        //更新当前的位置信息
        updateWithNewLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    //可用时执行的方法
    @Override
    public void onProviderEnabled(String s) {

    }

    //不可用时执行的方法
    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存当前城市
        SharedUtils.putCityName(getActivity(),cityName);
        //停止定位
        stopLocation();
    }
    //停止定位
    private  void  stopLocation(){

        PackageManager pkm = getActivity().getPackageManager();
        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                == pkm.checkPermission("android.permission.RECORD_AUDIO", "com.qdtevc.teld.app"));
        if (has_permission) {
            locationManager.removeUpdates(this);
        }else {
          // showToast("没有权限");
            Toast.makeText(getActivity(), "没有设置权限", Toast.LENGTH_SHORT).show();
        }
    }
}
