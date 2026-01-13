package com.starter.service;

import com.starter.model.CartItem;
import com.starter.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for cart operations.
 * Contains business logic for managing cart items.
 */
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Create a new cart item.
     *
     * @param cartItem the cart item to create
     * @return the created cart item
     */
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    /**
     * Get all cart items.
     *
     * @return list of all cart items
     */
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    /**
     * Get a cart item by ID.
     *
     * @param id the cart item ID
     * @return an Optional containing the cart item if found
     */
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    /**
     * Update a cart item.
     *
     * @param id the cart item ID
     * @param updatedItem the updated cart item data
     * @return an Optional containing the updated cart item if found
     */
    public Optional<CartItem> updateCartItem(Long id, CartItem updatedItem) {
        if (!cartItemRepository.existsById(id)) {
            return Optional.empty();
        }
        updatedItem.setId(id);
        return Optional.of(cartItemRepository.save(updatedItem));
    }

    /**
     * Delete a cart item by ID.
     *
     * @param id the cart item ID
     * @return true if the item was deleted, false if not found
     */
    public boolean deleteCartItem(Long id) {
        return cartItemRepository.deleteById(id);
    }
}
