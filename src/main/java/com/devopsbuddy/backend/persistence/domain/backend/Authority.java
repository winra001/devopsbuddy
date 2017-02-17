package com.devopsbuddy.backend.persistence.domain.backend;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 397779181559565849L;

	private final String authority;

	public Authority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
