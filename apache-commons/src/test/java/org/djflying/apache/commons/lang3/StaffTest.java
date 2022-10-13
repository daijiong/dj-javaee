package org.djflying.apache.commons.lang3;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Date;

/**
 * Staff Tester. 
 *
 * @author dj4817
 * @version $Id: $testClass.java, v 0.1 12/08/2017 dj4817 Exp $$
 */
public class StaffTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void test() throws Exception {

        Staff staff1 = new Staff(123, "John Smith", new Date());
        Staff staff2 = new Staff(456, "Jane Smith", new Date());

        System.out.println("staff1's info: " + staff1);
        System.out.println("staff2's info: " + staff2);
        System.out.println("staff1's hash code: " + staff1.hashCode());
        System.out.println("staff2's hash code: " + staff2.hashCode());
        System.out.println("staff1 equals staff2? " + staff1.equals(staff2));
    }
}
