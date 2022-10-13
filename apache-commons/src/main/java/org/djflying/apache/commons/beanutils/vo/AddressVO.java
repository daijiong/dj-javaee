package org.djflying.apache.commons.beanutils.vo;

import java.util.Date;

public class AddressVO {

    private long addressId;
    private String addressName;
    private Date addressDate;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Date getAddressDate() {
        return addressDate;
    }

    public void setAddressDate(Date addressDate) {
        this.addressDate = addressDate;
    }

}
