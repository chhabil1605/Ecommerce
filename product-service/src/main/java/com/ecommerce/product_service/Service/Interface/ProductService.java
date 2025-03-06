package com.ecommerce.product_service.Service.Interface;

import com.ecommerce.product_service.Entity.ProductImage;
import com.ecommerce.product_service.Request.AddProduct;
import com.ecommerce.product_service.Response.ProductSavedResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    String addProduct(AddProduct addProduct, List<MultipartFile> files) throws IOException;

    List<ProductImage> getProductImages(Long productId);
}
