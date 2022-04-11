package expense.track.application.service;

import expense.track.application.entity.Category;
import expense.track.application.repository.CategoryRepository;
import expense.track.application.request.CategoryRequest;
import expense.track.application.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public String saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();

        category.setCategoryId(CommonUtils.generateUUID());
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return "Category saved";
    }
}
