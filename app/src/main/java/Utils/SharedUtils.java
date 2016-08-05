package Utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {
    private static final String FILE_NAME="dianping";
    private static final String MODE_NAME="welcome";

    //获取boolen类型的值
    public  static  boolean getWelcomeBoolean(Context context){
        return  context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getBoolean(MODE_NAME,false);
    }

    //写入boolen值
    public  static  void putWelcomeBoolean(Context context,boolean isFirst){
        SharedPreferences.Editor editor=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit();
        editor.putBoolean(MODE_NAME,isFirst);
        editor.commit();
    }
    //写入String的值
    public static void putCityName(Context context,String cityName){
        SharedPreferences.Editor editor=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit();
        editor.putString(MODE_NAME,cityName);
        editor.commit();
    }
    //获取String的值
    public static String getCityName(Context context){
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getString("cityName","选择城市");
    }
}
