package org.djflying.apache.commons.beanutils;

import org.djflying.apache.commons.beanutils.dto.StaffDTO;
import org.djflying.apache.commons.beanutils.utils.BeanUtilsEx;
import org.djflying.apache.commons.beanutils.vo.AddressVO;
import org.djflying.apache.commons.beanutils.vo.StaffVO;
import org.junit.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeanUtilsTest {

    StaffDTO staffDTO;
    StaffVO staffVO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        staffDTO = new StaffDTO();
        staffVO = new StaffVO();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws IllegalAccessException, InvocationTargetException {
//		staffDTO.setStaffId("1");
//		staffDTO.setStaffName("戴炯");
//		staffDTO.setDateJoined("2016-03-09");
//		BeanUtilsEx.copyProperties(staffVO, staffDTO);
//		Assert.assertEquals(1, staffVO.getStaffId());
//		Assert.assertEquals("戴炯", staffVO.getStaffName());

        List<AddressVO> addressVOList = new ArrayList<AddressVO>();
        AddressVO addressVO = new AddressVO();
        addressVO.setAddressId(2);
        addressVO.setAddressName("中国");
        addressVO.setAddressDate(new Date());
        addressVOList.add(addressVO);

        staffVO.setStaffId(1);
        staffVO.setStaffName("戴炯");
        staffVO.setStaffValue(0.12);
        staffVO.setDateJoined(new Date());
        staffVO.setAddressList(addressVOList);

        System.out.println(staffVO.getAddressList().get(0).getAddressId());
        System.out.println(staffVO.getAddressList().get(0).getAddressName());
        System.out.println(staffVO.getAddressList().get(0).getAddressDate());

        BeanUtilsEx.copyProperties(staffDTO, staffVO);

//		Assert.assertEquals("1", staffDTO.getStaffId());
//		Assert.assertEquals("戴炯", staffDTO.getStaffName());
        System.out.println(staffDTO.getStaffValue());
        System.out.println(staffDTO.getDateJoined());
        System.out.println(staffDTO.getAddressList().size());
        System.out.println(staffDTO.getAddressList().get(0).getAddressId());
//		Assert.assertEquals(new Date(), staffDTO.getDateJoined());

    }

}
