package cn.jcm.core.domain;

import lombok.Data;

@Data
public class CommonResult {
	private Integer status = 200;
	private String message;
	private Object data;

	public CommonResult() {
	}

	public CommonResult(String message) {
		this.message = message;
	}
}
