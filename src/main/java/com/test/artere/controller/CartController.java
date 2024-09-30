package com.test.artere.controller;

import com.test.artere.model.Cart;
import com.test.artere.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getCarts(){
        return this.cartService.getCarts();
    }

    @PostMapping
    public Cart addCart(){
        return this.cartService.addCart();
    }

    @PostMapping("/{cartId}/products/{productId}")
    public Cart addProduct(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        return cartService.addProduct(cartId, productId, quantity);
    }

    @PutMapping("/items/{cartItemId}")
    public Cart updateQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        return cartService.updateQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/items/{cartItemId}")
    public Cart removeProduct(@PathVariable Long cartItemId) {
        return cartService.removeProduct(cartItemId);
    }

    @GetMapping("/{cartId}/total")
    public double calculateTotalAmount(@PathVariable Long cartId) {
        return cartService.calculateTotalAmount(cartId);
    }
}