package com.shop.enumerate;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
	USER(1, "ROLE_USER"), ADMIN(2, "ADMIN");
	private static final Map<Integer, UserType> USER_TYPE_MAP = new HashMap<Integer, UserType>();
	static {
		for (UserType myEnum : values()) {
			USER_TYPE_MAP.put(myEnum.getValue(), myEnum);
		}
	}
	private final int value;
	private final String role;

	private UserType(int value, String role) {
		this.value = value;
		this.role = role;
	}

	public int getValue() {
		return value;
	}

	public String getRole() {
		return role;
	}

	public static UserType getByValue(int value) {
		return USER_TYPE_MAP.get(value);
	}
}
