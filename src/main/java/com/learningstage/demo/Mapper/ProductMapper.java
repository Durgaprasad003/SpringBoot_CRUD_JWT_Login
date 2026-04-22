package com.learningstage.demo.Mapper;
import com.learningstage.demo.Dto.ProductDto;
import com.learningstage.demo.Entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(ProductEntity ent);
    ProductEntity toEntity(ProductDto dto);

}
