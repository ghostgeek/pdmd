package com.fh.pdmp.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.pdmp.demo.entity.DemoEntity;
import com.fh.pdmp.demo.service.impl.DemoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoServiceImpl demoService;


	@ResponseBody
	@RequestMapping(value = "/getName", method = RequestMethod.GET)
	public String getString() {
		DemoEntity gcZhang = new DemoEntity("张国才", 25, "female");
		return gcZhang.getName();
	}

	@ResponseBody
	@RequestMapping(value = "/getJSONString", method = RequestMethod.GET)
	public String getJSONString() {
		DemoEntity gcZhang = new DemoEntity("张国才", 25, "female");
		return JSONObject.toJSONString(gcZhang);
	}

	@ResponseBody
	@RequestMapping(value = "/getParamsFromGET", method = RequestMethod.GET)
	public String getParamFromGET(@RequestParam String name, @RequestParam int age) {
		return "You mean " + name + " is " + age + " years old ? It's ridiculous!";
	}

	@ResponseBody
	@RequestMapping(value = "/{userId}/updateUserInfo", method = RequestMethod.GET)
	public String updateUserInfo(@PathVariable String userId, @RequestParam int age) {
		return "You have changed user : ZhangGuoCai(id:" + userId + ")'s age to :" + age;
	}
}
