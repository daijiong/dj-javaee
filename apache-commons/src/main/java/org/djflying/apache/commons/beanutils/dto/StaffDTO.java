package org.djflying.apache.commons.beanutils.dto;

import java.util.List;

public class StaffDTO {
    private String   staffId;
    private String   staffName;
    private String   staffValue;
    private String   dateJoined;
    List<AddressDTO> addressList;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<AddressDTO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressDTO> addressList) {
        this.addressList = addressList;
    }

    public String getStaffValue() {
        return staffValue;
    }

    public void setStaffValue(String staffValue) {
        this.staffValue = staffValue;
    }

}
