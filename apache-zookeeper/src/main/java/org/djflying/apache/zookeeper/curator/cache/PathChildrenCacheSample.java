package org.djflying.apache.zookeeper.curator.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用PathChildrenCache
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class PathChildrenCacheSample {

    static String path = "/zk-book/pathchildrencache";

    /**
     * 测试使用PathChildrenCache
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).sessionTimeoutMs(Constants.sessionTimeout).build();
        client.start();
        // 1.新建一个PathChildrenCache实例
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        // 2.启动PathChildrenCache
        cache.start(StartMode.POST_INITIALIZED_EVENT);
        // 3.注册监听，当指定节点发生以下变化时，就会回调该方法
        /*
            1、CHILD_ADDED - 新增子节点
            2、CHILD_UPDATED - 子节点数据变更
            3、CHILD_REMOVED - 子节点删除
         */
        /*
            注意：
            1、节点本身的变更不会回调
            2、无法对2级子节点进行事件监听
         */
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            /**
             * 回调方法
             *
             * @param client
             * @param event
             * @throws Exception
             */
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED," + event.getData().getPath());
                        System.out.println("新增的子节点数据内容为：" + new String(event.getData().getData(), "utf-8"));
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED," + event.getData().getPath());
                        System.out.println("子节点更新之后的数据内容为：" + new String(event.getData().getData(), "utf-8"));
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED," + event.getData().getPath());
                        break;
                    default:
                        break;
                }
            }
        });
        // 4.创建节点
        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        Thread.sleep(1000);
        // 5.创建子节点
        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1", "init".getBytes());
        Thread.sleep(1000);
        // 6.更新子节点
        client.setData().forPath(path + "/c1", "init1".getBytes());
        Thread.sleep(1000);
        // 7.删除子节点
        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);
        // 8.删除节点
        client.delete().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}