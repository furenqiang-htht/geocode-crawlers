package com.furenqaing.jsoupcrawlers.TestDemo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class TestSimHash implements PageProcessor {
    public static void main(String[] args) {
        //创建下载器Downloader
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("60.191.11.249", 3128)));
        //给下载器设置代理服务器信息

        Spider.create(new TestSimHash())
                .addUrl("http://ip.chinaz.com/getip.aspx")
                //设置代理下载器
                .setDownloader(httpClientDownloader)
                .run();
    }

    @Override
    public void process(Page page) {
        String html = page.getHtml().toString();
        System.out.println(html);
    }

    private Site site=Site.me();
    @Override
    public Site getSite() {
        return site;
    }
}
