package us.codecraft.webmagic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.PriorityScheduler;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;
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
        List<String> keyNos = new ArrayList<String>();
        keyNos.add("640625808");
        keyNos.add("2427587838");
        keyNos.add("3139635864");
        keyNos.add("3139635862");
        for (String keyNo : keyNos) {
            Request request = new Request();
            String url = "https://www.tianyancha.com/company/" + keyNo + "?_=" + System.currentTimeMillis();
            request.setUrl(url);
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            request.addCookie("auth_token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTY4NzEyNDcyNiIsImlhdCI6MTU3ODk4OTgwMCwiZXhwIjoxNjEwNTI1ODAwfQ.h1e_PepAFZYsg4-NZkYvOpDLAc-EA0Wd__WSMlCBl7eFX11ozCVaKH0TOphp8MCsEUFWfNlZ-9J821GVPq7ZiQ");
            spider.addRequest(request);
        }
        Request r = new Request();
        String u = "https://www.tianyancha.com/pagination/holder.xhtml?ps=20&pn=1&id=" + 640625808 + "&_=" + System.currentTimeMillis();
        r.setUrl(u);
        r.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        r.addCookie("auth_token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTY4NzEyNDcyNiIsImlhdCI6MTU3ODk4OTgwMCwiZXhwIjoxNjEwNTI1ODAwfQ.h1e_PepAFZYsg4-NZkYvOpDLAc-EA0Wd__WSMlCBl7eFX11ozCVaKH0TOphp8MCsEUFWfNlZ-9J821GVPq7ZiQ");
        spider.addRequest(r);

        keyNos.add("64062580811");
        keyNos.add("24275878382");
        keyNos.add("31396358642");
        keyNos.add("31396358623");
        for (String keyNo : keyNos) {
            Request request = new Request();
            String url = "https://www.tianyancha.com/company/" + keyNo + "?_=" + System.currentTimeMillis();
            request.setUrl(url);
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            request.addCookie("auth_token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTY4NzEyNDcyNiIsImlhdCI6MTU3ODk4OTgwMCwiZXhwIjoxNjEwNTI1ODAwfQ.h1e_PepAFZYsg4-NZkYvOpDLAc-EA0Wd__WSMlCBl7eFX11ozCVaKH0TOphp8MCsEUFWfNlZ-9J821GVPq7ZiQ");
            spider.addRequest(request);
        }

        spider.start();

        while (true) {
            Thread.sleep(500);
            if (spider.getStatus().equals(Spider.Status.Stopped)) {
                break;
            } else if (spider.getStatus().equals(Spider.Status.Running)) {
                int statusCode = spider.getStatusCode();
                if (statusCode != 0 && !spider.getSite().getAcceptStatCode().contains(statusCode)) {
                    System.out.println("need stop");
                    spider.stop();
                    break;
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
