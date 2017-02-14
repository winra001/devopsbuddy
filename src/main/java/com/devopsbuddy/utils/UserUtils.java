package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;

public class UserUtils {

	/**
	 * Not instantiable.
	 */
	private UserUtils() {
		throw new AssertionError("Non instantiable");
	}

	/**
	 * Creates a user with basic attributes set.
	 * 
	 * @return A User entity
	 */
	public static User createBasicUser() {
		User user = new User();
		user.setUsername("basicUser");
		user.setPassword("secret");
		user.setEmail("me@example.com");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setPhoneNumber("1234567890");
		user.setCountry("AU");
		user.setEnabled(true);
		user.setDescription("A basic user");
		user.setProfileImageUrl("https://blabla.images.com/basicuser");

		return user;
	}

}
