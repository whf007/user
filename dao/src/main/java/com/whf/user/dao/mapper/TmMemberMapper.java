package com.whf.user.dao.mapper;

import com.whf.user.dao.entity.member.TmMember;
import java.util.List;

public interface TmMemberMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(TmMember record);

    TmMember selectByPrimaryKey(String memberId);

    List<TmMember> selectAll();

    int updateByPrimaryKey(TmMember record);
}