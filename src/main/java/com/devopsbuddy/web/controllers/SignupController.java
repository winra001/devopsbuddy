package com.devopsbuddy.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.web.domain.frontend.ProAccountPayload;

@Controller
public class SignupController {

	private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

	public static final String SIGNUP_URL_MAPPING = "/signup";

	public static final String PAYLOAD_MODEL_KEY_NAME = "payload";

	public static final String SUBSCRIPTION_VIEW_NAME = "registration/signup";

	@RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
	public String signupGet(@RequestParam("planId") int planId, ModelMap model) {
		if (planId != PlansEnum.BASIC.getId() && planId != PlansEnum.PRO.getId()) {
			throw new IllegalArgumentException("Plan id is not valid");
		}

		model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new ProAccountPayload());

		return SUBSCRIPTION_VIEW_NAME;
	}
}
