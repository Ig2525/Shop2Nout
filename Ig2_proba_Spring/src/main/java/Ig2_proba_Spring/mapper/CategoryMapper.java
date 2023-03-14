package Ig2_proba_Spring.mapper;

import Ig2_proba_Spring.dto.category.CategoryCreateDTO;
import Ig2_proba_Spring.dto.category.CategoryItemDTO;
import Ig2_proba_Spring.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "image", ignore = true)
    CategoryEntity CategoryByCreateDTO(CategoryCreateDTO dto);
    CategoryItemDTO CategoryItemByCategory(CategoryEntity entity);
    List<CategoryItemDTO> CategoryItemsByCategories(List<CategoryEntity> entities);

}
