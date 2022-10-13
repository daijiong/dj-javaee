package org.djflying.apache.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * 综合测试
 *
 * @author dj4817
 * @version $Id: CuratorSample.java, v 0.1 2018/5/9 11:13 dj4817 Exp $$
 */
public class CuratorSample {

    /**
     * 主方法测试
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();

        client.create().forPath("/test_log2", "v1".getBytes());
        client.setData().forPath("/test_log2", "v2".getBytes());
        client.getData().forPath("/test_log2");
        client.create().forPath("/test_log2/c", "v1".getBytes());
        client.delete().forPath("/test_log2/c");
    }
}
