package com.test.artere.service;

import com.test.artere.model.Cart;
import com.test.artere.model.CartItem;
import com.test.artere.model.Product;
import com.test.artere.repository.CartItemRepository;
import com.test.artere.repository.CartRepository;
import com.test.artere.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;


    private final ProductRepository productRepository;


    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart addCart() {
        return this.cartRepository.save(new Cart());
    }

    public List<Cart> getCarts() {
        return this.cartRepository.findAll();
    }

    public Cart addProduct(Long cartId, Long productId, int quantity) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity){
            throw new RuntimeException("quantity is greater than StockQuantity");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);

        cart.getCartItems().add(cartItem);
        return this.cartRepository.save(cart);
    }

    public Cart updateQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        if (cartItem.getProduct().getStockQuantity() < quantity){
            throw new RuntimeException("quantity is greater than StockQuantity");
        }
        cartItem.setQuantity(quantity);
        return this.cartRepository.save(cartItem.getCart());
    }

    public Cart removeProduct(Long cartItemId) {
        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        this.cartItemRepository.delete(cartItem);
        return this.cartRepository.save(cart);
    }

    public double calculateTotalAmount(Long cartId) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

}


