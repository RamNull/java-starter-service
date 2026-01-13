package com.starter.controller;

import com.starter.model.CartItem;
import com.starter.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for cart operations.
 * Provides endpoints to create, read, update, and delete cart items.
 */
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Get all cart items.
     *
     * @return list of all cart items
     */
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return ResponseEntity.ok(cartService.getAllCartItems());
    }

    /**
     * Get a cart item by ID.
     *
     * @param id the cart item ID
     * @return the cart item if found, 404 otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        return cartService.getCartItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new cart item.
     *
     * @param cartItem the cart item to create
     * @return the created cart item with 201 status
     */
    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@Valid @RequestBody CartItem cartItem) {
        CartItem created = cartService.createCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing cart item.
     *
     * @param id the cart item ID
     * @param cartItem the updated cart item data
     * @return the updated cart item if found, 404 otherwise
     */
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, 
                                                    @Valid @RequestBody CartItem cartItem) {
        return cartService.updateCartItem(id, cartItem)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a cart item by ID.
     *
     * @param id the cart item ID
     * @return 204 if deleted, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (cartService.deleteCartItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
