
package com.ya.yair.activity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ya.yair.Constant;
import com.ya.yair.EairApplaction;
import com.ya.yair.R;
import com.ya.yair.adapter.TimerAdapter;

public class YADeviceHomePageActivity extends BaseActivity
{

    private LinearLayout mBackGround;// ����ɫ
    private ImageView poweron;// ����
    private TextView mEairRunStateText;// ���ڻ�������
    private TextView mEairValueText;
    private ImageView mErrorIconView;
    private TextView location_text;
    private Animation mRotateAnimation;
    private View mRunProgress;
    private SharedPreferences storeSp;
    private int enivQu = 1;

    private LinearLayout windL;// ������Ŀ

    private LinearLayout timerL;// ��ʱ��Ŀ

    private TextView gdText;// �ߵ�

    private TextView zdText;// �е�

    private TextView ddText;// �͵�

    private TextView jyText;// ����

    private LinearLayout timeBack;

    private ImageView yatime;

    // private Button btn_left;

    // private Gallery timer_gallery;

    // private Button btn_right;

    private TextView ltimerText;// 0Сʱ

    private TextView ytimerText;// 1Сʱ

    private TextView stimerText;// 4Сʱ

    private TextView btimerText;// 8Сʱ

    private Time timer;

    // ���ٵ�λ
    private int fw = 1;
    // ��ʱ
    private int time = 1;
    // ģʽ
    private int mode = 1;

    private int pm = 1;

    private String air;

    private int lock = 1;// ��

    private int anion = 1;

    private int swind = 1;// ɨ��

    private int motor = 1;// �Ƿ��й��� 1��û�У�0 ��

    private String phoneMac;

    private String wlsn;

    private String sb;// ���û��µ������豸�б�

    private String userId;

    private LinearLayout lockL;

    private TextView location_pm;
    private TextView pmAndCity;
    private TextView pm_description;
    private boolean isreq = false;// �Ƿ�����

    private ImageView yalockI;

    // �����Ƿ񻬶�
    private float mPosX;

    private float mPosY;

    private float mCurrentPosX;

    private float mCurrentPosY;

    private TextView lockgT;

    private ImageView lockfxI;

    private TextView lockkT;

    private LinearLayout lockh;

    private ImageView err_iconV;

    // ɨ��ͼ��
    private ImageView swindI;

    // ģʽͼ��
    private ImageView modeI;

    // ��ʱͼ��
    private ImageView yatimeI;
    // ������ͼ��
    private ImageView anionI;

    private void findView()
    {
        this.mRunProgress = findViewById(R.id.run_progress);
        this.mEairValueText = ((TextView) findViewById(R.id.eair_value));
        this.mEairRunStateText = ((TextView) findViewById(R.id.eair_run_state));
        this.windL = (LinearLayout) findViewById(R.id.wind);
        this.timerL = (LinearLayout) findViewById(R.id.timer);
        this.location_text = ((TextView) findViewById(R.id.location_text));
        this.poweron = (ImageView) findViewById(R.id.poweron);
        this.mErrorIconView = ((ImageView) findViewById(R.id.err_icon));
        this.mBackGround = ((LinearLayout) findViewById(R.id.eair_bg));
        // ����
        this.gdText = (TextView) findViewById(R.id.gd);
        this.zdText = (TextView) findViewById(R.id.zd);
        this.ddText = (TextView) findViewById(R.id.dd);
        this.jyText = (TextView) findViewById(R.id.jy);
        this.timeBack = (LinearLayout) findViewById(R.id.timerback);
        // this.btn_left = (Button) findViewById(R.id.btn_left);
        // this.timer_gallery = (Gallery) findViewById(R.id.timer_gallery);
        // this.btn_right = (Button) findViewById(R.id.btn_right);
        this.yatime = (ImageView) findViewById(R.id.yatime);
        this.lockL = (LinearLayout) findViewById(R.id.lock);
        this.lockgT = (TextView) findViewById(R.id.lockg);
        this.lockfxI = (ImageView) findViewById(R.id.lockfx);
        this.lockkT = (TextView) findViewById(R.id.lockk);
        this.yalockI = (ImageView) findViewById(R.id.yalock);
        this.lockh = (LinearLayout) findViewById(R.id.lockh);
        // ��ʱ
        this.ltimerText = (TextView) findViewById(R.id.ltimer);
        this.ytimerText = (TextView) findViewById(R.id.ytimer);
        this.stimerText = (TextView) findViewById(R.id.stimer);
        this.btimerText = (TextView) findViewById(R.id.btimer);

        // ������Ϣ
        this.err_iconV = (ImageView) this.findViewById(R.id.err_icon);

        this.swindI = (ImageView) this.findViewById(R.id.swind);

        this.modeI = (ImageView) this.findViewById(R.id.motor);

        this.yatimeI = (ImageView) this.findViewById(R.id.yatime);

        this.anionI = (ImageView) this.findViewById(R.id.anion);
    }

    private void initEairView()
    {

        this.mEairRunStateText.setText(R.string.room_air);
        this.mRunProgress.setVisibility(View.VISIBLE);
        // this.timer_gallery.setAdapter(new TimerAdapter(this));
        // timer_gallery.setSelection(time);
        // ����ʱ��
        this.settime(time);
        // ���÷���
        this.setWind(fw);
        this.mEairValueText.setBackgroundDrawable(null);
        this.changBg();
        this.mErrorIconView.setVisibility(View.INVISIBLE);
        if (lock == 1) {// û����
            lockL.setVisibility(View.GONE);
            windL.setVisibility(View.VISIBLE);
            lockgT.setBackgroundResource(R.drawable.yalock);
            lockfxI.setBackgroundResource(R.drawable.yalockleft);
            lockkT.setBackgroundResource(R.drawable.yalockkd);
        } else {
            lockL.setVisibility(View.VISIBLE);
            windL.setVisibility(View.GONE);
            lockgT.setBackgroundResource(R.drawable.yalockd);
            lockfxI.setBackgroundResource(R.drawable.yalockright);
            lockkT.setBackgroundResource(R.drawable.yalockk);
        }

        if (this.motor == 0) {
            err_iconV.setVisibility(View.VISIBLE);
        } else {
            err_iconV.setVisibility(View.GONE);
        }
        if (mode == 0) {
            modeI.setBackgroundResource(R.drawable.yawind);
        } else {
            modeI.setBackgroundResource(R.drawable.yawindd);
        }

        if (swind == 0) {
            swindI.setBackgroundResource(R.drawable.yakd);
        } else {
            swindI.setBackgroundResource(R.drawable.yak);
        }

        if (anion == 0) {
            anionI.setBackgroundResource(R.drawable.yajjd);
        } else {
            anionI.setBackgroundResource(R.drawable.yajj);
        }

        if (time == 0) {
            yatimeI.setBackgroundResource(R.drawable.yatime);
        } else {
            yatimeI.setBackgroundResource(R.drawable.yatimed);
        }

    }

    private void changBg() {
        if ("A".equals(air)) {
            enivQu = 1;
        } else if ("B".equals(air)) {
            enivQu = 2;
        } else if ("C".equals(air)) {
            enivQu = 3;
        } else if ("x".equals(air)) {
            enivQu = 4;
        }
        if (enivQu == 1) {
            this.mBackGround.setBackgroundResource(R.drawable.yabg_good);
            this.mEairValueText.setText(R.string.air_good);
        }
        else if (enivQu == 4) {
            this.mBackGround.setBackgroundResource(R.drawable.yabg_good);
            this.mEairValueText.setText(R.string.air_good);
        } else if (enivQu == 3) {
            this.mBackGround.setBackgroundResource(R.drawable.yabg_bad);
            this.mEairValueText.setText(R.string.air_bad);
        }
        else if (enivQu == 2) {
            this.mBackGround.setBackgroundResource(R.drawable.yabg_normal);
            this.mEairValueText.setText(R.string.air_normal);
        }

    }

    /***
     * ���÷���
     * 
     * @param fw
     */
    private void settime(int time) {
        ltimerText.setBackgroundDrawable(null);
        ltimerText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        ytimerText.setBackgroundDrawable(null);
        ytimerText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        stimerText.setBackgroundDrawable(null);
        stimerText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        btimerText.setBackgroundDrawable(null);
        btimerText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        yatimeI.setBackgroundResource(R.drawable.yatimed);
        if (time == 0) {
            ltimerText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            ltimerText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
            yatimeI.setBackgroundResource(R.drawable.yatime);
        } else if (time == 1) {
            ytimerText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            ytimerText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        } else if (time == 4) {
            stimerText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            stimerText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        } else if (time == 8) {
            btimerText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            btimerText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        }
    }

    private void setWind(int fw) {
        jyText.setBackgroundDrawable(null);
        jyText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        ddText.setBackgroundDrawable(null);
        ddText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        zdText.setBackgroundDrawable(null);
        zdText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        gdText.setBackgroundDrawable(null);
        gdText.setTextColor(this.getResources().getColor(R.color.color_fonth));
        if (fw == 1) {
            jyText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            jyText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        } else if (fw == 2) {
            ddText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            ddText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        } else if (fw == 3) {
            zdText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            zdText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        } else if (fw == 4) {
            gdText.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.yawindbg));
            gdText.setTextColor(this.getResources().getColor(R.color.color_fontblue));
        }
    }

    /***
     * ��ʼ��ҳ����Ϣ
     */
    private void initWeatherView()
    {
        String weather = EairApplaction.todayWeather;
        String aqi = EairApplaction.aqi;
        String city = EairApplaction.city;
        Object[] object = new Object[3];
        // object[0] = aqi;
        object[0] = city;
        if (weather != null && weather.contains("~")) {
            Object[] objectTem = weather.split("~");
            object[1] = objectTem[0];
            object[2] = objectTem[1];
        } else {
            object[1] = 0;
            object[2] = 1;
        }
        location_text.setText(getString(R.string.format_location_weather, object));

        pmAndCity = (TextView) this.findViewById(R.id.pm);
        pmAndCity.setText(city + this.getResources().getString(R.string.aqibycity));
        location_pm = (TextView) this.findViewById(R.id.location_pm);
        location_pm.setText(EairApplaction.aqi);
        pmMap(EairApplaction.aqi);
    }

    /***
     * ���ض�����ť
     */
    private void loadAnim()
    {
        this.mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.air_speed_rotate);
    }

    private void setListener()
    {

        this.poweron.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&poweron=1&userid="
                        + userId + "&cmd";
                String mess = getRepMessage(cmd);
                if ("timeout".equals(mess) || mess == null) {
                    tips();
                } else if (mess.contains("door_open=1")) {
                    openTips();
                } else {
                    if (timer != null) {
                        timer.cancel();
                    }
                    Intent inte = new Intent(YADeviceHomePageActivity.this,
                            DeviceListActivity.class);
                    Bundle bundle = new Bundle();
                    // bundle.putString("sb", sb);
                    bundle.putString("userId", userId);
                    inte.putExtra("sbbundle", bundle);
                    YADeviceHomePageActivity.this.finish();
                    YADeviceHomePageActivity.this.startActivity(inte);
                }
            }
        });

        // ��ʱ���ĸ���ť��Ӽ���
        this.ltimerText.setOnClickListener(new TimeListenter(0));
        this.ytimerText.setOnClickListener(new TimeListenter(1));
        this.stimerText.setOnClickListener(new TimeListenter(4));
        this.btimerText.setOnClickListener(new TimeListenter(8));
        // �������ĸ���ť��Ӽ���
        this.gdText.setOnClickListener(new WindListenter(4));
        this.zdText.setOnClickListener(new WindListenter(3));
        this.ddText.setOnClickListener(new WindListenter(2));
        this.jyText.setOnClickListener(new WindListenter(1));
        // ����ʱ��Ӽ���
        this.yatime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                windL.setVisibility(View.GONE);
                timerL.setVisibility(View.VISIBLE);
                isreq = false;
            }
        });
        // ��ʱ�ķ����¼�
        this.timeBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                windL.setVisibility(View.VISIBLE);
                timerL.setVisibility(View.GONE);
            }
        });

        // ��ʱ����ť�¼�
        /*
         * btn_left.setOnClickListener(new View.OnClickListener() { public void
         * onClick(View paramAnonymousView) { int pos =
         * timer_gallery.getSelectedItemPosition(); if (pos > 0) {
         * timer_gallery.setSelection(pos - 1); String cmd = "wlsn=" + wlsn +
         * "&mac=" + phoneMac + "&set&timer=" + (pos - 1) % 9 + "&userid=" +
         * userId + "&cmd"; String mess = getRepMessage(cmd); if
         * ("timeout".equals(mess) || mess == null) { tips(); } else if
         * (mess.contains("door_open=1")) { openTips(); } } } });
         */
        // ��ʱ���Ұ�ť�¼�
        /*
         * btn_right.setOnClickListener(new View.OnClickListener() { public void
         * onClick(View paramAnonymousView) { int pos =
         * timer_gallery.getSelectedItemPosition(); if (pos < Integer.MAX_VALUE)
         * { timer_gallery.setSelection(pos + 1); String cmd = "wlsn=" + wlsn +
         * "&mac=" + phoneMac + "&set&timer=" + (pos + 1) % 9 + "&userid=" +
         * userId + "&cmd"; String mess = getRepMessage(cmd); if
         * ("timeout".equals(mess) || mess == null) { tips(); } else if
         * (mess.contains("door_open=1")) { openTips(); } } } });
         * timer_gallery.setCallbackDuringFling(false);
         * timer_gallery.setOnItemSelectedListener(new
         * AdapterView.OnItemSelectedListener() { public void
         * onItemSelected(AdapterView<?> paramAnonymousAdapterView, View
         * paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
         * if (isreq) { String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac +
         * "&set&timer=" + paramAnonymousInt % 9 + "&userid=" + userId + "&cmd";
         * String mess = getRepMessage(cmd); if ("timeout".equals(mess) || mess
         * == null) { tips(); } else if (mess.contains("door_open=1")) {
         * openTips(); } } else { isreq = true; } } public void
         * onNothingSelected(AdapterView<?> paramAnonymousAdapterView) { } });
         */

        lockh.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                // ����
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        mCurrentPosX = event.getX();
                        mCurrentPosY = event.getY();
                        if (mCurrentPosX - mPosX > 0 && Math.abs(mCurrentPosY - mPosY) < 50) {
                            // ����
                            String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&lock="
                                    + 1 + "&userid=" + userId + "&cmd";
                            String mess = getRepMessage(cmd);
                            if ("timeout".equals(mess) || mess == null) {
                                tips();
                            } else if (mess.contains("door_open=1")) {
                                openTips();
                            } else {
                                lockgT.setBackgroundDrawable(YADeviceHomePageActivity.this
                                        .getResources().getDrawable(R.drawable.yalock));
                                lockfxI.setBackgroundDrawable(YADeviceHomePageActivity.this
                                        .getResources().getDrawable(R.drawable.yalockleft));
                                lockkT.setBackgroundDrawable(YADeviceHomePageActivity.this
                                        .getResources().getDrawable(R.drawable.yalockkd));
                                lockL.setVisibility(View.GONE);
                                windL.setVisibility(View.VISIBLE);
                            }
                        } else if (mCurrentPosX - mPosX < 0 && Math.abs(mCurrentPosY - mPosY) < 50) {
                            // ������
                            // ����
                            String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&lock="
                                    + 0 + "&userid=" + userId + "&cmd";
                            String mess = getRepMessage(cmd);
                            if ("timeout".equals(mess) || mess == null) {
                                tips();
                            } else if (mess.contains("door_open=1")) {
                                openTips();
                            } else {
                                lockgT.setBackgroundDrawable(YADeviceHomePageActivity.this
                                        .getResources().getDrawable(R.drawable.yalockwd));
                                lockfxI.setBackgroundDrawable(YADeviceHomePageActivity.this
                                        .getResources().getDrawable(R.drawable.yalockright));
                                lockkT.setBackgroundDrawable(YADeviceHomePageActivity.this
                                        .getResources().getDrawable(R.drawable.yalockk));
                            }
                        }
                        break;
                    // �ƶ�
                    case MotionEvent.ACTION_MOVE:

                        break;
                    default:
                        break;
                }
                return true;
            }

        });

        yalockI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                windL.setVisibility(View.GONE);
                timerL.setVisibility(View.GONE);
                lockL.setVisibility(View.VISIBLE);
            }
        });

        // ģʽ����
        modeI.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                
                if (mode == 0) {// �ֶ��ĳ��Զ���
                    String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&mode="
                            + 1 + "&userid=" + userId + "&cmd";
                    String mess = getRepMessage(cmd);
                    if ("timeout".equals(mess) || mess == null) {
                        tips();
                    } else if (mess.contains("door_open=1")) {
                        openTips();
                    } else {
                        modeI.setBackgroundResource(R.drawable.yawindd);
                        setWind(2);// ��ʾ�ĵ͵�
                    }
                }

            }

        });
        
        //ɨ��
        swindI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                
                String cmd=null;
                if (swind == 0) {//ɨ��״̬��
                     cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&swind="
                            + 1 + "&userid=" + userId + "&cmd";
                   
                }else{
                     cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&swind="
                            + 0+ "&userid=" + userId + "&cmd";
                 
                }
                String mess = getRepMessage(cmd);
                if ("timeout".equals(mess) || mess == null) {
                    tips();
                } else if (mess.contains("door_open=1")) {
                    openTips();
                } else {
                    if(swind==0){
                        swind=1;
                        swindI.setBackgroundResource(R.drawable.yak);
                    }else{
                        swind=0;
                        swindI.setBackgroundResource(R.drawable.yakd);
                    }
                   
                }

            }

        });
        
        //������
        anionI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String cmd=null;
                if (anion == 0) {//������
                     cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&anion="
                            + 1 + "&userid=" + userId + "&cmd";
                   
                }else{
                     cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&anion="
                            + 0+ "&userid=" + userId + "&cmd";
                 
                }
                String mess = getRepMessage(cmd);
                if ("timeout".equals(mess) || mess == null) {
                    tips();
                } else if (mess.contains("door_open=1")) {
                    openTips();
                } else {
                    if(anion==0){
                        anion=1;
                        anionI.setBackgroundResource(R.drawable.yajj);
                    }else{
                        anion=0;
                        anionI.setBackgroundResource(R.drawable.yajjd);
                    }
                   
                }

            }

        });

    }

    /***
     * ʱ�����
     * 
     * @author sbp
     */
    public class TimeListenter implements OnClickListener {
        private int time;// ����

        public TimeListenter(int time) {
            this.time = time;
        }

        @Override
        public void onClick(View arg0) {
            String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&timer="
                    + time + "&userid=" + userId + "&cmd";
            String mess = getRepMessage(cmd);
            if ("timeout".equals(mess) || mess == null) {
                tips();
            } else if (mess.contains("door_open=1")) {
                openTips();
            } else {// ���سɹ���
                Editor windEd = storeSp.edit();
                windEd.putInt(Constant.WINDDW, time);
                windEd.commit();
                settime(time);
            }

        }

    }

    /***
     * ��������
     * 
     * @author sbp
     */
    public class WindListenter implements OnClickListener {
        private int fw;// ����

        public WindListenter(int fw) {
            this.fw = fw;
        }

        @Override
        public void onClick(View arg0) {
            String cmd = "wlsn=" + wlsn + "&mac=" + phoneMac + "&set&speed="
                    + fw + "&userid=" + userId + "&cmd";
            String mess = getRepMessage(cmd);
            if ("timeout".equals(mess) || mess == null) {
                tips();
            } else if (mess.contains("door_open=1")) {
                openTips();
            } else {// ���سɹ���
                Editor windEd = storeSp.edit();
                windEd.putInt(Constant.WINDDW, fw);
                windEd.commit();
                setWind(fw);
                //��ɵ��ֶ�
                mode=0;
                modeI.setBackgroundResource(R.drawable.yawind);
            }

        }

    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.yadevice_home_page_layout);
        Bundle bundle = this.getIntent().getExtras();
        wlsn = bundle.getString("wlsn");
        sb = bundle.getString("sb");
        userId = bundle.getString("userId");
        EairApplaction.getInstance().addActivity(this);
        loadAnim();
        findView();
        setListener();
        storeSp = this.getSharedPreferences(Constant.STOREDB, 0);
        phoneMac = storeSp.getString(Constant.PHONEMAC, "");
        this.fw = storeSp.getInt(Constant.WINDDW, 1);
        this.time = storeSp.getInt(Constant.TIME, 0);
        this.air = storeSp.getString(Constant.AIR, "A");
        this.mode = storeSp.getInt(Constant.MODE, 1);
        this.lock = storeSp.getInt(Constant.LOCK, 1);
        this.anion = storeSp.getInt(Constant.ANION, 1);
        this.swind = storeSp.getInt(Constant.SWIND, 1);
        this.motor = storeSp.getInt(Constant.MOTOR, 1);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // ������ؾ��˳�Ӧ��
            EairApplaction.getInstance().exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onPause()
    {
        super.onPause();
        this.mRunProgress.setVisibility(4);
        this.mRunProgress.clearAnimation();
        if (this.timer != null)
        {
            this.timer.cancel();
            this.timer = null;
        }
    }

    protected void onResume()
    {
        super.onResume();
        initWeatherView();
        initEairView();
        this.mRunProgress.startAnimation(this.mRotateAnimation);
        if (this.timer == null) {
            this.timer = new Time();
            timer.test();// //������ʱ������
        }
    }

    public class Time extends TimerTask {
        private Timer timer = null;// ��ʼҪ��timer���󸳳�ֵnull
        private long timestop = 120000;// ÿ��2����ִ��һ������

        public Time() {
            timer = new Timer();
            System.out.println("java��ʱ������");
        }// ���캯��

        public void test()
        {
            Time t = new Time();
            Date date = new Date();
            timer.schedule(t, date, timestop);
        }// ������������ĺ���������main������newһ���¶���ʱ�����ô˺������ڴ˺�������schedule��������������

        @Override
        public void run() {
            getInitData();
            air = storeSp.getString(Constant.AIR, "A");
            Message msg = new Message();
            msg.what = 1;
            handle.sendMessage(msg);

        }

    }

    /***
     * ���ݿ������������޸�ҳ����ʾ��
     */
    Handler handle = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    YADeviceHomePageActivity.this.changBg();
                    break;
                case -1:// ��ʱ����
                    tips();
                    break;
                case 2:
                    openTips();
                    break;
            }
            super.handleMessage(msg);
        }

    };

    public void getInitData() {
        String str = "wlsn=" + wlsn + "&mac=" + phoneMac + "&get&isair&userid=" + userId + "&cmd";
        String repMessage = getRepMessage(str);
        if ("timeout".equals(repMessage) || repMessage == null) {
            Message msg2 = new Message();
            msg2.what = -1;
            handle.sendMessage(msg2);
        } else if (repMessage.contains("door_open=1")) {
            Message msg2 = new Message();
            msg2.what = 2;
            handle.sendMessage(msg2);
        } else {// ���ص���Ϣ,������״̬���б���
            if (!repMessage.contains("success")) {
                storeData(repMessage);
            }
        }
    }

    /***
     * �����ص�init�豸����Ϣ
     * 
     * @param message
     */
    private void storeData(String message) {// rep&timer=0&speed=3&pmx=15&air=A&mode=1&uv=1&anion=1&hepa=1&end
        Editor editor = storeSp.edit();
        String[] mess = message.split("&");
        System.out.println("======message====" + message);
        String str = mess[1];
        if (str.contains("=")) {
            editor.putString(Constant.AIR, mess[1].split("=")[1]);
            editor.commit();
        }
    }

    private void pmMap(String aqi) {
        pm_description = (TextView) this.findViewById(R.id.pm_value);
        if (aqi == null) {
            pm_description.setText("��ȡ��");
        } else {
            int aqi_value = Integer.parseInt(aqi);
            if ((aqi_value > 0) && (aqi_value <= 50))
            {
                pm_description.setText("��");
            }
            else if ((aqi_value > 50) && (aqi_value <= 100))
            {
                pm_description.setText("��");
            }
            else if ((aqi_value > 100) && (aqi_value <= 150))
            {
                pm_description.setText("�����Ⱦ");
            }
            else if ((aqi_value > 150) && (aqi_value <= 200))
            {
                pm_description.setText("�ж���Ⱦ");
            }
            else if ((aqi_value > 200) && (aqi_value <= 300))
            {
                pm_description.setText("�ض���Ⱦ");
            }
            else if (aqi_value > 300)
            {
                pm_description.setText("������Ⱦ");
            }
            else
            {
                pm_description.setText("");
            }
        }
    }

}
