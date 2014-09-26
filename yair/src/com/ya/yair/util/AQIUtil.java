
package com.ya.yair.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class AQIUtil {

    /***
     * ������ҳ��URLץȡ����
     */
    public String getAQI(String AQIurl) {
        URLConnection conn = null;
        String str = null;
        try
        {
            URL url = new URL(AQIurl);
            // ��URL����
            conn = url.openConnection();
            // ʹ��InputStream����URLConnection��ȡ����
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            // ��ByteArrayBuffer����
            ByteArrayBuffer baf = new ByteArrayBuffer(102400);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            str = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
            int index = str.indexOf("jin_caption = cityname");
            if (index != -1) {
                str = str.substring(index + 69, index + 72).trim();
                str = str.replace("\"", "");
            } else {
                str = null;
            }
            bis.close();
            is.close();
        } catch (Exception ee)
        {
            System.out.print("ee:" + ee.getMessage());
        }
        return str;
    }
}
