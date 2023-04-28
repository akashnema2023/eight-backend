package com.logical.auth.services.impl;

import com.logical.auth.entity.CategoryData;
import com.logical.auth.model.response.ListCategoryResponse;
import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.model.response.UpdateCategoryResponse;
import com.logical.auth.repository.CategoryRepo;
import com.logical.auth.services.FileService;

import com.logical.auth.services.StorageServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
//    @Autowired(required = true)
//FileService fileService;

    @Autowired
    StorageServices fileService;

        @Value("${project.image}")
    String path;
    public CategoryData createCategory(MultipartFile multipartFile, String categoryName, String colourCode) throws IOException {
        CategoryData categoryData = new CategoryData();
        categoryData.setCategoryName(categoryName);
//        categoryData.setCategoryImageUrl(this.fileService.uploadFile(path,multipartFile));
        if (multipartFile.isEmpty()) {
            categoryData.setCategoryImageUrl("https://seven02.s3.ap-south-1.amazonaws.com/defaultCategoryImage.png");
        } else {
            categoryData.setCategoryImageUrl(this.fileService.uploadFile(path,multipartFile));
        }
        categoryData.setColourCode(colourCode);
        categoryData = categoryRepo.save(categoryData);
        return categoryData;
    }

    public ResponseEntity<?> getListCategory() {
        List<CategoryData> listCategory = categoryRepo.findAll();
        if (listCategory.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse(false, "Category does not exist with this  !"), HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(new ListCategoryResponse(true, listCategory));
        }
    }

    //    @Override
    public CategoryData getCategoryById(int categoryId) {
        if (categoryId > 0) {
            if (categoryRepo.existsById(categoryId)) {
                CategoryData categoryData = this.categoryRepo.findById(categoryId).get();
                return categoryData;
            } else {
                return null;
            }
            //  if (categoryRepo.existsById(categoryId)) {
            //   CategoryData categoryData = categoryRepo.findById(categoryId).get();
            // return ResponseEntity.ok(new CreateCategoryResponse(true,categoryData));
            // } else {
            // return new ResponseEntity<>(new MessageResponse("Category does not exist with this categoryId !", false), HttpStatus.NOT_FOUND);

            //  }
//        } else {
//            return new ResponseEntity<>(new MessageResponse("Please provide valid categoryId !", false), HttpStatus.NOT_ACCEPTABLE);
        } else {
            return null;
        }
    }

    //    @Override
    public ResponseEntity<?> deleteCategory(int categoryId) {
        if (categoryId > 0) {
            if (categoryRepo.existsById(categoryId)) {
                categoryRepo.deleteById(categoryId);
                return new ResponseEntity<>(new MessageResponse(true, "Category deleted successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Category does not exist with this categoryId !"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid categoryId !"), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //    @Override
    public ResponseEntity<?> updateCategory(int categoryId, MultipartFile multipartFile, String categoryName, String colourCode) throws IOException {
        CategoryData olddata=new CategoryData();
        if (categoryId > 0) {
            if (categoryRepo.existsById(categoryId)) {
                CategoryData categoryData = categoryRepo.findById(categoryId).get();
                categoryData.setCategoryName(categoryName);
//                categoryData.setCategoryImageUrl(this.fileService.uploadFile(path, multipartFile));
                if (!multipartFile.isEmpty()) {
                    categoryData.setCategoryImageUrl(this.fileService.uploadFile(path,multipartFile));
                }else{
                    olddata.setCategoryImageUrl(categoryData.getCategoryImageUrl());
                }

                categoryData.setColourCode(colourCode);
                categoryRepo.save(categoryData);
                UpdateCategoryResponse updateCategoryResponse = new UpdateCategoryResponse();
                updateCategoryResponse.setCategoryName(categoryData.getCategoryName());
                updateCategoryResponse.setCategoryImage(categoryData.categoryImageUrl);
                updateCategoryResponse.setColourCode(categoryData.getColourCode());
                return ResponseEntity.ok(updateCategoryResponse);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Category does not exist with this categoryId !"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid categoryId !"), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<?> searchByKeyword(String keyword) {
        List<CategoryData> searchcat = categoryRepo.searchByCategory(keyword);
        if (searchcat == null) {
            return new ResponseEntity<>(new MessageResponse(false, "not found!..."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(searchcat, HttpStatus.OK);
    }

    List<CategoryData> findAllBycategoryId(int categoryId) {
        List<CategoryData> categoryDatalist = new ArrayList<>();
        categoryDatalist = categoryRepo.findAllByCategoryId(categoryId);
        return categoryDatalist;

    }

}