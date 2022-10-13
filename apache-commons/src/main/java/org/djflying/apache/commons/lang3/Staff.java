package org.djflying.apache.commons.lang3;

import java.util.Date;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 职工类
 *
 * @author dj4817
 * @version $Id: Staff.java, v 0.1 2017/12/6 14:10 dj4817 Exp $$
 */
public class Staff implements Comparable {

    private long   staffId;
    private String staffName;
    private Date   dateJoined;

    /**
     * 无参构造器
     */
    public Staff() {
    }

    /**
     * 全参构造器
     *
     * @param staffId
     * @param staffName
     * @param dateJoined
     */
    public Staff(long staffId, String staffName, Date dateJoined) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.dateJoined = dateJoined;
    }

    /**
     * 重写compareTo方法
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        int res = -1;
        if (o != null && Staff.class.isAssignableFrom(o.getClass())) {
            Staff s = (Staff) o;
            res = new CompareToBuilder().append(dateJoined, s.getDateJoined()).append(staffName, s.getStaffName()).toComparison();
        }
        return res;
    }

    /**
     * 重写equals方法
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        boolean res = false;
        if (o != null && Staff.class.isAssignableFrom(o.getClass())) {
            Staff s = (Staff) o;
            res = new EqualsBuilder().append(staffId, s.getStaffId()).isEquals();
        }
        return res;
    }

    /**
     * 重写hashCode方法
     *
     * @return
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 23).append(staffId).toHashCode();
    }

    /**
     * 重写toString方法
     *
     * @return
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("staffId", staffId).append("staffName", staffName).append("dateJoined", dateJoined).toString();
    }

    /**
     * Getter method for property <tt>staffId</tt>.
     *
     * @return property value of staffId
     */
    public long getStaffId() {
        return staffId;
    }

    /**
     * Setter method for property <tt>staffId</tt>.
     *
     * @param staffId value to be assigned to property staffId
     */
    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    /**
     * Getter method for property <tt>staffName</tt>.
     *
     * @return property value of staffName
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Setter method for property <tt>staffName</tt>.
     *
     * @param staffName value to be assigned to property staffName
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * Getter method for property <tt>dateJoined</tt>.
     *
     * @return property value of dateJoined
     */
    public Date getDateJoined() {
        return dateJoined;
    }

    /**
     * Setter method for property <tt>dateJoined</tt>.
     *
     * @param dateJoined value to be assigned to property dateJoined
     */
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
}
