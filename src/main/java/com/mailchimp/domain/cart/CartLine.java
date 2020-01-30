package com.mailchimp.domain.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartLine {

    private String id;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("product_variant_id")
    private String productVariantId;
    private int quantity;
    private double price;

    public CartLine() {
    }

    public CartLine(final String id, final String productId, final String productVariantId, final int quantity,
            final double price) {
        this.id = id;
        this.productId = productId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public CartLine setId(final String id) {
        this.id = id;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public CartLine setProductId(final String productId) {
        this.productId = productId;
        return this;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public CartLine setProductVariantId(final String productVariantId) {
        this.productVariantId = productVariantId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartLine setQuantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public CartLine setPrice(final double price) {
        this.price = price;
        return this;
    }
}
