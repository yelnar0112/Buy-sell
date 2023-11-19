package com.yelnar.ebook.services;

import com.yelnar.ebook.exceptions.ApiRequestException;
import com.yelnar.ebook.models.Image;
import com.yelnar.ebook.models.Product;
import com.yelnar.ebook.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository products;

    @Autowired
    public ProductService(ProductRepository products) {
        this.products = products;
    }



    public List<Product> productTitle(String title){
        if(title!=null)
            return products.findByTitle(title);
        return products.findAll();
    }

    public Product showProduct(Long id){
        Optional<Product> byId = products.findById(id);

        if(!byId.isPresent()){
            throw new ApiRequestException("No Such ID");
        }

        return byId.orElse(null);
    }

    public void saveProduct(Product product, MultipartFile file1,MultipartFile file2,MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }

        if(file2.getSize() != 0){
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }

        if(file3.getSize() != 0){
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }

        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(),product.getAuthor());
        Product productFromDB = products.save(product);

        if(!productFromDB.getImages().isEmpty()){
            productFromDB.setPreviewImageId(productFromDB.getImages().get(0).getId());
        }

        products.save(product);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        products.deleteById(id);
    }
}