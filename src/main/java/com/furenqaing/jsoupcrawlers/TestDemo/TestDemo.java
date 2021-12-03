package com.furenqaing.jsoupcrawlers.TestDemo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TestDemo {
    public static void main(String[] args) throws Exception {
        //案例一：入门程序，模拟get请求，爬取网页原页面
        /*CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://www.itcast.cn/search");
        uriBuilder.setParameter("keys","java").setParameter("","");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        //HttpPost httpPost = new HttpPost(uriBuilder.build());
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
            //关闭连接
            response.close();
            closeableHttpClient.close();
        }*/

        //案例二：模拟表单post请求
        /*CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.itcast.cn/search");
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("keys","java"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params,"utf-8");
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
        }*/

        //案例三：连接池管理器
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(10);
        doGet(pool);
    }

    private static void doGet(PoolingHttpClientConnectionManager pool) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool).build();
        HttpPost httpPost = new HttpPost("http://www.itcast.cn");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
