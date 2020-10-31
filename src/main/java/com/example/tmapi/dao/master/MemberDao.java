package com.example.tmapi.dao.master;

import com.example.tmapi.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDao {
    /**
     * 根据手机号查询是否是会员
     * @param tel tel
     * @return Integer
     */
    Integer queryByCond(@Param("tel")String tel);

    /**
     * 查询各店新增会员数
     * @param member
     * @return
     */
    List<Member> queryNewMember(@Param(value = "member")Member member);

    /**
     * 查询各店总会员数
     *
     * @return
     */
    List<Member> querySumMember();
}
