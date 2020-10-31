package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.MemberDao;
import com.example.tmapi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    /**
     * 查询是否是会员
     * @param tel tel
     * @return boolean
     */
    @Override
    public boolean queryByCond(String tel) {

        Integer num = memberDao.queryByCond(tel);
        return num != null && !(num.equals(0));
    }
}
