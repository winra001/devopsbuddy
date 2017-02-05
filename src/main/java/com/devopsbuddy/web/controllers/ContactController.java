package com.devopsbuddy.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;

@Controller
public class ContactController {

	private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

	/** The key which identifies the feedback payload in the Model. */
	private static final String FEEDBACK_MODEL_KEY = "feedback";

	/** The Contact Us view name */
	private static final String CONTACT_US_VIEW_NAME = "contact/contact"; // "folder/view name"

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contactGet(ModelMap model) {
		FeedbackPojo feedbackPojo = new FeedbackPojo();
		model.addAttribute(ContactController.FEEDBACK_MODEL_KEY, feedbackPojo);
		return ContactController.CONTACT_US_VIEW_NAME;
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) FeedbackPojo feedback) {
		LOG.debug("Feedback POJO content: {}", feedback);
		return ContactController.CONTACT_US_VIEW_NAME;
	}

}