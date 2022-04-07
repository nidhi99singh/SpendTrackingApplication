package expense.track.application.service;
import expense.track.application.entity.Products;
import expense.track.application.repository.CategoryRepository;
import expense.track.application.repository.ProductsRepository;
import expense.track.application.request.ProductUpdateRequest;
import expense.track.application.request.ProductsRequest;
import expense.track.application.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import expense.track.application.exception.ValidationException;

import java.util.List;
import java.util.Objects;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String newProduct(ProductsRequest productsRequest) throws ValidationException {
        Products product = new Products();
        product.setProductId(CommonUtils.generateUUID());
        product.setProductName(productsRequest.getProductName());
        product.setPrice(productsRequest.getPrice());
        product.setQuantity(productsRequest.getQuantity());

        Products existingProduct=productsRepository.findByProductName(productsRequest.getProductName()).orElse(null);
        if(Objects.isNull(existingProduct)) {
            product.setCategory(categoryRepository.getCategory(productsRequest.getCategoryName()));
            productsRepository.save(product);
            return "Product Added";
        }
        else
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(),"product with this Name already exist");

    }

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }


    public String updateProducts(ProductUpdateRequest productUpdateRequest) throws ValidationException {
        Products products = productsRepository.findByProductName(productUpdateRequest.getProductName()).orElse(null);

        if (Objects.isNull(products))
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Product does not exist");

        products.setQuantity(productUpdateRequest.getQuantity());
        products.setPrice(productUpdateRequest.getPrice());
        productsRepository.save(products);
        return "Product Updated successfully";

    }

    public String deleteProductById(String id) throws ValidationException {
       Products product=productsRepository.findById(id).orElse(null);
       if(Objects.isNull(product))
           throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Product does not exist");
        productsRepository.deleteById(id);
        return id + ": Deleted from list";
    }


    public Products getProductById(String id) throws ValidationException {
        Products product = productsRepository.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Product does not exist");
        }
        return product;
    }

}
