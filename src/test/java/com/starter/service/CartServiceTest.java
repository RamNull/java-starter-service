package com.starter.service;

import com.starter.model.CartItem;
import com.starter.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CartService.
 */
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartService cartService;

    private CartItem testItem;

    @BeforeEach
    void setUp() {
        testItem = new CartItem(1L, "Test Item", 2, new BigDecimal("10.00"));
    }

    @Test
    void createCartItem_ShouldSaveAndReturnItem() {
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(testItem);

        CartItem result = cartService.createCartItem(testItem);

        assertNotNull(result);
        assertEquals(testItem.getId(), result.getId());
        assertEquals(testItem.getName(), result.getName());
        verify(cartItemRepository, times(1)).save(testItem);
    }

    @Test
    void getAllCartItems_ShouldReturnAllItems() {
        CartItem item2 = new CartItem(2L, "Item 2", 3, new BigDecimal("15.00"));
        List<CartItem> items = Arrays.asList(testItem, item2);
        when(cartItemRepository.findAll()).thenReturn(items);

        List<CartItem> result = cartService.getAllCartItems();

        assertEquals(2, result.size());
        verify(cartItemRepository, times(1)).findAll();
    }

    @Test
    void getCartItemById_WhenExists_ShouldReturnItem() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(testItem));

        Optional<CartItem> result = cartService.getCartItemById(1L);

        assertTrue(result.isPresent());
        assertEquals(testItem.getId(), result.get().getId());
        verify(cartItemRepository, times(1)).findById(1L);
    }

    @Test
    void getCartItemById_WhenNotExists_ShouldReturnEmpty() {
        when(cartItemRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<CartItem> result = cartService.getCartItemById(999L);

        assertFalse(result.isPresent());
        verify(cartItemRepository, times(1)).findById(999L);
    }

    @Test
    void updateCartItem_WhenExists_ShouldUpdateAndReturn() {
        CartItem updatedItem = new CartItem(null, "Updated Item", 5, new BigDecimal("20.00"));
        CartItem savedItem = new CartItem(1L, "Updated Item", 5, new BigDecimal("20.00"));

        when(cartItemRepository.existsById(1L)).thenReturn(true);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(savedItem);

        Optional<CartItem> result = cartService.updateCartItem(1L, updatedItem);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Updated Item", result.get().getName());
        verify(cartItemRepository, times(1)).existsById(1L);
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void updateCartItem_WhenNotExists_ShouldReturnEmpty() {
        CartItem updatedItem = new CartItem(null, "Updated Item", 5, new BigDecimal("20.00"));

        when(cartItemRepository.existsById(999L)).thenReturn(false);

        Optional<CartItem> result = cartService.updateCartItem(999L, updatedItem);

        assertFalse(result.isPresent());
        verify(cartItemRepository, times(1)).existsById(999L);
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void deleteCartItem_WhenExists_ShouldReturnTrue() {
        when(cartItemRepository.deleteById(1L)).thenReturn(true);

        boolean result = cartService.deleteCartItem(1L);

        assertTrue(result);
        verify(cartItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCartItem_WhenNotExists_ShouldReturnFalse() {
        when(cartItemRepository.deleteById(999L)).thenReturn(false);

        boolean result = cartService.deleteCartItem(999L);

        assertFalse(result);
        verify(cartItemRepository, times(1)).deleteById(999L);
    }
}
