package com.micromall.web.resp;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * <pre>
 * 接口调用返回体
 * 所有返回的代码都应该在{@link Ret}中有定义
 *
 * @author z.x
 * @date 2015年7月31日 下午2:29:35
 */
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int    code;
	private String msg;
	private T      data;

	public ResponseEntity() {
		this.code = Ret.Success.getCode();
		this.msg = Ret.Success.getMessage();
	}

	public ResponseEntity(Ret ret, T data) {
		super();
		this.code = ret.getCode();
		this.msg = ret.getMessage();
		this.data = data;
	}

	public ResponseEntity(Ret ret) {
		super();
		this.code = ret.getCode();
		this.msg = ret.getMessage();
	}

	public ResponseEntity(Ret ret, String msg) {
		super();
		this.code = ret.getCode();
		if (StringUtils.isNotEmpty(msg)) {
			this.msg = msg;
		} else {
			this.msg = ret.getMessage();
		}
	}

	public ResponseEntity(T data) {
		super();
		this.code = Ret.Success.getCode();
		this.msg = Ret.Success.getMessage();
		this.data = data;
	}

	public static <T> ResponseEntity<T> Success() {
		return new ResponseEntity<>(Ret.Success);
	}

	public static <T> ResponseEntity<T> Success(T data) {
		return new ResponseEntity<>(Ret.Success, data);
	}

	public static <T> ResponseEntity<T> Failure(String msg) {
		return new ResponseEntity<>(Ret.Error, msg);
	}

	public static <T> ResponseEntity<T> NotLogin(T data) {
		return new ResponseEntity<>(Ret.NotLogin, data);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String toJSONString() {
		return JSON.toJSONString(this);
	}

	@Override
	public String toString() {
		return "ResponseEntity{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
