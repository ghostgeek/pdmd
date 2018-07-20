package com.shfh.pdmp.demo.curd.controller;

import com.alibaba.fastjson.JSON;
import com.shfh.pdmp.base.Pager;
import com.shfh.pdmp.base.Response;
import com.shfh.pdmp.demo.curd.entities.UserEntity;
import com.shfh.pdmp.demo.curd.service.impl.DemoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/users/")
public class CurdController{

    private Logger logger = LoggerFactory.getLogger(CurdController.class);

    @Autowired
    DemoServiceImpl demoService;

    @PostMapping
    public ModelAndView add(@ModelAttribute UserEntity user){
        logger.info("进入了add方法："+ JSON.toJSONString(user));
        demoService.save(user);
        return new ModelAndView("demo/curd");
    }


    /**
     * 打开某个页面
     */
    @GetMapping("page")
    public ModelAndView page() throws SQLException{
        return new ModelAndView("demo/curd");
    }


    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Response delete(@PathVariable String id){
        logger.info("进入了delete方法："+ id);
        demoService.delete(id);
        return Response.success("删除Id为"+id+"的用户成功！");
    }


    /**
     * 修改
     */
    @PutMapping
    public ModelAndView update(@ModelAttribute UserEntity user){
        logger.info("进入了update方法："+ JSON.toJSONString(user));
        demoService.update(user);
        logger.info("进入了update方法："+ JSON.toJSONString(user));
        ModelAndView modelAndView = new ModelAndView("demo/curd");
        return modelAndView;
    }

    /**
     * 分页列表数据
     * users/search?username=zhangsan
     * @return rsp
     */
    @GetMapping("/search")
    @ResponseBody
    public Response list(String username,Pager page){
        List<UserEntity> entities = demoService.queryForList(username,page);
        return Response.success(entities,page);
    }

    /**
     * 详情数据
     * @return rsp
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Response detail(@PathVariable String id){
        logger.info("进入了detail方法："+ id);
        return Response.success();
    }
}
