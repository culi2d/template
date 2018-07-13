package com.shop.response;

public class ResponseData<T> extends ResponseNoData {
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
