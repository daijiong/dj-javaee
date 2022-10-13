package org.djflying.apache.zookeeper.jute;

import org.apache.jute.InputArchive;
import org.apache.jute.OutputArchive;
import org.apache.jute.Record;

import javax.annotation.Resource;

/**
 * MockReqHeader
 *
 * @author dj4817
 * @version $Id: MockReqHeader.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class MockReqHeader implements Record {

    private long   sessionId;
    private String type;

    public MockReqHeader() {
    }

    public MockReqHeader(long sessionId, String type) {
        this.sessionId = sessionId;
        this.type = type;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String m_) {
        type = m_;
    }

    /**
     * 序列化方法
     *
     * @param a_
     * @param tag
     * @throws java.io.IOException
     */
    @Override
    public void serialize(OutputArchive a_, String tag) throws java.io.IOException {

        // 将当前MockReqHeader对象的各个成员变量以tag为标记写入到OutputArchive序列化器中
        a_.startRecord(this, tag);
        a_.writeLong(sessionId, "sessionId");
        a_.writeString(type, "type");
        a_.endRecord(this, tag);
    }

    /**
     * 反序列化方法
     *
     * @param a_
     * @param tag
     * @throws java.io.IOException
     */
    @Override
    public void deserialize(InputArchive a_, String tag) throws java.io.IOException {

        // 从InputArchive反序列化器中根据tag标记将数据读取出来，并赋值给MockReqHeader对象的相应成员变量
        a_.startRecord(tag);
        sessionId = a_.readLong("sessionId");
        type = a_.readString("type");
        a_.endRecord(tag);
    }
}
