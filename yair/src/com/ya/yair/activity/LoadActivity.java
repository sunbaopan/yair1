
package com.ya.yair.activity;

import java.util.List;

import com.ya.yair.Constant;
import com.ya.yair.EairApplaction;
import com.ya.yair.R;
import com.ya.yair.db.DBManager;
import com.ya.yair.util.UdpHelper;
import com.ya.yair.util.UpdateManager;
import com.ya.yair.util.Util;
import com.ya.yair.util.WebServiceUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LoadActivity extends BaseActivity implements OnClickListener {

    private SharedPreferences storeSP;

    private String mac = "";

    private UpdateManager mUpdateManager;

    private List<String> deviceList;

    // static
    // {
    // System.out.println("load NatTypeJni library");
    // System.loadLibrary("NatTypeJni");
    // }

    @Override
    public void onClick(View arg0) {
       

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        EairApplaction.getInstance().addActivity(this);
        storeSP = this.getSharedPreferences(Constant.STOREDB, 0);
        mac = storeSP.getString(Constant.PHONEMAC, "");
        PackageInfo info;
        // ��������Ƿ�������
        boolean isExistNet = Util.isNetworkConnected(this);
        if (!isExistNet) {// û������
            loadTips();
            return;
        }
        try {
            PackageManager manager = LoadActivity.this.getPackageManager();
            info = manager.getPackageInfo(LoadActivity.this.getPackageName(), 0);
            String version = info.versionName;
            WebServiceUtil wsu = new WebServiceUtil();
            String repMessage = wsu.getVersion(version);
            if (repMessage == null) {// ���Է���2��
                try {
                    Thread.sleep(Constant.SLEEPTIME);
                    repMessage = wsu.getVersion(version);
                    if (repMessage == null) {
                        Thread.sleep(Constant.SLEEPTIME);
                        repMessage = wsu.getVersion(version);
                    }
                } catch (InterruptedException e) {
                    
                    e.printStackTrace();
                }
            }
            if (!("notupd".equals(repMessage)) && repMessage!=null) {// ��Ҫ�����ģ������ͣ����ҳ�ȴ��������������Ժ���ܽ�����һ����
                // ���������汾�Ƿ���Ҫ����
                String mess[] = repMessage.split("&");
                mUpdateManager = new UpdateManager(mess[1], this);
                mUpdateManager.checkUpdateInfo();
                return;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if ("".equals(mac)) {// ����ֻ�����û��mac��ʾ�û���wifi�ķ�ʽ����������ȡmac��ַ
            boolean bool = Util.isWifi(this);
            if (bool) {// ��wifi����
                mac = Util.getLocalMacAddressFromWifiInfo(this);
                Editor editor = storeSP.edit();
                editor.putString(Constant.PHONEMAC, mac);
                editor.commit();// ���ֻ���mac��ַ����
                Thread tt = new Thread(new TimeThread());
                tt.start();
            } else {
                Toast.makeText(this, this.getResources().getString(R.string.notwifi),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Thread tt = new Thread(new TimeThread());
            tt.start();
        }

    }

    /***
     * ������������ô��ת
     */
    private void pdhandle() {
        // ��ȡ�ֻ�����洢���豸
        DBManager db = new DBManager(LoadActivity.this);
        deviceList = db.query();
        if (deviceList == null || deviceList.size() == 0) {// û���豸,��Ҫ�����豸
            Intent intent = new Intent(this, ConfigActivity.class);
            this.finish();
            this.startActivity(intent);
        } else {// ���豸
            Intent intent = new Intent(this, DeviceListActivity.class);
            this.finish();
            this.startActivity(intent);
        }
    }

    private class TimeThread implements Runnable {
        @Override
        public void run() {
            // hQhandle();
            // pdhandle();
            // ��ת����½����
            Intent intent = new Intent(LoadActivity.this, LoginActivity.class);
            LoadActivity.this.finish();
            LoadActivity.this.startActivity(intent);

        }
    }

    /***
     * If current return value equal to NET_TYPE_FULLCONE_NAT, NET_TYPE_REST_NAT
     * and NET_TYPE_PORTREST_NAT, it can support UDP Hole Punching
     * NET_TYPE_OPENED = 0, NET_TYPE_FULLCONE_NAT = 1, // Full Cone NAT
     * NET_TYPE_REST_NAT = 2, // Restricted Cone NAT (restrict IP)
     * NET_TYPE_PORTREST_NAT = 3, // Port Restricted Cone NAT (restrict IP &
     * Port) NET_TYPE_SYM_UDP_FIREWALL = 4, NET_TYPE_SYM_NAT_LOCAL = 5,
     * NET_TYPE_SYM_NAT = 6, NET_TYPE_UDP_BLOCKED = 7, NET_TYPE_ERROR = 8
     ***/
    private static native int getNatType();

    public static native void destroying();

}
