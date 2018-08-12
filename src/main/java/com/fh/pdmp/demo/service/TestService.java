package pdmp.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by wpcao on 2018/7/14 16:54
 */
public interface IDemoService {
    /**
     * 查询测试申请列表
     *
     * @return
     */
    Map<String, Object> getTestPage(JSONObject jsonObject);

    /**
     * 新建测试申请
     *
     * @param jsonObject
     * @return
     */
    int addApply(JSONObject jsonObject);

    /**
     * 根据iD获取详细信息
     *
     * @param json
     * @return
     */
    Map<String, Object> getTestItemInfo(JSONObject json);

    /**
     * 修改
     */

    int updateTestItemInfo(JSONObject json);

    /**
     * 审核
     */

    int checkTestItemInfo(JSONObject json);

    /**
     * 补充
     */
    int addSupply(JSONObject json);

    /**
     * 延期
     */
    int addDelay(JSONObject json);

    /**
     * 获取补充信息列表
     */
    List<Map<String, Object>> getSupply(JSONObject json);
    /**
     * 获取延期信息列表
     */
    List<Map<String, Object>> getDelay(JSONObject json);

    /**
     * 测试完成
     */
    int testComplete(JSONObject json);

    /**
     * 导出
     */
    List<Map<String, Object>> export(JSONObject json);
}

