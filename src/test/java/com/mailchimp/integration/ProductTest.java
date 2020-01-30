package com.mailchimp.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import com.mailchimp.domain.product.Product;
import com.mailchimp.domain.product.ProductCreate;
import com.mailchimp.domain.product.ProductVariant;

public class ProductTest extends AbstractIntegrationTest {

    @Test
    public void testCreateProductShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId("product_variant_id");
        productVariant.setTitle("product_variant_title");
        productVariant.setPrice(2990);
        ProductCreate product = ProductCreate.builder()
                .setId("product_id")
                .setTitle("product_title")
                .setVariants(Collections.singletonList(productVariant)).build();
        expectPost("/3.0/ecommerce/stores/store_id/products", "product/create.json", "product/create.json");
        // WHEN
        Product created = mailChimpClient.create("store_id", product);
        // THEN
        assertNotNull(created);
    }

    @Test
    public void testDeleteShouldMapTowardsMailChimp() {
        // GIVEN
        expectDelete("/3.0/ecommerce/stores/store_id/products/product_id");
        // WHEN
        mailChimpClient.removeProduct("store_id", "product_id");
        // THEN
    }

    @Test
    public void testUpdateProductShouldMapTowardsMailChimp() throws IOException {
        // GIVEN
        expectPatch("/3.0/ecommerce/stores/store_id/products/product_id", "product/create.json", "product/update.json");
        // WHEN
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId("product_variant_id");
        productVariant.setTitle("product_variant_title");
        productVariant.setPrice(2990);
        ProductCreate product = ProductCreate.builder()
                .setId("product_id")
                .setTitle("product_title")
                .setVariants(Collections.singletonList(productVariant)).build();
        Product updated = mailChimpClient.updateProduct("store_id", "product_id", product);
        // THEN
        assertNotNull(updated);
        assert(updated.getVariants().get(0).getPrice() == 3000);
    }
}
