package com.ecommerce.order_service.Service.impl;

import com.ecommerce.order_service.Entity.Cart;
import com.ecommerce.order_service.Repository.CartRepository;
import com.ecommerce.order_service.Service.interfaces.CartService;
import com.ecommerce.order_service.ipc.Product;
import com.ecommerce.order_service.ipc.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartRepository cartRepository;


    public String addToCart(Long productId,Long quantity, Long userId) {
        if(!productClient.isProductExists(productId))
            throw new RuntimeException("Product does not exist");
        Product product = productClient.getProductById(productId);
        product.setQuantity(quantity);
        if(cartRepository.existsByUserId(userId))
        {
            Cart cart = cartRepository.findByUserId(userId);
            cart.getProducts().add(product);
            cartRepository.save(cart);
        }
        else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.getProducts().add(product);
            cartRepository.save(cart);
        }
        return "Product added to cart";
    }

    @Override
    public String removeFromCart(Long productId, Long userId) {
        if(!productClient.isProductExists(productId))
            throw new RuntimeException("Product does not exist");
        Product product = productClient.getProductById(productId);
        if(cartRepository.existsByUserId(userId))
        {
            Cart cart = cartRepository.findByUserId(userId);
            cart.getProducts().remove(product);
            cartRepository.save(cart);
        }
        return "Removed Successfully";
    }

    @Override
    public List<Product> getCart(Long userId) {
        if(!cartRepository.existsByUserId(userId))
            throw new RuntimeException("Cart does not exist");
        Cart cart = cartRepository.findByUserId(userId);
        return cart.getProducts();
    }

    @Override
    public String reduceQuantityByOne(Long productId, Long userId) {
        if(!productClient.isProductExists(productId))
            throw new RuntimeException("Product does not exist");
        if(!cartRepository.existsByUserId(userId))
            throw new RuntimeException("User does not exist in cart");
        Cart cart = cartRepository.findByUserId(userId);
        List<Product> products= cart.getProducts();
        Product product=products.stream()
                .filter(p->p.getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Product does not exist in cart"));
        if(product.getQuantity()>1)
            product.setQuantity(product.getQuantity()-1);
        else {
            products.remove(product);
        }
        cartRepository.save(cart);
        return "Product Quantity Reduced By One";
    }

    @Override
    public Long productRemaining(Long productId) {
        if(!productClient.isProductExists(productId))
            throw new RuntimeException("Product does not exist");
        return productClient.productRemaining(productId);
    }

}
