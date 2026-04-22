    package com.learningstage.demo.ProductService;

    import com.learningstage.demo.DAO.ProductDaoWrapper;
    import com.learningstage.demo.Dto.ProductDto;
    import com.learningstage.demo.Exceptions.BadRequestException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class Productservimp implements Productserviceinte {

        @Autowired
        private  ProductDaoWrapper proddao;
        public ProductDto saveProduct(ProductDto dto)
        {
            if(dto==null)
            {

                throw new BadRequestException("Product is empty");
            }
            if(dto.getPrice()<0)
            {
                throw  new BadRequestException("Product  price is should not be less than 0");
            }

            return proddao.saveProduct(dto);
        }
        public ProductDto updateProduct(Long id,ProductDto dto)
        {
            if(id==null ||dto==null)
            {
                throw new BadRequestException("Product is empty");
            }
            return proddao.updateProduct(id,dto);
        }
        public ProductDto patchProduct(Long id,ProductDto dto)
        {
            if (dto == null) {
                throw new BadRequestException("Product is empty");
            }

            if (dto.getPrice() != null && dto.getPrice() < 0) {
                throw new BadRequestException("price get cannot be negative");
            }
            return  proddao.patchProduct(id,dto);
        }

        public List<ProductDto> getAllProducts()
        {
           List<ProductDto> products=proddao.getAllProducts();
            if (products.isEmpty()) {
                throw new BadRequestException("Product is empty");
            }

            return products;
        }
        public ProductDto getProductById(Long id)
        {
            if (id == null || id <= 0) {
                throw new BadRequestException("Invalid Product Id");
            }

            // ✅ Call DAO
            ProductDto product = proddao.getProductById(id);

            return product;
        }
        public void deleteById(Long id){
            if(id==null)
            {
                throw  new BadRequestException("Invalid Product Id");
            }
            proddao.deleteById(id);
        }
    }
