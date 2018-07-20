package com.shfh.pdmp.demo.curd.service.impl;

import com.shfh.pdmp.base.Pager;
import com.shfh.pdmp.demo.curd.dao.DemoDAO;
import com.shfh.pdmp.demo.curd.entities.UserEntity;
import com.shfh.pdmp.demo.curd.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wpcao on 2018/7/14 16:54.
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoDAO demoDAO;

    /**
     * 添加
     *
     * @param user 用户信息
     */
    public void save(UserEntity user) {

        //验证参数合法性

        //执行业务逻辑

        //调用DAO
        demoDAO.save(user);

        //处理返回结果
    }


    public List<UserEntity> queryForList() throws SQLException {
        return demoDAO.queryEntities();
    }

    public List<UserEntity> queryForList(String username, Pager page) {
        return demoDAO.queryForPageList(username, page);
    }


    public void delete(String id) {
        demoDAO.delete(id);
    }

    public void update(UserEntity user) {
        demoDAO.update(user);
    }
}
