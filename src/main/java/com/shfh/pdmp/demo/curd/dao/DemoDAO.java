package com.shfh.pdmp.demo.curd.dao;

import com.shfh.pdmp.base.Pager;
import com.shfh.pdmp.demo.curd.entities.UserEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 */
@Repository
public class DemoDAO {

    @Resource
    private JdbcTemplate xxx_jdbcTemplate;//详细使用说明见Spring JDBCTemplate官方文档

    public void save(UserEntity user) {
        xxx_jdbcTemplate.update("insert into table_demo (id,name,age) values(?,?,?)", new Object[]{user.getId(), user.getUsername(), user.getAge()});
    }


    public List<UserEntity> queryEntities() throws SQLException {
        String sql = "select * from table_demo";
        List<UserEntity> list = xxx_jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserEntity.class));
        return list;
    }


    public List<UserEntity> queryForPageList(String username, Pager page) {
        String pageSql = "select id,name as username,age from table_demo order by id limit ?,?";
        List<UserEntity> list = xxx_jdbcTemplate.query(pageSql, new BeanPropertyRowMapper(UserEntity.class), page.getPageNo() - 1, page.getPageSize());

        page.setTotalRows(xxx_jdbcTemplate.queryForObject("select count(1) from table_demo limit ?,?", Integer.class, page.getPageNo() - 1, page.getPageSize()));
        return list;
    }


    public void delete(String id) {
        xxx_jdbcTemplate.update("delete from table_demo where id=?", id);
    }

    public void update(UserEntity user) {
        xxx_jdbcTemplate.update("update table_demo set id=?,name=?,age=?", new Object[]{user.getId(), user.getUsername(), user.getAge()});
    }
}
