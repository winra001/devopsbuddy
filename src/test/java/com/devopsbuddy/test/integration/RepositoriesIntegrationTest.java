package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
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
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoriesIntegrationTest {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Rule
	public TestName testName = new TestName();

	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}

	@Test
	public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan);
	}

	@Test
	public void testCreateNewRole() throws Exception {
		Role basicRole = createBasicRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);
		Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}

	@Test
	public void createNewUser() throws Exception {
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@devopsbuddy.com";

		User basicUser = createUser(username, email);

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

	@Test
	public void testDeleteUser() throws Exception {
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@devopsbuddy.com";

		User basicUser = createUser(username, email);
		userRepository.delete(basicUser.getId());
	}

	private Plan createBasicPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}

	private Role createBasicRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}

	private User createUser(String username, String email) {
		// Create and save a Plan record
		Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);

		// Create User instance and set the Plan saved entity as Foreign Key
		User basicUser = UserUtils.createBasicUser(username, email);
		basicUser.setPlan(basicPlan);

		Role basicRole = createBasicRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);

		// Sets the Set<UserRole> collection in the User entity.
		// We add a UserRole object set with the User and Role objects we've
		// just created
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole);
		userRoles.add(userRole);

		// IMPORTANT!!!
		// To add values to a collection within a JPA Entity,
		// always use the getter method first and all the objects afterwards.
		basicUser.getUserRoles().addAll(userRoles);

		basicUser = userRepository.save(basicUser);

		return basicUser;
	}

}
