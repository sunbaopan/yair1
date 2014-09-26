
package com.ya.yair;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ya.yair.util.AQIUtil;
import com.ya.yair.util.UdpHelper;
import com.ya.yair.util.Util;
import com.ya.yair.util.WeatherUtil;
import com.ya.yair.util.WebServiceUtil;

public class EairApplaction extends Application
{
    public static long mTimeDiff = 0L;
    public List<Activity> mActivityList = new ArrayList();
    public String mNetIP;
    private static EairApplaction instance;

    public static Map<String, String> cityMap = new HashMap<String, String>();

    public UdpHelper udpH;

    public int interval;

    public static boolean status;

    public static String todayWeather;

    public static String aqi;

    public static String city;

    public EairApplaction() {

    }

    public synchronized static EairApplaction getInstance() {
        if (null == instance) {
            instance = new EairApplaction();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mActivityList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        System.out.println("===EairApplaction==");
        cityMap.put("����", "beijing");
        cityMap.put("�Ϻ�", "shanghai");
        cityMap.put("���", "tianjin");
        cityMap.put("����", "chongqing");
        cityMap.put("��ɽ", "anshan");
        cityMap.put("����", "anyang");
        cityMap.put("����", "anqing");
        cityMap.put("����", "alidiqu");
        cityMap.put("����", "baoding");
        cityMap.put("��ͷ", "baotou");
        cityMap.put("��Ϫ", "benxi");
        cityMap.put("����", "beihai");
        cityMap.put("����", "baoji");
        cityMap.put("����", "bozhou");
        cityMap.put("����", "bengpu");
        cityMap.put("����", "bazhong");
        cityMap.put("����", "binzhou");
        cityMap.put("��ɽ", "baoshan");
        cityMap.put("�����׶�", "bayanchuoer");
        cityMap.put("����", "changzhi");
        cityMap.put("���", "chifeng");
        cityMap.put("����", "changchun");
        cityMap.put("����", "changzhou");
        cityMap.put("��ɳ", "changsha");
        cityMap.put("����", "changde");
        cityMap.put("�ɶ�", "chengdu");
        cityMap.put("����", "cangzhou");
        cityMap.put("�е�", "chengde");
        cityMap.put("����", "chaoyang");
        cityMap.put("����", "chuzhou");
        cityMap.put("����", "chaohushi");
        cityMap.put("����", "chizhou");
        cityMap.put("����", "chuxiong");
        cityMap.put("����", "changdu");
        cityMap.put("����", "changshu");
        cityMap.put("����", "chaozhou");
        cityMap.put("��ͬ", "datong");
        cityMap.put("����", "dalian");
        cityMap.put("����", "daqing");
        cityMap.put("����", "deyang");
        cityMap.put("��ݸ", "dongguan");
        cityMap.put("����", "dandong");
        cityMap.put("����", "dazhou");
        cityMap.put("��Ӫ", "dongying");
        cityMap.put("����", "dezhou");
        cityMap.put("����", "dali");
        cityMap.put("������˹", "eerduozi");
        cityMap.put("��������", "erlianhaote");
        cityMap.put("��˳", "fushun");
        cityMap.put("����", "fuzhou");
        cityMap.put("��ɽ", "foshan");
        cityMap.put("����", "fuxin");
        cityMap.put("����", "fuyang");
        cityMap.put("����", "fuzhoushi");
        cityMap.put("����", "fuyangshi");
        cityMap.put("����", "guangzhou");
        cityMap.put("����", "guilin");
        cityMap.put("����", "guiyang");
        cityMap.put("�㰲", "guangan");
        cityMap.put("��Ԫ", "guangyuan");
        cityMap.put("����", "ganzhou");
        cityMap.put("����", "handan");
        cityMap.put("���ͺ���", "huhehaote");
        cityMap.put("������", "haerbin");
        cityMap.put("����", "hangzhou");
        cityMap.put("����", "huzhou");
        cityMap.put("�Ϸ�", "hefei");
        cityMap.put("����", "haikou");
        cityMap.put("����", "huaian");
        cityMap.put("����", "huizhou");
        cityMap.put("��ˮ", "hengshui");
        cityMap.put("����", "huaibei");
        cityMap.put("����", "huainan");
        cityMap.put("��ɽ", "huangshan");
        cityMap.put("��ɽ�羰��", "huangshangjq");
        cityMap.put("����", "heze");
        cityMap.put("�ױ�", "hebi");
        cityMap.put("�Ƹ�", "huanggan");
        cityMap.put("���ױ���", "hulunbeier");
        cityMap.put("��Դ", "heyuan");
        cityMap.put("��«��", "huludao");
        cityMap.put("����", "haimen");
        cityMap.put("����", "jinzhou");
        cityMap.put("����", "jilin");
        cityMap.put("����", "jiaxing");
        cityMap.put("�Ž�", "jiujiang");
        cityMap.put("����", "jinan");
        cityMap.put("����", "jining");
        cityMap.put("����", "jiaozuo");
        cityMap.put("����", "jingzhou");
        cityMap.put("���", "jinchang");
        cityMap.put("����", "jiangmen");
        cityMap.put("��", "jinhua");
        cityMap.put("����", "jincheng");
        cityMap.put("����", "jinzhong");
        cityMap.put("�Ż�ɽ�羰��", "jiuhuanshanjq");
        cityMap.put("����", "jian");
        cityMap.put("������", "jingdezhen");
        cityMap.put("����", "jiangyin");
        cityMap.put("��̳", "jintan");
        cityMap.put("����", "jiaozhou");
        cityMap.put("��ī", "jimo");
        cityMap.put("����", "jiaonan");
        cityMap.put("����", "jurong");
        cityMap.put("����", "jieyang");
        cityMap.put("������", "jiayuguan");
        cityMap.put("����", "kaifeng");
        cityMap.put("����", "kunming");
        cityMap.put("��������", "kelamayi");
        cityMap.put("�����", "kuerle");
        cityMap.put("��ɽ", "kunshan");
        cityMap.put("�ٷ�", "linfen");
        cityMap.put("���Ƹ�", "lianyungang");
        cityMap.put("����", "luoyang");
        cityMap.put("����", "liuzhou");
        cityMap.put("����", "luzhou");
        cityMap.put("����", "lasa");
        cityMap.put("����", "lanzhou");
        cityMap.put("��ˮ", "lishui");
        cityMap.put("�ȷ�", "langfang");
        cityMap.put("����", "luliang");
        cityMap.put("����", "liaoyang");
        cityMap.put("����", "liuan");
        cityMap.put("��ɽ", "leshan");
        cityMap.put("����", "laiwu");
        cityMap.put("����", "linyi");
        cityMap.put("�ĳ�", "liaocheng");
        cityMap.put("���", "luohe");
        cityMap.put("����", "lijiang");
        cityMap.put("�ٲ�", "lincang");
        cityMap.put("��֥", "linzhi");
        cityMap.put("����", "longyan");
        cityMap.put("����", "liyang");
        cityMap.put("�ٰ�", "linan");
        cityMap.put("����", "laixi");
        cityMap.put("����", "laizhou");
        cityMap.put("ĵ����", "mudanjiang");
        cityMap.put("��ɽ", "maanshan");
        cityMap.put("����", "mianyang");
        cityMap.put("ï��", "maoming");
        cityMap.put("÷��", "meizhou");
        cityMap.put("�Ͼ�", "nanjing");
        cityMap.put("��ͨ", "nantong");
        cityMap.put("����", "ningbo");
        cityMap.put("�ϲ�", "nanchang");
        cityMap.put("����", "nanning");
        cityMap.put("�ϳ�", "nanchong");
        cityMap.put("����", "nanyang");
        cityMap.put("����", "naqu");
        cityMap.put("��ƽ", "nanping");
        cityMap.put("ƽ��ɽ", "pingdingshan");
        cityMap.put("��֦��", "panzhihua");
        cityMap.put("�̽�", "panjin");
        cityMap.put("Ƽ��", "pingxiang");
        cityMap.put("���", "puyang");
        cityMap.put("����", "putian");
        cityMap.put("ƽ��", "pingdu");
        cityMap.put("����", "penglai");
        cityMap.put("�ػʵ�", "qinhuangdao");
        cityMap.put("�������", "qiqihaer");
        cityMap.put("Ȫ��", "quanzhou");
        cityMap.put("�ൺ", "qingdao");
        cityMap.put("����", "qujing");
        cityMap.put("����", "quzhou");
        cityMap.put("��Զ", "qingyuan");
        cityMap.put("����", "rizhao");
        cityMap.put("�տ���", "rikaze");
        cityMap.put("�ٳ�", "rongcheng");
        cityMap.put("��ɽ", "rushan");
        cityMap.put("ʯ��ׯ", "shijiazhuang");
        cityMap.put("����", "shenyang");
        cityMap.put("��ͷ", "shantou");
        cityMap.put("����", "suzhou");
        cityMap.put("����", "shaoxing");
        cityMap.put("����Ͽ", "sanmenxia");
        cityMap.put("�ع�", "shaoguan");
        cityMap.put("����", "shenzhen");
        cityMap.put("����", "sanya");
        cityMap.put("ʯ��ɽ", "shizuishan");
        cityMap.put("��Ǩ", "suqian");
        cityMap.put("˷��", "shuozhou");
        cityMap.put("����", "suzhoushi");
        cityMap.put("����", "suiing");
        cityMap.put("����", "shangrao");
        cityMap.put("����", "shangqiu");
        cityMap.put("����", "suizhou");
        cityMap.put("��ͨ", "shaotong");
        cityMap.put("ɽ��", "shannan");
        cityMap.put("����", "sanming");
        cityMap.put("�ٹ�", "shouguang");
        cityMap.put("��β", "shanwei");
        cityMap.put("��ɽ", "tangshan");
        cityMap.put("̫ԭ", "taiyuan");
        cityMap.put("̨��", "taizhou");
        cityMap.put("̩��", "taian");
        cityMap.put("ͭ��", "tongchuan");
        cityMap.put("̩��", "taizhoushi");
        cityMap.put("����", "tieling");
        cityMap.put("ͭ��", "tongling");
        cityMap.put("ͨ��", "tongliao");
        cityMap.put("̫��", "taicang");
        cityMap.put("����", "wuxi");
        cityMap.put("����", "wenzhou");
        cityMap.put("�ߺ�", "wuhu");
        cityMap.put("Ϋ��", "weifang");
        cityMap.put("����", "weihai");
        cityMap.put("�人", "wuhan");
        cityMap.put("μ��", "weinan");
        cityMap.put("��³ľ��", "wulumuqi");
        cityMap.put("��ɽ", "wenshan");
        cityMap.put("�ں�", "wuhai");
        cityMap.put("�߷���", "wafangdian");
        cityMap.put("�⽭", "wujiang");
        cityMap.put("�ĵ�", "wendeng");
        cityMap.put("����", "xuzhou");
        cityMap.put("����", "xiamen");
        cityMap.put("��̶", "xiangtan");
        cityMap.put("����", "xian");
        cityMap.put("����", "xianyang");
        cityMap.put("����", "xining");
        cityMap.put("��̨", "xingtai");
        cityMap.put("����", "xuancheng");
        cityMap.put("����", "qizhou");
        cityMap.put("����", "xinyu");
        cityMap.put("����", "xinxiang");
        cityMap.put("���", "xuchang");
        cityMap.put("����", "xinyang");
        cityMap.put("��Ȫ", "yangquan");
        cityMap.put("����", "yangzhou");
        cityMap.put("��̨", "yantai");
        cityMap.put("�˲�", "yichang");
        cityMap.put("����", "yueyang");
        cityMap.put("�˱�", "yibin");
        cityMap.put("��Ϫ", "yuxi");
        cityMap.put("�Ӱ�", "yanan");
        cityMap.put("����", "yinchuan");
        cityMap.put("�γ�", "yancheng");
        cityMap.put("�˳�", "yuncheng");
        cityMap.put("Ӫ��", "yingkou");
        cityMap.put("�Ű�", "yaan");
        cityMap.put("�˴�", "yichun");
        cityMap.put("ӥ̶", "yingtan");
        cityMap.put("����", "yixing");
        cityMap.put("����", "yiwu");
        cityMap.put("����", "yangjiang");
        cityMap.put("�Ƹ�", "yunfu");
        cityMap.put("��", "zhenjiang");
        cityMap.put("�Ͳ�", "zibo");
        cityMap.put("��ׯ", "zaozhuang");
        cityMap.put("֣��", "zhengzhou");
        cityMap.put("����", "zhuzhou");
        cityMap.put("�żҽ�", "zhangjiajie");
        cityMap.put("�麣", "zhuhai");
        cityMap.put("տ��", "zhanjiang");
        cityMap.put("��ɽ", "zhongshan");
        cityMap.put("�Թ�", "zigong");
        cityMap.put("����", "zunyi");
        cityMap.put("����", "zhaoqing");
        cityMap.put("�żҿ�", "zhangjiakou");
        cityMap.put("��ɽ", "zhoushan");
        cityMap.put("����", "ziyang");
        cityMap.put("�ܿ�", "zhoukou");
        cityMap.put("פ���", "zhumadian");
        cityMap.put("�żҸ�", "zhangjiagang");
        cityMap.put("����", "zhuji");
        cityMap.put("����", "zhangqiu");
        cityMap.put("��Զ", "zhaoyuan");
        cityMap.put("���", "hongkong");
        cityMap.put("����", "macau");
        cityMap.put("�±�", "xinbei");
        cityMap.put("̨��", "taibei");
        cityMap.put("��԰", "taoyuan");
        cityMap.put("����", "xinzhushi");
        cityMap.put("����", "miaoli");
        cityMap.put("̨��", "taizhong");
        cityMap.put("�û�", "zhanghua");
        cityMap.put("��Ͷ", "nantou");
        cityMap.put("����", "yunlin");
        cityMap.put("���x", "jiayi");
        cityMap.put("̨��", "tainan");
        cityMap.put("����", "gaoxiong");
        cityMap.put("����", "pingdong");
        cityMap.put("̨��", "taidong");
        cityMap.put("����", "hualian");
        cityMap.put("����", "yilan");
        cityMap.put("����", "jinmen");
        cityMap.put("���", "penghu");
        cityMap.put("��¡", "jilong");
        Thread  messageThread=new Thread(new GetAllMessage(this));
        messageThread.start();
    }
    
    
    public class GetAllMessage implements Runnable{
        
        private Context  context;
        
        public  GetAllMessage(Context context){
            this.context=context;
        }

        @Override
        public void run() {
            WebServiceUtil wsU = new WebServiceUtil();
            String message = wsU.getWIP();
            if (message == null) {//���Է���2��
                try {
                    Thread.sleep(Constant.SLEEPTIME);
                    message = wsU.getWIP();
                    if (message == null) {
                        Thread.sleep(Constant.SLEEPTIME);
                        message = wsU.getWIP();
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            if (message == null) {
                status = false;
            } else {
                status = true;
                String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?ip="
                        + message;
                // ��ȡ���ڵĵ�ַ
                // String city = Util.getCIty(url);
                city = Util.getCIty(url);
                // map chaohu to hefei
                if ("����".equals(city))
                {
                    city = "�Ϸ�";
                }
                AQIUtil au = new AQIUtil();
                String pycity = EairApplaction.cityMap.get(city);
                String aqiUrl = "http://www.cnpm25.cn/city/" + pycity + ".html";
                aqi = au.getAQI(aqiUrl);
                WeatherUtil wu = new WeatherUtil(context);
                todayWeather = wu.getWeather(city);
                if (todayWeather != null) {
                    todayWeather = todayWeather.replace("/", "~");
                }
               
            }
        }
        
    }
 

    /****
     * ���������ȡ����ֵ
     * 
     * @param str
     * @return
     */
    public String getRepMessage(String str) {
        String message = null;
        try {
            udpH.sendSb(Constant.IPADDRESS, Constant.WPORT, str);
            for (;;) {
                if (UdpHelper.result != null) {
                    message = UdpHelper.result;
                    UdpHelper.result = null;
                    break;
                }
                Thread.sleep(500);
                interval += 1;
                if (interval > 6) {
                    message = "timeout";
                    interval = 0;// ��ʼ�����ָ�0��
                    break;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
