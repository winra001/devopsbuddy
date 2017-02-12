package com.devopsbuddy.enums;

public enum RolesEnum {

	/**
	 * If you want to create a "BASIC" role, Spring Security will require
	 * the value ROLE_BASIC to be assigned to a GrantedAuthority
	 */
	BASIC(1, "ROLE_BASIC"),
	PRO(2, "ROLE_PRO"),
	ADMIN(3, "ROLE_ADMIN");

	private final int id;

	private final String roleName;

	private RolesEnum(int id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

}
