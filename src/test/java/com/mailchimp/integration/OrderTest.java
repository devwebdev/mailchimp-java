package com.mailchimp.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.mailchimp.domain.customer.Customer;
import com.mailchimp.domain.order.Order;
import com.mailchimp.domain.order.OrderCreate;
import com.mailchimp.domain.order.OrderLine;
import com.mailchimp.domain.order.Orders;
import com.mailchimp.query.OrdersQuery;

public class OrderTest extends AbstractIntegrationTest {

    @Test
    public void testCreateShouldMapTowardsMailChimp() throws IOException
    {
        // GIVEN
        Customer customer = new Customer();
        customer.setId("customer_id");
        customer.setEmailAddress("emil@email.com");

        OrderLine orderLine = new OrderLine();
        orderLine.setId("order_line_id");
        orderLine.setPrice(2500);
        orderLine.setDiscount(0);
        orderLine.setProductId("product_id");
        orderLine.setProductVariantId("product_variant_id");

        OrderCreate cartCreate = OrderCreate.builder()
           .setCurrencyCode("EUR")
           .setId("order_id")
           .setCustomer(customer)
           .setLines(Collections.singletonList(orderLine)).build();

        expectPost("/3.0/ecommerce/stores/arandomid/orders", "order/create.json", "order/create.json");
        // WHEN
        Order created = mailChimpClient.createOrder("arandomid", cartCreate);

        // THEN
        assertNotNull(created);
    }

    @Test
    public void testRetrieveShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        expectGet("/3.0/ecommerce/stores/arandomid/orders/order_id", "order/list.json");
        // WHEN
        Order order = mailChimpClient.retrieveOrder("arandomid", "order_id");
        // THEN
        assertNotNull(order);
    }

    @Test
    public void testListShouldMapTowardsMailChimp() throws IOException {
        OrdersQuery ordersQuery = OrdersQuery.firstPage();
        //OrdersQuery ordersQuery = new OrdersQuery(0, 1);
        // GIVEN
        expectGet("/3.0/ecommerce/stores/arandomid/orders", "order/list.json");
        // WHEN
        Orders orders = mailChimpClient.retrieveOrders("arandomid", ordersQuery);
        // THEN
        assertNotNull(orders);
        List<Order> orderList = orders.getOrders();
        assertEquals(1, orderList.size());
        assertEquals("order_id", orderList.get(0).getId());

    }

}
