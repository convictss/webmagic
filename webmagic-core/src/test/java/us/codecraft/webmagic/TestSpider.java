package us.codecraft.webmagic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    private HttpClientDownloader downloader = new HttpClientDownloader();

    @Override
    public void process(Page page) {
        String curUrl = page.getUrl().get();
        Html html = page.getHtml();
        System.out.println(curUrl);

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            // add proxy
            HttpClientDownloader downloader = new HttpClientDownloader();
            Document doc = Jsoup.parse(new URL("http://www.89ip.cn/index_1.html"), 5000);
            Elements trs = doc.select("tbody > tr");
            List<Proxy> proxies = new ArrayList<Proxy>();
            for (Element tr : trs) {
                String host = tr.select("td").first().text().trim();
                int port = Integer.parseInt(tr.select("td").get(1).text().trim());
                proxies.add(new Proxy(host, port));
            }

            if (proxies.size() > 0) {
                downloader.addProxyRemoveCode(403, 404);
                downloader.setProxyProvider(new SimpleProxyProvider(proxies));
            }

            Spider spider = Spider.create(new TestSpider()).thread(1);
            spider.setDownloader(downloader);
            String testUrl = "https://www.redfin.com/VA/Falls-Church/6511-Cape-Ct-22043/home/9476508";
            spider.addUrl(testUrl);
            spider.start();
            spider.getScheduler().clearDuplicateSet();
            spider.getScheduler().clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
