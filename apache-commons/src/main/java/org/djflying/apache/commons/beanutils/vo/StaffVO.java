package org.djflying.apache.commons.beanutils.vo;

import org.djflying.apache.commons.beanutils.vo.AddressVO;

import java.util.Date;
import java.util.List;

public class StaffVO {
	private long staffId;
	private String staffName;
	private double staffValue;
	private Date dateJoined;
	List<AddressVO> addressList;

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public List<AddressVO> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressVO> addressList) {
		this.addressList = addressList;
	}

	public double getStaffValue() {
		return staffValue;
	}

	public void setStaffValue(double staffValue) {
		this.staffValue = staffValue;
	}

}
