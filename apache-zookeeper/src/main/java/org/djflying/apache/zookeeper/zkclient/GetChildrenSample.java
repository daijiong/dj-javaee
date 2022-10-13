package org.djflying.apache.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用ZkClient获取子节点列表。
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class GetChildrenSample {

    /**
     * 测试使用ZkClient获取子节点列表。
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

    	String path = "/zk-book";
        ZkClient zkClient = new ZkClient(Constants.connectString, Constants.connectionTimeout);
        // 注册子节点变更事件监听
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            /**
             * 回调函数
             *
             * @param parentPath 子节点变更通知对应的父节点的节点路径
             * @param currentChilds 子节点的相对路径列表，如果没有子节点，那么会传入null
             * @throws Exception
             */
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + " 's child changed, currentChilds:" + currentChilds);
            }
        });
        zkClient.createPersistent(path);
        Thread.sleep( 1000 );
        System.out.println(zkClient.getChildren(path));
        Thread.sleep( 1000 );
        zkClient.createPersistent(path+"/c1");
        Thread.sleep( 1000 );
        zkClient.delete(path+"/c1");
        Thread.sleep( 1000 );
        zkClient.delete(path);
        Thread.sleep( Integer.MAX_VALUE );
    }
}