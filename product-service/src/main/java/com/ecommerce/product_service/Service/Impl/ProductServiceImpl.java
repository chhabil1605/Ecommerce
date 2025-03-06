package com.ecommerce.product_service.Service.Impl;

import com.ecommerce.product_service.Entity.*;
import com.ecommerce.product_service.Repository.CategoryRepository;
import com.ecommerce.product_service.Repository.ProductImageRepository;
import com.ecommerce.product_service.Repository.ProductRepository;
import com.ecommerce.product_service.Repository.SubCategoryRepository;
import com.ecommerce.product_service.Request.AddProduct;
import com.ecommerce.product_service.Response.ProductSavedResponse;
import com.ecommerce.product_service.Service.Interface.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public String addProduct(AddProduct addProduct, List<MultipartFile> files) throws IOException {
        if (addProduct==null){
            return "Product is Empty";
        }
        Optional<Category> category = categoryRepository.findByCategoryName(addProduct.getCategoryName());
        if (category.isEmpty())
        {
            Category category1 = new Category();
            category1.setCategoryName(addProduct.getCategoryName());
            categoryRepository.save(category1);
            logger.info("Category '{}' added successfully.", addProduct.getCategoryName());

            SubCategories subCategories = new SubCategories();
            subCategories.setSubCategoryName(addProduct.getSubCategoryName());
            subCategories.setCategory(category1);
            subCategoryRepository.save(subCategories);
            logger.info("Subcategory '{}' added under category '{}'.", addProduct.getSubCategoryName(), addProduct.getCategoryName());
        } else {
            logger.info("Category '{}' already exists.", addProduct.getCategoryName());
            Long id = category.get().getId();
            Optional<SubCategories> subCategories = subCategoryRepository.findBySubCategoryNameAndCategoryId(addProduct.getSubCategoryName(), id);
            if (subCategories.isEmpty()) {
                SubCategories subCategories1 = new SubCategories();
                subCategories1.setSubCategoryName(addProduct.getSubCategoryName());
                subCategories1.setCategory(category.get());
                subCategoryRepository.save(subCategories1);
                logger.info("Subcategory '{}' added under category '{}'.", addProduct.getSubCategoryName(), addProduct.getCategoryName());
            }

            Category category1 = category.get();
            SubCategories subCategories1 = subCategories.get();
            subCategories1.setCategory(category1);
            Product product = modelMapper.map(addProduct, Product.class);
            product.setSubCategories(subCategories1);

            InStock inStock = new InStock();
            inStock.setQuantity(addProduct.getQuantity());
            product.setInStock(inStock);

            List<ProductImage> productImages=new ArrayList<>();
            for (MultipartFile file : files) {
                byte[] imageData = file.getBytes();                ProductImage productImage = new ProductImage();
                productImage.setImageData(imageData);
                productImage.setProduct(product);
                productImages.add(productImage);
            }
            product.setProductImages(productImages);

            productRepository.save(product);
            logger.info("Product '{}' added successfully.", addProduct.getProductName());

        }
        return "Product added successfully";
    }

    @Override
    public List<ProductImage> getProductImages(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return productImageRepository.findByProduct(product.get());
        }
        return new ArrayList<>();
    }
}
