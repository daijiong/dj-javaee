package org.djflying.apache.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用ZkClient创建节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class CreateNodeSample {

    /**
     * 测试使用ZkClient创建节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZkClient zkClient = new ZkClient(Constants.connectString, Constants.connectionTimeout);
        String path = "/zk-book/c1";
        // createParents设置为true表示递归创建父节点
        zkClient.createPersistent(path, true);
    }
}