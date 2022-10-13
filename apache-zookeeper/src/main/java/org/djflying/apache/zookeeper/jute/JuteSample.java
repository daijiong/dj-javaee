package org.djflying.apache.zookeeper.jute;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.BinaryOutputArchive;
import org.apache.zookeeper.server.ByteBufferInputStream;

/**
 * 使用Jute进行序列化和反序列化
 *
 * @author dj4817
 * @version $Id: JuteSample.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class JuteSample {

	/**
	 * 测试使用Jute进行序列化和反序列化
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {

        // 构建一个BinaryOutputArchive序列化器
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BinaryOutputArchive boa = BinaryOutputArchive.getArchive(baos);
		// 将MockReqHeader对象数据序列化到header这个tag中去
		new MockReqHeader(0x34221eccb92a34eL, "ping" ).serialize(boa, "header");
		// 这里通常是TCP网络传输对象
		ByteBuffer bb = ByteBuffer.wrap( baos.toByteArray() );
		// 构建一个BinaryInputArchive序列化器
		ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
		BinaryInputArchive bbia = BinaryInputArchive.getArchive(bbis);
		// 调用MockReqHeader的反序列化方法，从指定tag中反序列化出数据内容
		MockReqHeader header2 = new MockReqHeader();
		header2.deserialize(bbia, "header");
		bbis.close();
		baos.close();
	}
}
