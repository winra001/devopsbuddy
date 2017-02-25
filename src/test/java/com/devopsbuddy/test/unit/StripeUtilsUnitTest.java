package com.devopsbuddy.test.unit;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopsbuddy.test.integration.StripeIntegrationTest;
import com.devopsbuddy.utils.StripeUtils;
import com.devopsbuddy.web.domain.frontend.ProAccountPayload;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StripeUtilsUnitTest {

	@Test
	public void createStripeTokenParamsFromUserPayload() {
		ProAccountPayload payload = new ProAccountPayload();
		String cardNumber = StripeIntegrationTest.TEST_CC_NUMBER;
		payload.setCardNumber(cardNumber);
		String cardCode = StripeIntegrationTest.TEST_CC_CVC_NBR;
		payload.setCardCode(cardCode);
		String cardMonth = String.valueOf(StripeIntegrationTest.TEST_CC_EXP_MONTH);
		payload.setCardMonth(cardMonth);
		String cardYear = String.valueOf(LocalDate.now(Clock.systemUTC()).getYear() + 1);
		payload.setCardYear(cardYear);

		Map<String, Object> tokenParams = StripeUtils.extractTokenParamsFromSignupPayload(payload);
		Map<String, Object> cardParams = (Map<String, Object>) tokenParams.get(StripeUtils.STRIPE_CARD_KEY);
		MatcherAssert.assertThat(cardNumber, Is.is(cardParams.get(StripeUtils.STRIPE_CARD_NUMBER_KEY)));
		MatcherAssert.assertThat(cardMonth, Is.is(String.valueOf(cardParams.get(StripeUtils.STRIPE_EXPIRY_MONTH_KEY))));
		MatcherAssert.assertThat(cardYear, Is.is(String.valueOf(cardParams.get(StripeUtils.STRIPE_EXPIRY_YEAR_KEY))));
		MatcherAssert.assertThat(cardCode, Is.is(cardParams.get(StripeUtils.STRIPE_CVC_KEY)));
	}

}
