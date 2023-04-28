package com.logical.auth.controller;

import com.logical.auth.entity.CategoryData;
import com.logical.auth.model.response.CreateCategoryResponse;
import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.services.StorageServices;
import com.logical.auth.services.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    StorageServices storageServices;

   private Logger logger=LoggerFactory.getLogger(CategoryController.class);
    @PostMapping("/addCategory")
    public ResponseEntity<?>createCategory(@RequestParam(name="file",required = true) MultipartFile multipartFile, @RequestParam(name="categoryName",required = true) String categoryName,@RequestParam(name="colourCode",required = true)String colourCode) throws IOException {
            try {
                  return ResponseEntity.ok(new CreateCategoryResponse(true,categoryService.createCategory(multipartFile,categoryName,colourCode)));
            }
            catch (Exception e) {
                logger.info(" "+e);
                return new ResponseEntity<>(new MessageResponse(false,"Something went wrong..."), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    @GetMapping("/getListCategory")
    public ResponseEntity<?>getListCategory(){
        try {
            return categoryService.getListCategory();
        } catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateCategory")
    public ResponseEntity<?>updateCategory(@RequestParam(name="categoryId",required = true,defaultValue = "0")int categoryId,@RequestParam(name="file",required = true) MultipartFile multipartFile, @RequestParam(name="categoryName",required = true) String categoryName,@RequestParam(name="colourCode",required = true)String colourCode) throws IOException {

        try {
            return categoryService.updateCategory(categoryId,multipartFile,categoryName,colourCode);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategoryById")
    CategoryData getCategoryById(@Valid @RequestParam(name="categoryId",required = true,defaultValue = "0")int categoryId){
  //      try {
            return categoryService.getCategoryById(categoryId);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new MessageResponse("Something went wrong...!", false), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @DeleteMapping("/deleteCategory")
    ResponseEntity<?>deleteCategory(@Valid @RequestParam(name="categoryId",required = true,defaultValue = "0")int categoryId){
        try {
            return categoryService.deleteCategory(categoryId);
        } catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/searchCategoryByKeyword")
    public ResponseEntity<?> searchCategoryByKeyword(@RequestParam("keyword") String keyword) {
        return categoryService.searchByKeyword(keyword);
    }

}