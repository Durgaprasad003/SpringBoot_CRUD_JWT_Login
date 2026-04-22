package com.learningstage.demo.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull(message = "not null")
    @NotEmpty(message = "not empty ")
    @NotBlank(message = "Name should not be empty")
    private String name;
    @NotNull(message = "Price should not be null")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;
}
