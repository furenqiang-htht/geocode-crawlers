package com.furenqaing.jsoupcrawlers.TestDemo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class WebMagicTest implements PageProcessor {
    private Site site = Site.me()
            .setTimeOut(1000*10)//设置请求超时，单位ms
            .setCharset("utf8")//设置编码
            .setRetrySleepTime(1000*3)//设置请求失败后的重新请求时间
            .setSleepTime(3)//设置重置次数 ;
            ;
    public static void main(String[] args) {
        Spider.create(new WebMagicTest())
                //.addPipeline(new FilePipeline("E:\\STUDY\\study\\jsoup-crawlers\\src\\main\\resources\\static"))//将结果保存到文件中需要单独设置，默认是控制台打印
                .addUrl("http://ace.piesat.cn/login.xhtml")
                .thread(3)//设置五个线程处理
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))//设置去重过滤器为布隆过滤器
                .run();
    }

    @Override
    public void process(Page page) {
        //1、 使用css选择器 解析页面,获取<div>标签下并且class=mod_conatiner 里面的<script>
        page.putField("div1", page.getHtml().css("div.ios_qrcode div").all());

        //2、 使用xpath 解析 解析页面,获取<div>下面的<a>标签下并且id=InitCartUrl-mini的值
        //page.putField("div2", page.getHtml().xpath("//div[@id=copyright]").toString());

        //3、 使用正则表达式 解析 解析页面,获取<div>下面的<a>标签下并且id=InitCartUrl-mini的值
        page.putField("div3",page.getHtml().css("div#copyright").regex(".*Team.*").all());

        //4、 获取某标签内的超链接
        page.putField("div4",page.getHtml().css("div#forget_pass").links().all());

        //5、addTargetRequest\addTargetRequests将任务放入任务队列中
//        page.addTargetRequests( page.getHtml().css("div#forget_pass").links().all());
//        page.addTargetRequest("http://ace.piesat.cn/login.xhtml");
    }

    @Override
    public Site getSite() {
        return site;
    }
}
