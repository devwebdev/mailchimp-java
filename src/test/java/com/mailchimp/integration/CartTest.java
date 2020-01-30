package com.mailchimp.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import com.mailchimp.domain.cart.Cart;
import com.mailchimp.domain.cart.CartCreate;
import com.mailchimp.domain.cart.CartLine;
import com.mailchimp.domain.customer.Customer;

public class CartTest extends AbstractIntegrationTest {

    @Test
    public void testCartCreateShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        Customer customer = new Customer().setId("customer_id")
                .setEmailAddress("email@email.com");
        CartCreate cartCreate = CartCreate.builder()
                .setId("cart_id")
                .setCustomer(customer)
                .setOrderTotal(5000L)
                .setLines(Collections
                        .singletonList(new CartLine("cart_line_id", "product_id", "product_variant_id", 1, 2990.0)))
                .setCurrencyCode("HUF").build();
        expectPost("/3.0/ecommerce/stores/store_id/carts", "cart/create.json", "cart/create.json");
        // WHEN
        Cart cart = mailChimpClient.create("store_id", cartCreate);
        // THEN
        assertNotNull(cart);
    }

}
