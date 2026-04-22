package com.learningstage.demo.DAO;

import com.learningstage.demo.Dto.ProductDto;
import com.learningstage.demo.Entity.ProductEntity;
import com.learningstage.demo.Exceptions.ProductNotFoundException;
import com.learningstage.demo.Mapper.ProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import  java.util.List;

import java.util.Optional;

@Component
public class ProductDaoWrapper {

    @Autowired
    private ProductRepo repo;
  @Autowired
    private ProductMapper mapper;


    public ProductDto saveProduct(ProductDto bean)
    {
        ProductEntity entity=convertDtoToEntity(bean);
        ProductEntity saved=repo.save(entity);
        return  convertEntityToDto(saved);



//        ProductEntity ent=new ProductEntity();
//        ent.setId(bean.getId());  //we should not set id
//        ent.setName(bean.getName());
//        ent.setPrice(bean.getPrice());
//        ProductEntity save=repo.save(ent);
//        ProductDto dto=new ProductDto();
//        dto.setId(save.getId());
//        dto.setName(save.getName());
//        dto.setPrice(save.getPrice());
//        return dto;

//        ProductEntity ent=mapper.toEntity(bean);
//        ProductEntity save=repo.save(ent);
//        return mapper.toDto(save);

    }



public ProductDto updateProduct(Long id,ProductDto dto)
{
//
        ProductEntity exist=repo.findById(id).orElseThrow(()->
                new ProductNotFoundException("product not found with id :"+id)); //it return productentity object
//    Optional<ProductEntity>opt=repo.findById(id);
//    ProductEntity exist=opt.get();  //👉 If product is NOT found → 💥 crash Exception: NoSuchElementException

//    Optional<ProductEntity> opt = repo.findById(id);
//if (opt.isPresent()) {
//    ProductEntity exist = opt.get();
//} else {
//    throw new RuntimeException("Product not found");


//             or

//        exist.setName(dto.getName());
//        exist.setPrice(dto.getPrice());
//        ProductEntity savedentity=repo.save(exist);
//        ProductDto dtobean=new ProductDto();
//        dtobean.setId(savedentity.getId());
//        dtobean.setName(savedentity.getName());
//        dtobean.setPrice(savedentity.getPrice());
//        return dtobean;




    BeanUtils.copyProperties(dto,exist,"id");
    ProductEntity updaent=repo.save(exist);
    return convertEntityToDto(updaent);

//
//    DTO:
//    {
//        "id": 999,
//            "name": "Mobile",
//            "price": 20000
//    }
//    Existing DB:
//    {
//        "id": 1,
//            "name": "Old Mobile",
//            "price": 15000
//    }
//
//👉 After copy:
//
//    {
//        "id": 999  ❌ WRONG (you changed primary key!)
//    }
////


}

    public ProductDto patchProduct(Long id, ProductDto dto) {

        ProductEntity exist = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("product not found with id :"+id));

        // 🔥 Update ONLY if value is not null
        if (dto.getName() != null) {
            exist.setName(dto.getName());
        }

//        🟢 PATCH (Partial Update)
//        Request:
//        {
//            "price": 60000
//        }
//
//👉 Notice:
//
//        name is NOT sent
//⚡ What happens inside your code?
//        if (dto.getName() != null) {
//            exist.setName(dto.getName());
//        }
//
//👉 dto.getName() → null (because not sent)
//
//✔ Condition fails
//✔ So name is NOT updated


        if (dto.getPrice() != null) {
            exist.setPrice(dto.getPrice());
        }

        ProductEntity updated = repo.save(exist);

        // 🔥 Manual conversion
        ProductDto response = new ProductDto();
        response.setId(updated.getId());
        response.setName(updated.getName());
        response.setPrice(updated.getPrice());

        return response;
    }

    public List<ProductDto>  getAllProducts()
    {
        List<ProductEntity> entities=repo.findAll();
        List<ProductDto> dtos = new ArrayList<>();

        for (ProductEntity entity : entities) {

            dtos.add(convertEntityToDto(entity));
        }

        return dtos;
    }

   public ProductDto getProductById(Long id)
   {
       ProductEntity entity=repo.findById(id).orElseThrow(()->new ProductNotFoundException("product not found with id :"+id));

       return convertEntityToDto(entity);

   }

   public void deleteById(Long id)
   {
       ProductEntity entity=repo.findById(id).orElseThrow(()->new ProductNotFoundException("product not found with id :"+id));
       repo.deleteById(id);


   }


    public ProductDto convertEntityToDto(ProductEntity ent)
    {
        ProductDto dto=new ProductDto();
        BeanUtils.copyProperties(ent,dto);
        return dto;
    }
    public ProductEntity convertDtoToEntity(ProductDto dto)
    {
        ProductEntity ent=new ProductEntity();
        BeanUtils.copyProperties(dto,ent);
        return ent;
    }


}
