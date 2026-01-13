package com.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.model.CartItem;
import com.starter.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for CartController.
 */
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @Test
    void getAllCartItems_ShouldReturnAllItems() throws Exception {
        CartItem item1 = new CartItem(1L, "Apple", 2, new BigDecimal("1.50"));
        CartItem item2 = new CartItem(2L, "Banana", 5, new BigDecimal("0.75"));
        List<CartItem> items = Arrays.asList(item1, item2);

        when(cartService.getAllCartItems()).thenReturn(items);

        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[0].price").value(1.50))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Banana"));
    }

    @Test
    void getCartItemById_WhenExists_ShouldReturnItem() throws Exception {
        CartItem item = new CartItem(1L, "Apple", 2, new BigDecimal("1.50"));

        when(cartService.getCartItemById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/v1/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(1.50));
    }

    @Test
    void getCartItemById_WhenNotExists_ShouldReturn404() throws Exception {
        when(cartService.getCartItemById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/cart/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCartItem_WithValidData_ShouldReturnCreated() throws Exception {
        CartItem item = new CartItem(null, "Apple", 2, new BigDecimal("1.50"));
        CartItem savedItem = new CartItem(1L, "Apple", 2, new BigDecimal("1.50"));

        when(cartService.createCartItem(any(CartItem.class))).thenReturn(savedItem);

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(1.50));
    }

    @Test
    void createCartItem_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        CartItem item = new CartItem(null, "", 0, new BigDecimal("-1"));

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCartItem_WithMissingName_ShouldReturnBadRequest() throws Exception {
        CartItem item = new CartItem(null, null, 2, new BigDecimal("1.50"));

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCartItem_WhenExists_ShouldReturnUpdated() throws Exception {
        CartItem updatedItem = new CartItem(1L, "Updated Apple", 3, new BigDecimal("2.00"));

        when(cartService.updateCartItem(eq(1L), any(CartItem.class)))
                .thenReturn(Optional.of(updatedItem));

        mockMvc.perform(put("/api/v1/cart/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Apple"))
                .andExpect(jsonPath("$.quantity").value(3))
                .andExpect(jsonPath("$.price").value(2.00));
    }

    @Test
    void updateCartItem_WhenNotExists_ShouldReturn404() throws Exception {
        CartItem item = new CartItem(null, "Apple", 2, new BigDecimal("1.50"));

        when(cartService.updateCartItem(eq(999L), any(CartItem.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/cart/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCartItem_WhenExists_ShouldReturn204() throws Exception {
        when(cartService.deleteCartItem(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/cart/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCartItem_WhenNotExists_ShouldReturn404() throws Exception {
        when(cartService.deleteCartItem(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/cart/999"))
                .andExpect(status().isNotFound());
    }
}
