package com.example.huhu.universityactivity.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/4/29.
 */
public class NetUtil {

    public static boolean checkNet(Context context){

        //获取手机所以连接管理对象（包括wi-fi，net等连接的管理）
        ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            //网络管理链接对象
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            //判断网络是否链接
            if(networkInfo!=null&&networkInfo.isConnected()){
                return true;
            }
        }
        return false;
    }
}
