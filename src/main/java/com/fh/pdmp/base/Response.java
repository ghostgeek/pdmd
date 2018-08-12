package com.fh.pdmp.base;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = -7711243398254471219L;

	/**
	 * 操作状态码
	 */
	private int code = 0;

	/**
	 * 操作返回信息
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private Object data;

	/**
	 * 分页对象
	 */
	private Pager page;

	/**
	 * 无参构造
	 */
	private Response() {
	}

	private Response(int code, String msg, Object data, Pager page) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.page = page;
	}

	public static Response success() {
		return new Response(ResponseConstant.CODE_SUCCESS, ResponseConstant.MSG_SUCCESS, null, null);
	}

	public static Response success(Object data) {
		return new Response(ResponseConstant.CODE_SUCCESS, ResponseConstant.MSG_SUCCESS, data, null);
	}

	public static Response success(Object data, Pager page) {
		return new Response(ResponseConstant.CODE_SUCCESS, ResponseConstant.MSG_SUCCESS, data, page);
	}

	public static Response success(String message, Object data, Pager page) {
		return new Response(ResponseConstant.CODE_SUCCESS, message, data, page);
	}

	public static Response error() {
		return new Response(ResponseConstant.CODE_ERROR, ResponseConstant.MSG_ERROR, null, null);
	}

	public static Response error(String msg) {
		return new Response(ResponseConstant.CODE_ERROR, msg, null, null);
	}

	public static Response error(Object data) {
		return new Response(ResponseConstant.CODE_ERROR, ResponseConstant.MSG_ERROR, data, null);
	}

	public static Response error(Object data, Pager page) {
		return new Response(ResponseConstant.CODE_ERROR, ResponseConstant.MSG_ERROR, data, page);
	}

	public static Response error(String message, Object data, Pager page) {
		return new Response(ResponseConstant.CODE_ERROR, message, data, page);
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Pager getPage() {
		return this.page;
	}

	public void setPage(Pager page) {
		this.page = page;
	}
}
