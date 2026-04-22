package com.learningstage.demo.ProductService;

import com.learningstage.demo.Dto.ProductDto;

import java.util.List;

public interface Productserviceinte {
    public ProductDto saveProduct(ProductDto dto);
    public ProductDto updateProduct(Long id,ProductDto dto);
    public ProductDto patchProduct(Long id,ProductDto dto);
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(Long id);
    public void deleteById(Long id);

}
