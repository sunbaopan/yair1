
package com.ya.yair.domain;

public class DeviceDomain {

    private String mac;// �豸mac��ַ

    private String state;// �豸��״̬
    
    private String poweron;//�Ƿ񿪹ػ�
    
    private String air;//���ڻ�������
    
    private String bm;//�豸�ı���

    public String getPoweron() {
        return poweron;
    }

    public void setPoweron(String poweron) {
        this.poweron = poweron;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }
    
}
