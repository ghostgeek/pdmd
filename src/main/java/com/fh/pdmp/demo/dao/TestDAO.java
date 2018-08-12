package com.fh.pdmp.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.fh.pdmp.demo.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/6.
 */
@Repository
public class TestDAO {
    JdbcTemplate jdbcTemplate = Util.jdbcTemplate;

    /**
     * 查询测试申请列表
     *
     * @return
     */
    public Map<String, Object> getTestPage(JSONObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        // sql语句
        String sql = "select *  from pb_test_process_info order by TEST_PROCESS_ID desc";
        String searchsql = sql + " limit ?,?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(searchsql, new Object[]{(json.getInteger("pageNo") - 1) * json.getInteger("pageSize"), json.getInteger("pageSize")});
        Map<String, Object> total = jdbcTemplate.queryForMap("SELECT count(1) total FROM (" + sql + ") a");
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    /**
     * 添加测试
     *
     * @return
     */
    public int addApply(JSONObject json) {
        // sql语句
        String sql = "insert INTO pb_test_process_info(CREATE_TIME,TEST_NAME,MODULE_NAME,TEST_CONTENT,MD5,FTP_URL,LEVEL,DEPONDENCY,EXPECT_TIME,STATUS,JIRA_URL,APPLY_USER)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{json.getString("create_time"), json.getString("test_name"), json.getString("module_name"), json.getString("test_content"), json.getString("md5"), json.getString("ftp_url"), json.getString("level"), json.getString("depondency"), json.getString("expect_time"), json.getString("status"), json.getString("jira_url"), json.getString("apply_user")});

    }

    /**
     * 根据iD获取详细信息
     */
    public Map<String, Object> getTestItemInfo(JSONObject json) {
        String testid = json.getString("testid");
        String sql = "select* FROM pb_test_process_info a WHERE  TEST_PROCESS_ID=?";
        return jdbcTemplate.queryForMap(sql, new Object[]{testid});
    }

    /**
     * 修改
     */

    public int updateTestItemInfo(JSONObject json) {
        // sql语句
        String sql = "UPDATE pb_test_process_info set MODULE_NAME=?,TEST_NAME=?,TEST_CONTENT=?,MD5=?,FTP_URL=?,LEVEL=?,DEPONDENCY=?,STATUS=0,EXPECT_TIME=? WHERE  TEST_PROCESS_ID=?";
        return jdbcTemplate.update(sql, new Object[]{json.getString("module_name"), json.getString("test_name"), json.getString("test_content"), json.getString("md5"), json.getString("ftp_url"), json.getString("level"), json.getString("depondency"), json.getString("expect_time"), json.getString("testid")});
    }

    /**
     * 审核
     */

    public int checkTestItemInfo(JSONObject json) {
        // sql语句
        String sql = "UPDATE pb_test_process_info set status=?,START_TIME=?,END_TIME=?,TEST_USER=?,JIRA_URL=? WHERE  TEST_PROCESS_ID=?";
        return jdbcTemplate.update(sql, json.getString("status"), json.getInteger("start_time"), json.getInteger("end_time"), json.getString("test_user"), json.getString("jira_url"), json.getString("testid"));
    }

    /**
     * 补充
     */
    public int addSupply(JSONObject json) {
        String sql = "INSERT INTO pb_test_add_info(TEST_PROCESS_ID,CREATE_TIME,ADD_CONTENT,EDIT_USER)VALUES(?,?,?,?)";
        return jdbcTemplate.update(sql, json.getInteger("testid"), json.getString("create_time"), json.getString("add_content"), json.getString("edit_user"));
    }

    /**
     * 获取延期信息列表
     */
    public List<Map<String, Object>> getSupply(JSONObject json) {
        String sql = "SELECT * FROM pb_test_add_info where TEST_PROCESS_ID=?";
        return jdbcTemplate.queryForList(sql, json.getInteger("testid"));
    }


    /**
     * 延期
     */
    public int addDelay(JSONObject json) {
        String[] sql = new String[2];
        sql[0] = "INSERT INTO PB_TEST_DELAY_INFO(TEST_PROCESS_ID,END_TIME,CREATE_TIME,DELAY_REASON,EDIT_USER)VALUES(" + json.getInteger("testid") + "," + json.getString("end_time") + "," + json.getString("create_time") + "," + json.getString("delay_reason") + "," + json.getString("edit_user") + ")";
        sql[1] = "UPDATE pb_test_process_info set END_TIME=" + json.getString("end_time") + " WHERE  TEST_PROCESS_ID=" + json.getInteger("testid");
        int[] result = jdbcTemplate.batchUpdate(sql);
        return result[0] == 1 && result[1] == 1 ? 1 : 0;
    }

    /**
     * 获取延期信息列表
     */
    public List<Map<String, Object>> getDelay(JSONObject json) {
        String sql = "SELECT * FROM PB_TEST_DELAY_INFO where TEST_PROCESS_ID=?";
        return jdbcTemplate.queryForList(sql, json.getInteger("testid"));
    }

    /**
     * 测试完成
     */
    public int testComplete(JSONObject json) {
        String sql = "UPDATE pb_test_process_info set STATUS=?,CHECK_USER=? WHERE  TEST_PROCESS_ID=?";
        return jdbcTemplate.update(sql, json.getString("status"), json.getString("check_user"), json.getString("testid"));
    }

    /**
     * 导出
     */
    public List<Map<String, Object>> export(JSONObject json) {
        // sql语句
        String sql = "select *  from pb_test_process_info ";
        return jdbcTemplate.queryForList(sql);
    }
}
