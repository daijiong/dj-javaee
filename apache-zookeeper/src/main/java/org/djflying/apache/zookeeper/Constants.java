package org.djflying.apache.zookeeper;

/**
 * Description
 *
 * @author dj4817
 * @version $Id: Constants.java, v 0.1 2018/3/12 14:16 dj4817 Exp $$
 */
public class Constants {

    /** Zookeeper服务器列表 */
    //public static final String connectString     = "10.101.44.200:2181,10.101.44.201:2181,10.101.44.202:2181,10.101.44.203:2181,10.101.44.204:2181";
    public static final String connectString     = "centos:2181";
    /** 会话的超时时间，单位ms */
    public static final int    sessionTimeout    = 2000;
    /** 连接创建超时时间，单位ms */
    public static final int    connectionTimeout = 5000;
    /** 初始sleep时间 */
    public static final int    BASESLEEPTIMEMS   = 1000;
    /** 最大重试次数 */
    public static final int    MAXRETRIES        = 3;

}
