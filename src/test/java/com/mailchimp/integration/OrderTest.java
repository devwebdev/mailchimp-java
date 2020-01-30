package com.mailchimp.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collections;

import com.mailchimp.domain.customer.Customer;
import com.mailchimp.domain.order.OrderLine;
import org.junit.Test;

import com.mailchimp.domain.order.Order;
import com.mailchimp.domain.order.OrderCreate;

public class OrderTest extends AbstractIntegrationTest {

    @Test
    public void testCreateOrderShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        Customer customer = new Customer();
        customer.setId("customer_id");
        customer.setEmailAddress("email_address");
        OrderLine orderLine = new OrderLine();
        orderLine.setId("order_line_id");
        orderLine.setPrice(2500);
        OrderCreate order = OrderCreate.builder()
                .setId("order_id")
                .setCustomer(customer)
                .setLines(Collections.singletonList(orderLine)).build();

        expectPost("/3.0/ecommerce/stores/store_id/orders", "order/create.json", "order/create.json");
        // WHEN
        Order created = mailChimpClient.create("store_id", order);
        // THEN
        assertNotNull(created);
    }

    @Test
    public void testDeleteOrderShouldMapTowardsMailChimp() {
        // GIVEN
        expectDelete("/3.0/ecommerce/stores/store_id/orders/order_id");
        // WHEN
        mailChimpClient.removeOrder("store_id","order_id");
        // THEN no exception occurs
    }


    @Test
    public void testUpdateOrderShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        expectPost("/3.0/ecommerce/stores/store_id/orders/order_id", "order/create.json", "order/update.json");
        // WHEN
        Customer customer = new Customer();
        customer.setId("customer_id");
        customer.setEmailAddress("email_address");
        OrderLine orderLine = new OrderLine();
        orderLine.setId("order_line_id");
        orderLine.setPrice(2500);
        OrderCreate order = OrderCreate.builder()
                .setId("order_id")
                .setCustomer(customer)
                .setLines(Collections.singletonList(orderLine)).build();
        Order updated = mailChimpClient.updateOrder("store_id", "order_id", order);
        // THEN
        assertNotNull(updated);
        assert (updated.getLines().get(0).getPrice() == 3000);
    }

}
