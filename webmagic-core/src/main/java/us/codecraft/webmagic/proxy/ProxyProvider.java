package us.codecraft.webmagic.proxy;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;

import java.util.List;

/**
 * Proxy provider. <br>
 *
 * @since 0.7.0
 */
public interface ProxyProvider {

    /**
     *
     * Return proxy to Provider when complete a download.
     * @param proxy the proxy config contains host,port and identify info
     * @param page the download result
     * @param task the download task
     */
    void returnProxy(Proxy proxy, Page page, Task task);

    /**
     * Get a proxy for task by some strategy.
     * @param task the download task
     * @return proxy
     */
    Proxy getProxy(Task task);

    /**
     * Get all proxies. write by Convict
     * @return
     */
    List<Proxy> getProxies();

    /**
     * remove specified proxy object
     * @param proxy
     */
    void removeProxy(Proxy proxy);

}
