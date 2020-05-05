/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.binbinhan.common.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static com.binbinhan.common.controller.R error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static com.binbinhan.common.controller.R error(String msg) {
		return error(500, msg);
	}
	
	public static com.binbinhan.common.controller.R  error(int code, String msg) {
		com.binbinhan.common.controller.R r = new com.binbinhan.common.controller.R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static com.binbinhan.common.controller.R  ok(String msg) {
		com.binbinhan.common.controller.R  r = new com.binbinhan.common.controller.R ();
		r.put("msg", msg);
		return r;
	}
	
	public static com.binbinhan.common.controller.R  ok(Map<String, Object> map) {
		com.binbinhan.common.controller.R  r = new com.binbinhan.common.controller.R ();
		r.putAll(map);
		return r;
	}
	
	public static com.binbinhan.common.controller.R  ok() {
		return new com.binbinhan.common.controller.R ();
	}

	@Override
	public com.binbinhan.common.controller.R  put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
