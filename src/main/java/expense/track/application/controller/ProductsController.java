package expense.track.application.controller;

import expense.track.application.entity.Products;
import expense.track.application.enums.ApiResponseCode;
import expense.track.application.exception.ValidationException;
import expense.track.application.request.ProductUpdateRequest;
import expense.track.application.request.ProductsRequest;
import expense.track.application.response.generics.ResponseDTO;
import expense.track.application.response.utils.ResponseUtil;
import expense.track.application.service.ProductsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @Autowired
    ResponseUtil responseUtil;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createproduct")
    public String newProduct(@RequestBody ProductsRequest productsRequest) throws ValidationException {
        return productsService.newProduct(productsRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping("/getProducts")
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get by id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/getProductById/{id}")
    public ResponseDTO<?> getProductById(@PathVariable String id) throws Exception {
        return responseUtil.ok(productsService.getProductById(id), ApiResponseCode.SUCCESS);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseDTO<?> updateProducts(@RequestBody ProductUpdateRequest productUpdateRequest) throws ValidationException {
        return responseUtil.ok(productsService.updateProducts(productUpdateRequest), ApiResponseCode.SUCCESS);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseDTO<?> deleteProductById(@PathVariable String id) throws ValidationException {
        //return productsService.deleteProductById(id);
        return responseUtil.ok(productsService.deleteProductById(id), ApiResponseCode.SUCCESS);
    }

}
