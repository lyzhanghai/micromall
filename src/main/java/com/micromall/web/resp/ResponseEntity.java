package com.micromall.web.resp;

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
		this.code = Ret.ok.getCode();
		this.msg = Ret.ok.getMessage();
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
		if (StringUtils.isNotEmpty(msg)) this.msg = msg;
		else this.msg = ret.getMessage();
	}

	public ResponseEntity(T data) {
		super();
		this.code = Ret.ok.getCode();
		this.msg = Ret.ok.getMessage();
		this.data = data;
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

	@Override
	public String toString() {
		return "ResponseEntity{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
