package com.starter.repository;

import com.starter.model.CartItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory repository for CartItem entities.
 * Uses ConcurrentHashMap for thread-safe operations.
 */
@Repository
public class CartItemRepository {

    private final ConcurrentHashMap<Long, CartItem> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Save a cart item.
     * If the item has no ID, generates a new one.
     *
     * @param item the cart item to save
     * @return the saved cart item
     */
    public CartItem save(CartItem item) {
        if (item.getId() == null) {
            item.setId(idGenerator.getAndIncrement());
        }
        storage.put(item.getId(), item);
        return item;
    }

    /**
     * Find all cart items.
     *
     * @return list of all cart items
     */
    public List<CartItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Find a cart item by ID.
     *
     * @param id the cart item ID
     * @return an Optional containing the cart item if found
     */
    public Optional<CartItem> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Delete a cart item by ID.
     *
     * @param id the cart item ID
     * @return true if the item was deleted, false if not found
     */
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    /**
     * Check if a cart item exists by ID.
     *
     * @param id the cart item ID
     * @return true if the item exists
     */
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
