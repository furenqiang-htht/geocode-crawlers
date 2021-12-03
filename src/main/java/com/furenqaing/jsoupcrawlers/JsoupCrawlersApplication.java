package com.furenqaing.jsoupcrawlers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时
public class JsoupCrawlersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsoupCrawlersApplication.class, args);
    }

}
