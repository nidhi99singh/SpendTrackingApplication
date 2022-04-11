package expense.track.application.repository;

import expense.track.application.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("from Category where categoryName=:categoryName")
    public Category getCategory(@Param("categoryName") String categoryName);


    @Query("select categoryId from Category where categoryName=:categoryName")
    String findByCategoryName(@Param("categoryName") String categoryName);
}



