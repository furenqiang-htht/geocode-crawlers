package com.furenqaing.jsoupcrawlers.task;

import com.alibaba.fastjson.JSON;
import com.furenqaing.jsoupcrawlers.entity.GeoCodeEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeoPoiTask implements PageProcessor {

    @Autowired
    SpringDataPipeline springDataPipeline;

    private Site site = Site.me()
            .setTimeOut(1000 * 60)//设置请求超时，单位ms
            .setCharset("utf8")//设置编码
            .setRetrySleepTime(1000 * 3)//设置请求失败后的重新请求时间
            .setSleepTime(3)//设置重置次数 ;
            ;

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().css("div.wrap-bg div.container div.row ul li").nodes();
        if (nodes.size() == 0) {
            saveGeoInfo(page);
        } else {
            for (Selectable node : nodes) {
                String url = node.links().toString();
                String cityName = node.css("a", "text").toString();
                System.out.println("爬取的城市：" + cityName + "-地址：" + url);
                PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
                pool.setMaxTotal(10);
                List<String> urls = getUrls(pool, url);
                for (String s : urls) {
                    page.addTargetRequest(s);//将此地址加入任务中
                }
            }
        }
    }

    private void saveGeoInfo(Page page) {
        Html html = page.getHtml();
        List<Selectable> nodestr = html.css("div.data-wrap tbody tr").nodes();
        List<GeoCodeEntity> geoCodeEntitys = new ArrayList<>();
        for (Selectable nodetr : nodestr) {
            List<Selectable> nodestd = nodetr.css("td").nodes();
            String text = nodestd.get(1).css("td", "text").toString();
            String province = nodestd.get(2).css("td", "text").toString();
            String city = nodestd.get(3).css("td", "text").toString();
            String counties = nodestd.get(4).css("td", "text").toString();
            String areaCode = nodestd.get(5).css("td", "text").toString();
            String phone = nodestd.get(6).css("td", "text").toString();
            String area = nodestd.get(7).css("td", "text").toString();
            String adress = nodestd.get(8).css("td", "text").toString();
            String maxClass = nodestd.get(9).css("td", "text").toString();
            String minClass = nodestd.get(10).css("td", "text").toString();
            String lon = nodestd.get(11).css("td", "text").toString();
            String lat = nodestd.get(12).css("td", "text").toString();
            String latStr = new BigDecimal(lat).setScale(6).toString();
            String lonStr = new BigDecimal(lon).setScale(6).toString();
            String id = lonStr + latStr;
            GeoCodeEntity geoCodeEntity = new GeoCodeEntity();
            geoCodeEntity.setId(id.replace(".", ""));
            geoCodeEntity.setText(text);
            geoCodeEntity.setProvince(province);
            geoCodeEntity.setCity(city);
            geoCodeEntity.setCounties(counties);
            geoCodeEntity.setAreaCode(areaCode);
            geoCodeEntity.setPhone(phone);
            geoCodeEntity.setArea(area);
            geoCodeEntity.setAdress(adress);
            geoCodeEntity.setMaxClass(maxClass);
            geoCodeEntity.setMinClass(minClass);
            GeoPoint geoPoint = new GeoPoint(Double.valueOf(lat), Double.valueOf(lon));
            geoCodeEntity.setLocation(geoPoint);
            geoCodeEntitys.add(geoCodeEntity);
        }
        System.out.println("每页数据分别是：======>" + JSON.toJSONString(geoCodeEntitys));
        page.putField("geoCodeEntitys", geoCodeEntitys);
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    //initialDelay：当任务启动后多久开始执行此方法，fixedDelay：执行频率为多少
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 60 * 24 * 7)
    //@Scheduled(initialDelay = 1000)
    public void process() {
        Spider.create(new GeoPoiTask())
                .addUrl("http://www.poilist.cn/cities-list/%E6%99%AF%E7%82%B9")
                .thread(10)//设置五个线程处理
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))//设置去重过滤器为布隆过滤器
                .addPipeline(springDataPipeline)//添加输出模式
                .run();
    }

    private List<String> getUrls(PoolingHttpClientConnectionManager pool, String uri) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool).build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        List<String> urlList = new ArrayList<String>();
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String htmlStr = EntityUtils.toString(entity);
                Document html = Jsoup.parse(htmlStr);
                Elements select = html.select("ul.pagination li");
                for (Element element : select) {
                    String url = element.select("a").first().attr("href");
                    System.out.println("爬取的二级地址有======>：" + url);
                    urlList.add(url);
                }
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
        return urlList;
    }
}
