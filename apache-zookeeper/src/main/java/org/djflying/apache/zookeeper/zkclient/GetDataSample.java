package org.djflying.apache.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用ZkClient获取节点数据
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class GetDataSample {

    /**
     * 测试使用ZkClient获取节点数据
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(Constants.connectString, Constants.connectionTimeout);
        zkClient.createEphemeral(path, "123");
        // 注册节点数据内容变更事件监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {

            /**
             * 节点数据删除回调函数
             *
             * @param dataPath 事件通知对应的节点路径
             * @throws Exception
             */
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted.");
            }

            /**
             * 节点数据内容改变回调函数
             *
             * @param dataPath 事件通知对应的节点路径
             * @param data 最新的数据内容
             * @throws Exception
             */
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed, new data: " + data);
            }
        });

        System.out.println((String) zkClient.readData(path));
        zkClient.writeData(path, "456");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}