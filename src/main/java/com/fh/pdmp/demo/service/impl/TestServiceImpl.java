package pdmp.service.impl;

import com.alibaba.fastjson.JSONObject;
import pdmp.dao.DemoDAO;
import pdmp.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdmp.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wpcao on 2018/7/14 16:54.
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoDAO demoDAO;

    public DemoDAO getDemoDAO() {
        return demoDAO;
    }

    public void setDemoDAO(DemoDAO userDao) {
        this.demoDAO = userDao;
    }

    /**
     * 查询测试申请列表
     *
     * @return
     */
    public Map<String, Object> getTestPage(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        map = demoDAO.getTestPage(jsonObject);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
        for (Map<String, Object> map1 : list) {
            map1.put("CREATE_TIME", Util.stampToDate(map1.get("CREATE_TIME").toString(), "yyyy-MM-dd"));
            map1.put("EXPECT_TIME", Util.stampToDate(map1.get("EXPECT_TIME").toString(), "yyyy-MM-dd"));
            String START_TIME = null == map1.get("START_TIME") ? "" : Util.stampToDate(map1.get("START_TIME").toString(), "yyyy-MM-dd");
            String END_TIME = null == map1.get("END_TIME") ? "" : Util.stampToDate(map1.get("END_TIME").toString(), "yyyy-MM-dd");
            map1.put("START_TIME", START_TIME);
            map1.put("END_TIME", END_TIME);
        }
        return map;
    }

    /**
     * 新建测试任务
     *
     * @param json
     * @return
     */
    public int addApply(JSONObject json) {
        return demoDAO.addApply(json);
    }

    /**
     * 根据iD获取详细信息
     *
     * @param json
     * @return
     */
    public Map<String, Object> getTestItemInfo(JSONObject json) {
        return demoDAO.getTestItemInfo(json);
    }

    /**
     * 修改
     */

    public int updateTestItemInfo(JSONObject json) {
        return demoDAO.updateTestItemInfo(json);
    }

    /**
     * 审核
     */

    public int checkTestItemInfo(JSONObject json) {
        return demoDAO.checkTestItemInfo(json);
    }

    /**
     * 补充
     */
    public int addSupply(JSONObject json) {
        return demoDAO.addSupply(json);
    }

    /**
     * 获取补充信息列表
     */
    public List<Map<String, Object>> getSupply(JSONObject json) {
        List<Map<String, Object>> list = demoDAO.getSupply(json);
        for (Map<String, Object> map1 : list) {
            map1.put("CREATE_TIME", Util.stampToDate(map1.get("CREATE_TIME").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        return list;
    }

    /**
     * 延期
     */
    public int addDelay(JSONObject json) {
        return demoDAO.addDelay(json);
    }

    /**
     * 获取延期信息列表
     */
    public List<Map<String, Object>> getDelay(JSONObject json) {
        List<Map<String, Object>> list = demoDAO.getDelay(json);
        for (Map<String, Object> map1 : list) {
            map1.put("END_TIME", Util.stampToDate(map1.get("END_TIME").toString(), "yyyy-MM-dd"));
            map1.put("CREATE_TIME", Util.stampToDate(map1.get("CREATE_TIME").toString(), "yyyy-MM-dd HH:mm:ss"));
        }
        return list;
    }

    /**
     * 测试完成
     */
    public int testComplete(JSONObject json) {
        return demoDAO.testComplete(json);
    }

    /**
     * 导出
     */
    public List<Map<String, Object>> export(JSONObject json) {
        List<Map<String, Object>> list = demoDAO.export(json);
        for (Map<String, Object> map1 : list) {
            map1.put("END_TIME", Util.stampToDate(map1.get("END_TIME").toString(), "yyyy-MM-dd"));
            map1.put("CREATE_TIME", Util.stampToDate(map1.get("CREATE_TIME").toString(), "yyyy-MM-dd HH:mm:ss"));
            map1.put("EXPECT_TIME", Util.stampToDate(map1.get("EXPECT_TIME").toString(), "yyyy-MM-dd"));
            map1.put("START_TIME", Util.stampToDate(map1.get("START_TIME").toString(), "yyyy-MM-dd"));
        }
        return  list;
    }
}
