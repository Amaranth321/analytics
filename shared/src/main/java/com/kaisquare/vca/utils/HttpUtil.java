package com.kaisquare.vca.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class HttpUtil {

    private static final Logger logger = LogManager.getLogger(HttpUtil.class);

    private static final int CACHE = 1024;

    /**
     *
     * @param url  http url which point to resources need to download
     * @param path path for store the resource
     * @return
     */
    public static boolean download(String url,String path){
        boolean result = false;
        CloseableHttpClient client = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            client = HttpClientBuilder.create().build();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            File file = new File(path);
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer=new byte[CACHE];
            int ch = 0;
            while((ch=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,ch);
            }
            result=true;
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            client.close();
        } catch (Exception e) {
           logger.error(e.toString(),e);
        }
        return result;
    }
}
