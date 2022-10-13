package org.djflying.apache.commons.lang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.*;

/**
 * StringUtils测试类
 *
 * @author dj4817
 * @version $Id: StringUtilsTest.java, v 0.1 2017/9/28 15:46 dj4817 Exp $$
 */
public class StringUtilsTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        // data setup
        String str1 = "";
        String str2 = "";
        String str3 = "\t";
        String str4 = null;
        String str5 = "123";
        String str6 = "ABCDEFG";
        String str7 = "Itfeels good to use JakartaCommons.\r\n";

        // check for empty strings
        System.out.println("==============================");
        System.out.println("Is str1 blank? " + StringUtils.isBlank(str1));
        System.out.println("Is str2 blank? " + StringUtils.isBlank(str2));
        System.out.println("Is str3 blank? " + StringUtils.isBlank(str3));
        System.out.println("Is str4 blank? " + StringUtils.isBlank(str4));

        // check for numerics
        System.out.println("==============================");
        System.out.println("Is str5 numeric? " + StringUtils.isNumeric(str5));
        System.out.println("Is str6 numeric? " + StringUtils.isNumeric(str6));

        // reverse strings / whole words
        System.out.println("==============================");
        System.out.println("str6: " + str6);
        System.out.println("str6reversed: " + StringUtils.reverse(str6));
        System.out.println("str7: " + str7);
        String str8 = StringUtils.chomp(str7);
        str8 = StringUtils.reverseDelimited(str8, ' ');
        System.out.println("str7 reversed whole words : \r\n" + str8);

        // build header (useful to print logmessages that are easy to locate)
        System.out.println("==============================");
        System.out.println("print header:");
        String padding = StringUtils.repeat("=", 50);
        String msg = StringUtils.center(" Customised Header ", 50, "%");
        Object[] raw = new Object[]{padding, msg, padding};
        String header = StringUtils.join(raw, "\r\n");
        System.out.println(header);
    }

    /**
     * LeftPad方法测试类
     */
    @Test
    public void testLeftPad() {
        String supplerId = "123";
        String leftPad_supplerId = StringUtils.leftPad(supplerId, 4, "0");
        Assert.assertEquals("0123", leftPad_supplerId);
    }

    /**
     * split方法测试类
     * 注意：StringUtils的split方法和String的split方法的区别：
     * StringUtils的split方法返回的数组是去除了空字符串的。
     * String的split方法返回的数组未去除空字符串。
     *
     * @throws Exception
     */
    @Test
    public void testSplit() throws Exception {

        String testString = ",1,,2,";
        String[] testStringArray = StringUtils.split(testString,",");
        Assert.assertEquals(2, testStringArray.length);
        Assert.assertEquals("1", testStringArray[0]);
        Assert.assertEquals("2", testStringArray[1]);

        String[] testStringArray2 = testString.split(",");
        Assert.assertEquals(4, testStringArray2.length);
        Assert.assertEquals("", testStringArray2[0]);
        Assert.assertEquals("1", testStringArray2[1]);
        Assert.assertEquals("", testStringArray2[2]);
        Assert.assertEquals("2", testStringArray2[3]);
    }

    /**
     * split方法测试类
     *
     * @throws Exception
     */
    @Test
    public void testSplit1() throws Exception {

        String testString = "TCPL";
        String[] testStringArray = StringUtils.split(testString,"_");
        Assert.assertEquals("1", testStringArray[1]);

    }
}
