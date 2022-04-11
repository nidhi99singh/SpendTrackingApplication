package expense.track.application.repository;

import expense.track.application.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    @Query("select quantity from Products where productId=:productId")
    public Integer getExistingQuantity(@Param("productId") String productId);

    @Query("select price from Products where productId=:productId")
    public Integer getCurrentPrice(@Param("productId") String productId);

    @Modifying
    @Transactional
    @Query("update Products set quantity=:quantity where productId=:productId")
    public void updateProductQuantity(@Param("quantity") Integer quantity, @Param("productId") String productId);

    @Query("from Products where productId=:productId")
    public Products setProduct(@Param("productId") String productId);

    Optional<Products> findByProductName(String productName);

    @Query(value = "select * from products where category_id=:categoryId limit 5", nativeQuery = true)
    List<Products> findByCategory(@Param("categoryId") String categoryId);
}
