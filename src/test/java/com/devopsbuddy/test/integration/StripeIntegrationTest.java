package com.devopsbuddy.test.integration;

import static org.hamcrest.CoreMatchers.notNullValue;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopsbuddy.backend.service.StripeService;
import com.devopsbuddy.enums.PlansEnum;
import com.stripe.Stripe;
import com.stripe.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StripeIntegrationTest {

	private static final Logger LOG = LoggerFactory.getLogger(StripeIntegrationTest.class);

	public static final String TEST_CC_NUMBER = "4242424242424242";

	public static final int TEST_CC_EXP_MONTH = 1;

	public static final String TEST_CC_CVC_NBR = "314";

	@Autowired
	private StripeService stripeService;

	@Autowired
	private String stripeKey;

	@Before
	public void init() {
		Assert.assertNotNull(stripeKey);
		Stripe.apiKey = stripeKey;
	}

	@Test
	public void createStripeCustomer() throws Exception {
		Map<String, Object> tokenParams = new HashMap<>();
		Map<String, Object> cardParams = new HashMap<>();
		cardParams.put("number", TEST_CC_NUMBER);
		cardParams.put("exp_month", TEST_CC_EXP_MONTH);
		cardParams.put("exp_year", LocalDate.now(Clock.systemUTC()).getYear() + 1);
		cardParams.put("cvc", TEST_CC_CVC_NBR);
		tokenParams.put("card", cardParams);

		Map<String, Object> customerParams = new HashMap<>();
		customerParams.put("description", "Customer for test@example.com");
		customerParams.put("plan", PlansEnum.PRO.getId());

		String stripeCustomerId = stripeService.createCustomer(tokenParams, customerParams);
		MatcherAssert.assertThat(stripeCustomerId, Is.is(notNullValue()));

		Customer cu = Customer.retrieve(stripeCustomerId);
		cu.delete();
	}

}
