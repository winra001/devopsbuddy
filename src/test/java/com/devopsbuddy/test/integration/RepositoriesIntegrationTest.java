package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoriesIntegrationTest {

	private static final int BASIC_PLAN_ID = 1;
	private static final int BASIC_ROLE_ID = 1;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}

	@Test
	public void testCreateNewPlan() {
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(BASIC_PLAN_ID);
		Assert.assertNotNull(retrievedPlan);
	}

	@Test
	public void testCreateNewRole() {
		Role basicRole = createBasicRole();
		roleRepository.save(basicRole);
		Role retrievedRole = roleRepository.findOne(BASIC_ROLE_ID);
		Assert.assertNotNull(retrievedRole);
	}

	@Test
	public void createNewUser() {
		// Create and save a Plan record
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);

		// Create User instance and set the Plan saved entity as Foreign Key
		User basicUser = createBasicUser();
		basicUser.setPlan(basicPlan);

		// Sets the Set<UserRole> collection in the User entity.
		// We add a UserRole object set with the User and Role objects we've just created
		Role basicRole = createBasicRole();
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
		userRoles.add(userRole);

		// IMPORTANT!!!
		// To add values to a collection within a JPA Entity,
		// always use the getter method first and all the objects afterwards.
		basicUser.getUserRoles().addAll(userRoles);

		// Saves the other side of the User to Roles relationship
		// by persisting all Roles in the UserRoles collection
		for (UserRole ur : userRoles) {
			roleRepository.save(ur.getRole());
		}

		// Now that all relationship entities have been saved, it saves the User entity.
		basicUser = userRepository.save(basicUser);

		User newlyCreatedUser = userRepository.findOne(basicUser.getId());

		// If all relationships contain data after running the findOne method,
		// it means our Repositories work correctly.
		Assert.assertNotNull(newlyCreatedUser);
		Assert.assertTrue(newlyCreatedUser.getId() != 0);
		Assert.assertNotNull(newlyCreatedUser.getPlan());
		Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
		Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
		for (UserRole ur : newlyCreatedUserRoles) {
			Assert.assertNotNull(ur.getRole());
			Assert.assertNotNull(ur.getRole().getId());
		}
	}

	private Plan createBasicPlan() {
		Plan plan = new Plan();
		plan.setId(BASIC_PLAN_ID);
		plan.setName("Basic");
		return plan;
	}

	private Role createBasicRole() {
		Role role = new Role();
		role.setId(BASIC_ROLE_ID);
		role.setName("ROLE_USER");
		return role;
	}

	private User createBasicUser() {
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
