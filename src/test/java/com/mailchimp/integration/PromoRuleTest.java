package com.mailchimp.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.mailchimp.domain.promo.PromoRule;
import com.mailchimp.domain.promo.PromoRuleCreate;
import com.mailchimp.domain.promo.PromoRules;

public class PromoRuleTest extends AbstractIntegrationTest {

    @Test
    public void testCreateShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        PromoRuleCreate rule = PromoRuleCreate.builder()
                .setId("promo-rule")
                .setDescription("description")
                .setTarget(PromoRuleCreate.Target.total)
                .setType(PromoRuleCreate.Type.percentage)
                .setAmount(0.5f).build();
        expectPost("/3.0/ecommerce/stores/store_id/promo-rules", "promo-rules/create.json", "promo-rules/create.json");
        // WHEN
        PromoRule promoRule = mailChimpClient.createPromoRule("store_id", rule);
        // THEN
        assertNotNull(promoRule);
    }

    @Test
    public void testReadAllShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        expectGet("/3.0/ecommerce/stores/store_id/promo-rules", "promo-rules/list.json");
        // WHEN
        PromoRules promoRules = mailChimpClient.getPromoRules("store_id");
        // THEN
        assertNotNull(promoRules);
    }

    @Test
    public void testReadPromoRuleShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        expectGet("/3.0/ecommerce/stores/store_id/promo-rules/promo_rule_id", "promo-rules/single.json");
        // WHEN
        PromoRule promoRule = mailChimpClient.getPromoRule("store_id", "promo_rule_id");
        // THEN
        assertNotNull(promoRule);
    }

    @Test
    public void testUpdatePromoRuleShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        expectPatch("/3.0/ecommerce/stores/store_id/promo-rules/promo_rule_id", "promo-rules/update.json", "promo-rules/single.json");
        // WHEN
        PromoRule promoRule = new PromoRule();
        promoRule.setAmount(30);
        promoRule.setType(PromoRuleCreate.Type.fixed);
        PromoRule updated = mailChimpClient.updatePromoRule("store_id", "promo_rule_id", promoRule);
        // THEN
        assertNotNull(updated);
    }

    @Test
    public void testDeleteShouldMapTowardsMailChimp() {
        // GIVEN
        expectDelete("/3.0/ecommerce/stores/store_id/promo-rules/promo_rule_id");
        // WHEN
        mailChimpClient.deletePromoRule("store_id", "promo_rule_id");
        // THEN
    }

}
