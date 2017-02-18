package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

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

public abstract class AbstractIntegrationTest {

	@Autowired
	protected PlanRepository planRepository;

	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected UserRepository userRepository;

	protected Plan createPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}

	protected Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}

	protected User createUser(String username, String email) {
		// Create and save a Plan record
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);

		// Create User instance and set the Plan saved entity as Foreign Key
		User basicUser = UserUtils.createBasicUser(username, email);
		basicUser.setPlan(basicPlan);

		Role basicRole = createRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);

		// Sets the Set<UserRole> collection in the User entity.
		// We add a UserRole object set with the User and Role objects we've just created
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

	protected User createUser(TestName testName) {
		return createUser(testName.getMethodName(), testName.getMethodName() + "@devopsbuddy.com");
	}

}
