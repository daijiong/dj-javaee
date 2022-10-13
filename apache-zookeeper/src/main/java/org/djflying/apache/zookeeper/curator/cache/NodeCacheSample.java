package org.djflying.apache.zookeeper.curator.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用NodeCache
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class NodeCacheSample {

    static String           path   = "/zk-book/nodecache";

	/**
	 * 测试使用NodeCache
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
				.retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
		// 1.创建节点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
        // 2.新建一个NodeCache实例，数据不压缩
        final NodeCache cache = new NodeCache(client, path, false);
        // 3.启动NodeCache，这里参数的默认值为false，如果设置为true，那么NodeCache在第一次启动的时候就会立刻从Zookeeper上读取对应节点的数据内容，并保存在cache中
        cache.start(true);
        // 4.注册监听，当指定节点发生以下变更时，就会回调该方法
        /*
            1、节点的数据内容发生变化
            2、节点被创建
         */
        /*
            注意：
            1、节点删除的时候不会回调
         */
        cache.getListenable().addListener(new NodeCacheListener() {
			/**
			 * 回调方法
			 *
			 * @throws Exception 异常
			 */
			@Override
            public void nodeChanged() throws Exception {
                System.out.println("Node data update, new data: " + new String(cache.getCurrentData().getData()));
            }
        });
        // 5.更新数据节点的数据内容
        client.setData().forPath(path, "u".getBytes());
        Thread.sleep(1000);
        // 6.删除数据节点
        client.delete().deletingChildrenIfNeeded().forPath(path);
        // 7.再次创建节点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
        Thread.sleep(Integer.MAX_VALUE);
    }
}