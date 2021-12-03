package com.furenqaing.jsoupcrawlers.TestDemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;

public class JsoupDemo {
    public static void main(String[] args) throws Exception {
        Document document = Jsoup.parse(new URL("http://47.97.200.76/login"), 10000);
        String title = document.getElementsByTag("title").first().text();
        System.out.println("打印=========>"+title);
    }
}
