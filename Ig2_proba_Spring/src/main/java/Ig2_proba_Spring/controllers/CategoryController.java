package Ig2_proba_Spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Ig2_proba_Spring.dto.category.CategoryCreateDTO;
import Ig2_proba_Spring.entities.CategoryEntity;
import Ig2_proba_Spring.repositories.CategoryRepository;

import java.util.List;
    @RestController
    @AllArgsConstructor
    @RequestMapping("api/categories")
    public class CategoryController {
        private  final CategoryRepository categoryRepository;
      //  private  final CategoryMapper categoryMapper;
      //  private  final StorageService storageService;

        @GetMapping
        public ResponseEntity<List<CategoryEntity>> index(){
        //public ResponseEntity<List<CategoryItemDTO>> index(){
            var list = categoryRepository.findAll();
            //var model = categoryMapper.CategoryItemByCategory(list);
            //return new ResponseEntity<>(model, HttpStatus.OK);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }

        @PostMapping
        //@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<CategoryEntity> create (@RequestBody CategoryCreateDTO model){
        //public ResponseEntity<CategoryItemDTO> create (@ModelAttribute CategoryCreateDTO model){
            //var fileName=storageService.saveMultipartFile((model.getFile())); //storageService.save(model.getBase64());
            CategoryEntity category= new CategoryEntity();
            //CategoryEntity category= categoryMapper.CategoryByCreateDTO(model);
            category.setName(model.getName());
            //category.setImage(fileName);
            categoryRepository.save(category);
            //var result= categoryMapper.CategoryItemByCategory(category);
            return new ResponseEntity<>(category,HttpStatus.CREATED);
            //return new ResponseEntity<>(result,HttpStatus.CREATED);
        }
    }

