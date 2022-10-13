package org.djflying.apache.zookeeper.jute;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 发起一个“获取数据”请求，分析其请求协议
 *
 * @author dj4817
 * @version $Id: JuteSample.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class SimpleGetDataRequest implements Watcher {

    /**
     * 测试发起一个“获取数据”请求，分析其请求协议
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new SimpleGetDataRequest());
        zk.getData("/zookeeper", true, null);
    }

    /**
     * 回调方法
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
    }
}