package com.mailchimp;

import com.mailchimp.domain.Batch;
import com.mailchimp.domain.Batches;
import com.mailchimp.domain.CreateBatch;
import com.mailchimp.domain.ListMergeField;
import com.mailchimp.domain.ListMergeFields;
import com.mailchimp.domain.Member;
import com.mailchimp.domain.Members;
import com.mailchimp.domain.Root;
import com.mailchimp.domain.SearchMembers;
import com.mailchimp.domain.Segment;
import com.mailchimp.domain.SegmentCreate;
import com.mailchimp.domain.SegmentModified;
import com.mailchimp.domain.SegmentModify;
import com.mailchimp.domain.Segments;
import com.mailchimp.domain.SubscriberList;
import com.mailchimp.domain.SubscriberLists;
import com.mailchimp.domain.cart.Cart;
import com.mailchimp.domain.cart.CartCreate;
import com.mailchimp.domain.customer.Customer;
import com.mailchimp.domain.customer.CustomerCreate;
import com.mailchimp.domain.customer.Customers;
import com.mailchimp.domain.order.Order;
import com.mailchimp.domain.order.OrderCreate;
import com.mailchimp.domain.order.Orders;
import com.mailchimp.domain.product.Product;
import com.mailchimp.domain.product.ProductCreate;
import com.mailchimp.domain.promo.PromoCode;
import com.mailchimp.domain.promo.PromoCodeCreate;
import com.mailchimp.domain.promo.PromoCodes;
import com.mailchimp.domain.promo.PromoRule;
import com.mailchimp.domain.promo.PromoRuleCreate;
import com.mailchimp.domain.promo.PromoRules;
import com.mailchimp.domain.store.Store;
import com.mailchimp.domain.store.StoreCreate;
import com.mailchimp.domain.store.Stores;
import com.mailchimp.query.BatchesQuery;
import com.mailchimp.query.ListMembersQuery;
import com.mailchimp.query.ListsQuery;
import com.mailchimp.query.OrdersQuery;
import com.mailchimp.query.StoresQuery;

import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

/**
 * @author stevensnoeijen, eamoralesl
 */
public interface MailChimpClient {

    /**
     *
     * @return builder to build the client
     */
    static MailChimpClientBuilder builder() {
        return new MailChimpClientBuilder();
    }

    /**
     * Gets API Root.
     * @return root info about the account
     */
    @RequestLine("GET /3.0/")
    Root getRoot();

    /**
     * Gets member by subscriberHash in list.
     * @param listId if of the list
     * @param subscriberHash hash of member's email (see {@link Member#getSubscriberHash(String)})
     * @return member
     * @throws MailChimpErrorException when listId or subscriberHash was not found
     */
    @RequestLine("GET /3.0/lists/{list-id}/members/{subscriber-hash}")
    Member getListMember(@Param("list-id") String listId, @Param("subscriber-hash") String subscriberHash);

    /**
     * Creates member in list.
     * @param listId id of the list where to create the member in
     * @param member to create
     * @return created member
     * @throws MailChimpErrorException when the list-id was not found
     */
    @RequestLine("POST /3.0/lists/{list-id}/members")
    Member createListMember(@Param("list-id") String listId, Member member);

    /**
     * Updates member in a list.
     * @param listId id of the list where to create the member in
     * @param subscriberHash hash of member's email (see {@link Member#getSubscriberHash(String)})
     * @param member to update
     * @return updated user
     * @throws MailChimpErrorException when the list-id was not found
     */
    @RequestLine("PUT /3.0/lists/{list-id}/members/{subscriber-hash}")
    Member updateListMember(@Param("list-id") String listId, @Param("subscriber-hash") String subscriberHash,
            Member member);

    /**
     * Removes member from list.
     * @param listId id of the list
     * @param subscriberHash hash of member's email (see {@link Member#getSubscriberHash(String)})
     * @throws MailChimpErrorException when listId or subscriberHash was not found
     */
    @RequestLine("DELETE /3.0/lists/{list-id}/members/{subscriber-hash}")
    void removeListMember(@Param("list-id") String listId, @Param("subscriber-hash") String subscriberHash);

    /**
     * Create subscriberList.
     * @param subscriberList to create
     * @return created subscriberList
     */
    @RequestLine("POST /3.0/lists")
    SubscriberList createList(SubscriberList subscriberList);

    /**
     * Removes list.
     * @param listId id of the list
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("DELETE /3.0/lists/{list-id}")
    void removeList(@Param("list-id") String listId);

    /**
     * Gets list.
     * @param listId id of the list
     * @return found list
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("GET /3.0/lists/{list-id}")
    SubscriberList getList(@Param("list-id") String listId);

    /**
     * Gets lists by query.
     * @param query to filter results on
     * @return filtered list of lists
     */
    @RequestLine("GET /3.0/lists")
    SubscriberLists getLists(@QueryMap ListsQuery query);

    /**
     * Gets list members.
     * @param listId id of the list
     * @param query to filter results on
     * @return filtered members
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("GET /3.0/lists/{list-id}/members")
    Members getListMembers(@Param("list-id") String listId, @QueryMap ListMembersQuery query);

    /**
     * Get list's member-fields.
     * @param listId id of the list
     * @return list's member-fields
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("GET /3.0/lists/{list-id}/merge-fields")
    ListMergeFields getListMergeFields(@Param("list-id") String listId);

    /**
     * Create merge-field for list.
     * @param listId id of the list
     * @param mergeField merge-field to create
     * @return created merge-field for the list
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("POST /3.0/lists/{list-id}/merge-fields")
    ListMergeField createMergeField(@Param("list-id") String listId, ListMergeField mergeField);

    /**
     * Removes merge-field from list.
     * @param listId id of the list
     * @param mergeId id of merge-field
     * @throws MailChimpErrorException when listId or mergeId was not found
     */
    @RequestLine("DELETE /3.0/lists/{list-id}/merge-fields/{merge-id}")
    void removeListMergeField(@Param("list-id") String listId, @Param("merge-id") String mergeId);

    /**
     * Create segment in list.
     * @param listId id of the list
     * @param segment segment to create
     * @return created segment
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("POST /3.0/lists/{list-id}/segments")
    Segment createSegment(@Param("list-id") String listId, SegmentCreate segment);

    /**
     * Modify a segment in list.
     * @param listId id of the list
     * @param segmentId id of segment
     * @param segment segment to be modified
     * @return modified segment
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("POST /3.0/lists/{list-id}/segments/{segment-id}")
    SegmentModified modifySegment(@Param("list-id") String listId, @Param("segment-id") Integer segmentId,
            SegmentModify segment);

    /**
     * Get segments in list.
     * @param listId id of the list
     * @return segments of the list
     * @throws MailChimpErrorException when listId was not found
     */
    @RequestLine("GET /3.0/lists/{list-id}/segments")
    Segments getSegments(@Param("list-id") String listId);

    /**
     * Get a segment in the list.
     * @param listId id of the list
     * @param segmentId id of the segment
     * @return segment by id
     * @throws MailChimpErrorException when listId or segmentId was not found
     */
    @RequestLine("GET /3.0/lists/{list-id}/segments/{segment-id}")
    Segment getSegment(@Param("list-id") String listId, @Param("segment-id") Integer segmentId);

    /**
     * Removes segment.
     * @param listId id of the list
     * @param segmentId id of the segment
     * @throws MailChimpErrorException when listId or segmentId was not found
     */
    @RequestLine("DELETE /3.0/lists/{list-id}/segments/{segment-id}")
    void removeSegment(@Param("list-id") String listId, @Param("segment-id") Integer segmentId);

    /**
     * Create batch.
     * @param batch to create
     * @return created batch
     */
    @RequestLine("POST /3.0/batches")
    Batch createBatch(CreateBatch batch);

    /**
     * Get batch.
     * @param batchId id of batch
     * @return found batch
     * @throws MailChimpErrorException when batchId was not found
     */
    @RequestLine("GET /3.0/batches/{batch-id}")
    Batch getBatch(@Param("batch-id") String batchId);

    /**
     * Get batches by filter.
     * @param query to filter
     * @return filtered batches
     */
    @RequestLine("GET /3.0/batches")
    Batches getBatches(@QueryMap BatchesQuery query);

    /**
     * Removes batch.
     * @param batchId id of batch
     * @throws MailChimpErrorException when batchId was not found
     */
    @RequestLine("DELETE /3.0/batches/{batch-id}")
    void removeBatch(@Param("batch-id") String batchId);

    /**
     * Search members by query.
     * @param query to search by
     * @return found members by query
     */
    @RequestLine("GET /3.0/search-members?query={query}")
    SearchMembers searchMembers(@Param("query") String query);

    /**
     * Search members by query and listId.
     * @param query to search by
     * @param listId id of list
     * @return found members by query and listId
     * @throws MailChimpErrorException when batchId was not found
     */
    @RequestLine("GET /3.0/search-members?query={query}&list_id={listId}")
    SearchMembers searchMembers(@Param("query") String query, @Param("listId") String listId);

    /**
     * Search stores by query.
     * @param query to filter.
     * @return found stores
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/")
    Stores getStores(@QueryMap StoresQuery query);

    /**
     * Search stores by id.
     * @param storeId id of store
     * @return found store by the id
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}")
    Store getStore(@Param("storeId") String storeId);

    /**
     * Creates a store.
     * @param store the store to be created
     * @return the created store
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores")
    Store create(StoreCreate store);

    /**
     * Creates a customer.
     * @param customer the customer to be created
     * @return the created customer
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/customers")
    Customer create(@Param("storeId") String storeId, CustomerCreate customer);

    /**
     * Creates a cart.
     * @param cart the cart to be created
     * @return the created cart
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/carts")
    Cart create(@Param("storeId") String storeId, CartCreate cart);

    /**
     * Creates a product.
     * @param product the product to be created
     * @return the created product
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/products")
    Product create(@Param("storeId") String storeId, ProductCreate product);

    /**
     * Removes store.
     * @param storeId id of store
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("DELETE /3.0/ecommerce/stores/{storeId}")
    void removeStore(@Param("storeId") String storeId);

    /**
     * List customers for a store.
     * @param storeId the store id to be created
     * @return the customers
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/customers")
    Customers getCustomers(@Param("storeId") String storeId);

    /**
     * Retrieves a cart by its id.
     * @return the cart if found
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when cartId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/carts/{cartId}")
    Cart getCart(@Param("storeId") String storeId, @Param("cartId") String cartId);

    /**
     * Removes a cart by its id.
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when cartId was not found
     */
    @RequestLine("DELETE /3.0/ecommerce/stores/{storeId}/carts/{cartId}")
    void removeCart(@Param("storeId") String storeId, @Param("cartId") String cartId);

    /**
     * Updates a specific cart.
     * @return the updated cart
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when cartId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/carts/{cartId}")
    @Headers("X-HTTP-Method-Override: PATCH")
    Cart updateCart(@Param("storeId") String storeId, @Param("cartId") String cartId, CartCreate cart);

    /**
     * Creates a new promo rule.
     * @return the created promo rule
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/promo-rules")
    PromoRule createPromoRule(@Param("storeId") String storeId, PromoRuleCreate promoRule);

    /**
     * Gets promo rules for the store.
     * @return the promo rules
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/promo-rules")
    PromoRules getPromoRules(@Param("storeId") String storeId);

    /**
     * Gets a single promo rule from the store by id
     * @return the promo rule
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}")
    PromoRule getPromoRule(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId);

    /**
     * Updates a promo rule
     * @return the updated promo rule
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}")
    @Headers("X-HTTP-Method-Override: PATCH")
    PromoRule updatePromoRule(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId,
            PromoRule promoRule);

    /**
     * Deletes a promo rules from the store by id
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     */
    @RequestLine("DELETE /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}")
    void deletePromoRule(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId);

    /**
     * Creates a promo code for the given promo rule.
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}/promo-codes")
    PromoCode createPromoCode(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId,
            PromoCodeCreate promoCodeCreate);

    /**
     * Returns promo codes for the given promo rule.
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}/promo-codes")
    PromoCodes getPromoCodes(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId);

    /**
     * Returns promo code for the given promo rule and id
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     * @throws MailChimpErrorException when promoCodeId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}/promo-codes/{promoCodeId}")
    PromoCode getPromoCode(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId,
            @Param("promoCodeId") String promoCodeId);


    /**
     * Updates a promo code with the given promo rule and id.
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when promoRuleId was not found
     * @throws MailChimpErrorException when promoCodeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/promo-rules/{promoRuleId}/promo-codes/{promoCodeId}")
    @Headers("X-HTTP-Method-Override: PATCH")
    PromoCode updatePromoCode(@Param("storeId") String storeId, @Param("promoRuleId") String promoRuleId,
            @Param("promoCodeId") String promoCodeId, PromoCode code);

    /**
     * Creates a new order.
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("POST /3.0/ecommerce/stores/{storeId}/orders")
    Order createOrder(@Param("storeId") String storeId, OrderCreate create);

    /**
     * Get orders associated with the store.
     * @throws MailChimpErrorException when storeId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/orders")
    Orders retrieveOrders(@Param("storeId") String storeId, @QueryMap OrdersQuery ordersQuery);

    /**
     * Get order associated with the store and the given id.
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when orderId was not found
     */
    @RequestLine("GET /3.0/ecommerce/stores/{storeId}/orders/{orderId}")
    Order retrieveOrder(@Param("storeId") String storeId, @Param("orderId") String orderId);

    /**
     * Remove order associated with the store and the given id.
     * @throws MailChimpErrorException when storeId was not found
     * @throws MailChimpErrorException when orderId was not found
     */
    @RequestLine("DELETE /3.0/ecommerce/stores/{storeId}/orders/{orderId}")
    void deleteOrder(@Param("storeId") String storeId, @Param("orderId") String orderId);

}
