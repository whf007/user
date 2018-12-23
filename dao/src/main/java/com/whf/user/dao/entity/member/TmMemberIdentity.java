package com.whf.user.dao.entity.member;

import java.io.Serializable;
import java.util.Date;

public class TmMemberIdentity implements Serializable {
    private String memberId;

    private String identityId;

    private Boolean identityType;

    private Integer pidId;

    private String partnerId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    public Boolean getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Boolean identityType) {
        this.identityType = identityType;
    }

    public Integer getPidId() {
        return pidId;
    }

    public void setPidId(Integer pidId) {
        this.pidId = pidId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId == null ? null : partnerId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", memberId=").append(memberId);
        sb.append(", identityId=").append(identityId);
        sb.append(", identityType=").append(identityType);
        sb.append(", pidId=").append(pidId);
        sb.append(", partnerId=").append(partnerId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}