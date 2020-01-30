package com.mailchimp.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductVariant {

    private String id;
    private String title;
    private String url;
    private String sku;
    private double price;
    @JsonProperty("inventory_quantity")
    private int inventoryQuantity;
    @JsonProperty("image_url")
    private String imageUrl;
    private String backorders;
    private String visibility;

    public String getId() {
        return id;
    }

    public ProductVariant setId(final String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductVariant setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ProductVariant setUrl(final String url) {
        this.url = url;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductVariant setSku(final String sku) {
        this.sku = sku;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ProductVariant setPrice(final double price) {
        this.price = price;
        return this;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public ProductVariant setInventoryQuantity(final int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductVariant setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getBackorders() {
        return backorders;
    }

    public ProductVariant setBackorders(final String backorders) {
        this.backorders = backorders;
        return this;
    }

    public String getVisibility() {
        return visibility;
    }

    public ProductVariant setVisibility(final String visibility) {
        this.visibility = visibility;
        return this;
    }
}
