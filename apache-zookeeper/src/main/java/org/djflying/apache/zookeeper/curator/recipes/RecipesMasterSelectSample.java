package org.djflying.apache.zookeeper.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * Master选举
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class RecipesMasterSelectSample {

    static String master_path = "/curator_recipes_master_path";

    /**
     * 测试Master选举
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.创建LeaderSelector
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
            /**
             * 回调方法（当Curator成功获取到Master权利的时候会回调）
             *
             * @param client
             * @throws Exception
             */
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为Master角色");
                // 实现自己的业务逻辑
                Thread.sleep(30000);
                System.out.println("完成Master操作，释放Master权利");
            }
        });
        // 2.
        selector.autoRequeue();
        // 3.
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}