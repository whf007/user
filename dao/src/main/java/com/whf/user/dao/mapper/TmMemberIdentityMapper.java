package com.whf.user.dao.mapper;

import com.whf.user.dao.entity.member.TmMemberIdentity;
import java.util.List;

public interface TmMemberIdentityMapper {
    int insert(TmMemberIdentity record);

    List<TmMemberIdentity> selectAll();
}