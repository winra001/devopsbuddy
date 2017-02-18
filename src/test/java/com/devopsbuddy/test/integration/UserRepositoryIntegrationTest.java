package com.devopsbuddy.test.integration;

import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

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
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan);
	}

	@Test
	public void testCreateNewRole() throws Exception {
		Role basicRole = createRole(RolesEnum.BASIC);
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

	@Test
	public void testGetUserByEmail() throws Exception {
		User user = createUser(testName);

		User newlyFoundUser = userRepository.findByEmail(user.getEmail());
		Assert.assertNotNull(newlyFoundUser);
		Assert.assertNotNull(newlyFoundUser.getId());
	}

	@Test
	public void testUpdateUserPassword() throws Exception {
		User user = createUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());

		String newPassword = UUID.randomUUID().toString();

		userRepository.updateUserPassword(user.getId(), newPassword);

		user = userRepository.findOne(user.getId());
		Assert.assertEquals(newPassword, user.getPassword());
	}

}
