package com.fh.pdmp.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fh.pdmp.base.Response;
import com.fh.pdmp.demo.entity.User;
import com.fh.pdmp.demo.service.TestService;
import com.fh.pdmp.demo.util.ExcelUtils;
import com.fh.pdmp.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Administrator on 2018/6/17 0017.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/test")
public class TesetController {
    @Autowired
    TestService testService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String json(HttpServletRequest request) {
        HashMap<String, Object> responseBody = new HashMap<String, Object>();
        responseBody.put("code", 200);
        responseBody.put("message", "Create success.");
        return JSON.toJSONString(responseBody, SerializerFeature.WriteNonStringValueAsString);
    }

    /**
     * RequestMapping 用来映射一个请求和请求的方法
     * value="/register" 表示请求由 register 方法进行处理
     */
    @RequestMapping(value = "/register")
    public String Register(@ModelAttribute("form") User user, Model model) {  // user:视图层传给控制层的表单对象；model：控制层返回给视图层的对象
        // 在 model 中添加一个名为 "user" 的 user 对象
        model.addAttribute("user", user);
        // 返回一个字符串 " success" 作为视图名称
        return "success";
    }

    /**
     * 分页列表数据
     *
     * @return rsp
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response progresslist(HttpServletRequest request, @RequestParam String pageNo, @RequestParam String pageSize) {
       /* response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");*/
        JSONObject json = new JSONObject();
        json.put("pageNo", pageNo);
        json.put("pageSize", pageSize);
        Map<String, Object> result = testService.getTestPage(json);
        return Response.success(result);
    }

    /**
     * 新增测试申请
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addApply", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public boolean addApply(HttpServletRequest request, @RequestBody Map map) {
        //JSONObject json = JSONObject.parseObject(request.getParameter("pageinfo"));
        JSONObject json = new JSONObject();
        json.put("create_time", Util.getCurrentTime());
        json.put("test_name", map.get("TEST_NAME"));
        json.put("module_name", map.get("MODULE_NAME"));
        json.put("test_content", map.get("TEST_CONTENT"));
        json.put("md5", map.get("MD5"));
        json.put("ftp_url", map.get("FTP_URL"));
        json.put("level", map.get("LEVEL"));
        json.put("depondency", map.get("DEPONDENCY"));
        String expect_time = Util.dateToStamp(map.get("EXPECT_TIME").toString(), "yyyy-MM-dd");
        json.put("expect_time", expect_time);
        json.put("status", 0);
        json.put("jira_url", "");
        json.put("apply_user", "36");
        int result = testService.addApply(json);
        return result == 1;
    }

    /**
     * 根据iD获取详细信息
     * test/search/1
     *
     * @return rsp
     */
    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response getTestItemInfo(@PathVariable String id) {
        JSONObject json = new JSONObject();
        json.put("testid", id);
        Map<String, Object> result = testService.getTestItemInfo(json);
        return Response.success(result);
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateApply", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public boolean updateTestItemInfo(HttpServletRequest request, @RequestBody Map map) {
        //JSONObject json = JSONObject.parseObject(request.getParameter("pageinfo"));
        JSONObject json = new JSONObject();
        json.put("module_name", map.get("MODULE_NAME"));
        json.put("test_name", map.get("TEST_NAME"));
        json.put("test_content", map.get("TEST_CONTENT"));
        json.put("md5", map.get("MD5"));
        json.put("ftp_url", map.get("FTP_URL"));
        json.put("level", map.get("LEVEL"));
        json.put("depondency", map.get("DEPONDENCY"));
        String expect_time = Util.dateToStamp(map.get("EXPECT_TIME").toString(), "yyyy-MM-dd");
        json.put("expect_time", expect_time);
        json.put("testid", map.get("TEST_PROCESS_ID"));
        int result = testService.updateTestItemInfo(json);
        return result == 1;
    }

    /**
     * 审核
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkTestItemInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response checkTestItemInfo(HttpServletRequest request, @RequestBody Map map) {
        //JSONObject json = JSONObject.parseObject(request.getParameter("pageinfo"));
        JSONObject json = new JSONObject();
        String start_time = Util.dateToStamp(map.get("START_TIME").toString(), "yyyy-MM-dd");
        String end_time = Util.dateToStamp(map.get("END_TIME").toString(), "yyyy-MM-dd");
        String user = map.get("TEST_USER").toString();
        json.put("start_time", start_time);
        json.put("end_time", end_time);
        json.put("jira_url", map.get("JIRA_URL"));
        json.put("testid", map.get("TEST_PROCESS_ID"));
        json.put("status", map.get("CHECK_STATUS"));
        json.put("test_user", "1");
        int result = testService.checkTestItemInfo(json);
        Map<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("CHECK_STATUS", map.get("CHECK_STATUS"));
        return result == 1 ? Response.success(resultmap) : Response.error();
    }

    /**
     * 新增补充信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addSupply", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response addSupply(HttpServletRequest request, @RequestBody Map map) {
        //JSONObject json = JSONObject.parseObject(request.getParameter("pageinfo"));
        JSONObject json = new JSONObject();
        json.put("testid", map.get("TEST_PROCESS_ID"));
        json.put("create_time", Util.getCurrentTime());
        json.put("add_content", map.get("ADD_CONTENT"));
        json.put("edit_user", 36);
        int result = testService.addSupply(json);
        Map<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("CREATE_TIME", Util.stampToDate(json.getString("create_time"), "yyyy-MM-dd HH:mm:ss"));
        return result == 1 ? Response.success(resultmap) : Response.error();
    }

    /**
     * 获取补充信息列表
     */
    @RequestMapping(value = "/getSupply", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response getSupply(@RequestParam String id) {
        JSONObject json = new JSONObject();
        json.put("testid", id);
        List<Map<String, Object>> result = testService.getSupply(json);
        return Response.success(result);
    }

    /**
     * 新增延期信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addDelay", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response addDelay(HttpServletRequest request, @RequestBody Map map) {
        JSONObject json = new JSONObject();
        json.put("testid", map.get("TEST_PROCESS_ID"));
        json.put("end_time", Util.dateToStamp(String.valueOf(map.get("END_TIME")), "yyyy-MM-dd"));
        json.put("create_time", Util.getCurrentTime());
        json.put("delay_reason", map.get("DELAY_REASON"));
        json.put("edit_user", 36);
        int result = testService.addDelay(json);
        Map<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("CREATE_TIME", Util.stampToDate(json.getString("create_time"), "yyyy-MM-dd HH:mm:ss"));
        return result == 1 ? Response.success(resultmap) : Response.error();
    }

    /**
     * 获取延期信息列表
     */
    @RequestMapping(value = "/getDelay", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response getDelay(@RequestParam String id) {
        JSONObject json = new JSONObject();
        json.put("testid", id);
        List<Map<String, Object>> result = testService.getDelay(json);
        return Response.success(result);
    }

    /**
     * 测试完成
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/testComplete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Response testComplete(HttpServletRequest request, @RequestBody Map map) {
        JSONObject json = new JSONObject();
        json.put("status", 2);
        json.put("check_user", 36);
        json.put("testid", map.get("TEST_PROCESS_ID"));
        int result = testService.testComplete(json);
        return result == 1 ? Response.success() : Response.error();
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/exportTestList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public void export(HttpServletResponse response) {
        JSONObject json = new JSONObject();
        List<Map<String, Object>> list = testService.export(json);
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects0 = new Object[list.get(0).size() + 1];
        dataList.add(objects0);
        Map<String, Object> map2 = list.get(0);
        Set<String> keySet = map2.keySet();
        int m = 1;
        for (String key : keySet) {
            dataList.get(0)[m++] = key;
        }
        for (int i = 0; i < list.size(); i++) {
            Object[] objects = new Object[list.get(i).size() + 1];
            dataList.add(objects);
            int j = 1;
            Map<String, Object> map3 = list.get(i);
            Set<String> keySet1 = map3.keySet();
            for (String key : keySet1) {
                dataList.get(i + 1)[j++] = map3.get(key);
            }
        }
        // 使用流将数据导出
        OutputStream out = null;
        try {
            // 防止中文乱码

            String headStr = "attachment; filename=\""
                    + new String(("测试" +/*下载后的文件名*/".xlsx").getBytes("gb2312"), "ISO8859-1") + "\"";
            response.setContentType("octets/stream");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            out = response.getOutputStream();
            ExcelUtils ex = new ExcelUtils("测试",/*表的标题*/
                    new String[]{"测试"/*内容标题*/},
                    dataList/*数据准备  ---数据类型为List<Object[]>*/);
            ex.export(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}