package com.ecommerce.product_service.Controller;

import com.ecommerce.product_service.Entity.Product;
import com.ecommerce.product_service.Entity.ProductImage;
import com.ecommerce.product_service.Request.AddProduct;
import com.ecommerce.product_service.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody AddProduct addProduct, @RequestParam("files") List<MultipartFile> files) throws IOException {
        productService.addProduct(addProduct, files);
        return "Product added successfully";
    }

    @GetMapping("/{productId}/product-images")
    public ResponseEntity<List<ProductImage>> getProductImages(@PathVariable Long productId) {
        List<ProductImage> productImages = productService.getProductImages(productId);
        return ResponseEntity.ok(productImages);
    }


    @GetMapping("/isProductExist/{productId}")
    public boolean isProductExist(@PathVariable Long productId) {
        return productService.isProductExist(productId);
    }

    @GetMapping("/getFavouriteProducts") // Change the mapping to not include productId
    public List<Product> getFavouriteProducts(@RequestParam List<Long> productIds) {
        // Use @RequestParam to accept a list
        return productService.getProducts(productIds);
    }


    @GetMapping("/getProductById/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProducts(List.of(productId)).get(0);
    }

    @PostMapping("/productRemaining/{productId}")
    public Long productRemaining(@PathVariable Long productId) {
        return productService.productRemaining(productId);
    }
}
