package com.ecommerce.product_service.Controller;

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

}
