package com.learningstage.demo.ProductController;

import com.learningstage.demo.Dto.ProductDto;
import com.learningstage.demo.Exceptions.ApiResponse;
import com.learningstage.demo.ProductService.Productserviceinte;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//validation cheyali next vachi testing cheyaliand inkokato @exceptionhandler ela work avtundi adhi
// frontend ki ela pass


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class Productcontroller {
    private final Productserviceinte serv;

//     @RequestMapping(value = "/save",method = RequestMethod.POST)
    @PostMapping("/save")
     public ResponseEntity<ApiResponse<ProductDto>> saveProduct( @Valid @RequestBody ProductDto dto)
     {

         return new ResponseEntity<>(new ApiResponse<>(true,serv.saveProduct(dto),"Product created"),HttpStatus.CREATED);
     }
     @GetMapping("/")
     public String show()
     {
         return "message to browser";
     }
     @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct( @PathVariable Long id ,@Valid @RequestBody ProductDto dto)
     {
         return new ResponseEntity<>(new ApiResponse<>(true, serv.updateProduct(id,dto)
                 ,"Product Updated"),HttpStatus.OK);
     }
     @PatchMapping("/{id}")
    public  ResponseEntity<ApiResponse<ProductDto>> patchProduct(@PathVariable Long id,@Valid @RequestBody ProductDto dto)
     {
         return  new ResponseEntity<>(new ApiResponse<>(true,serv.patchProduct(id,dto),"Product update"),HttpStatus.OK);
     }
    @GetMapping("/get")

    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts()
    {
        return new ResponseEntity<>(new ApiResponse<>(true,serv.getAllProducts(),"All Products Fetched"),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponse<>(true,serv.getProductById(id),"Product Fetched Succesfully"),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable Long id) {

        serv.deleteById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Product deleted successfully")
        );
    }

}
