package us.codecraft.webmagic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Kailian.Huang
 * @date: 2019/12/25 11:57
 * @description: TestSpider
 */
public class TestSpider implements PageProcessor {

    private Logger logger = LoggerFactory.getLogger(TestSpider.class);

    private Site site = Site.me()
            .setRetryTimes(20)
            .setCycleRetryTimes(3)
            .setSleepTime(500)
            .setTimeOut(1000 * 20)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

    @Override
    public void process(Page page) {
        String curUrl = page.getUrl().get();
        Html html = page.getHtml();
        System.out.println(curUrl);

    }

    public static void main(String[] args) throws InterruptedException {

        Spider spider = Spider.create(new TestSpider()).thread(1);
        spider.destroyWhenExit = false;
        for (int i = 0; i < 100; i++) {
            String url = "https://www.tianyancha.com/company/1129200733?_=" + i;
            Request request = new Request();
            request.setUrl(url);
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            request.addCookie("auth_token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTA1Nzk4MDgyMyIsImlhdCI6MTU3NzM0MDM1OCwiZXhwIjoxNjA4ODc2MzU4fQ.pWfX9N301LD09Lk62EYRxL0SJE92fmSP-otjkq7W9jvIFQst1Z6SpJ3wrBOiPaR1Av452zlRsyYNY-5My0goKg");
            spider.addRequest(request);
        }
        spider.start();

        while (true) {
            Thread.sleep(2000);
            System.out.println("check!!!");
            spider.stop();
            if (spider.getStatus().equals(Spider.Status.Stopped)) {
                for (int i = 0; i < 100; i++) {
                    spider.addUrl("http://www.baidu.com?_=" + i);
                }
                spider.start();
                break;
            }
        }


        while (true) {
            Thread.sleep(5000);
            System.out.println("check222!!!");
            spider.stop();
            if (spider.getStatus().equals(Spider.Status.Stopped)) {
                for (int i = 0; i < 5; i++) {
                    spider.addUrl("http://www.baidu.com?kk=" + i);
                }
                spider.start();
                break;
            }
        }

        while (true) {
            Thread.sleep(5000);
            if (spider.getStatus().equals(Spider.Status.Stopped)) {
                spider.close();
                break;
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
