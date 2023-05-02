package com.logical.auth.controller;

import com.logical.auth.entity.CategoryData;
import com.logical.auth.entity.SubCategory;
import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.services.impl.SubCategoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {

    @Autowired
    SubCategoryImp subCategoryImp;

    @PostMapping("/createSubCategory")
    public ResponseEntity<?> createSubCategory(@RequestParam("subCategoryName") String subCategoryName, @RequestParam("categoryId") int categoryId, @RequestParam("file") MultipartFile multipartFile) throws IOException {

        if (categoryId > 0) {
            return subCategoryImp.createSubCategory(subCategoryName, categoryId, multipartFile);
//            return new ResponseEntity<>(new MessageResponse(true, "Successfully registered..."), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid category id...."), HttpStatus.OK);
        }
    }

    @GetMapping("/showSubCategory")
    public ResponseEntity showSubCategory() {
        return subCategoryImp.showSubCategorywithPage();

    }

    @GetMapping("/deleteSubCategory")
    public ResponseEntity deleteSubCategory(@RequestParam("subCategoryId") int subid) {
        if (subid > 0) {
            return subCategoryImp.deleteSubCategoryById(subid);
        } else {
            return new ResponseEntity(new MessageResponse(false, "Provide valid Id!!"), HttpStatus.OK);
        }
    }

    @GetMapping("/getSubCategoryByCategoryId")
    public ResponseEntity findSubCategoryByCategoryId(@RequestParam(name = "categoryId", required = true) int categoryId) {
        if (categoryId > 0) {
            return subCategoryImp.getSubCategoryByCategoryId(categoryId);
        } else {
            return new ResponseEntity(new MessageResponse(false, "Provide Valid Category Id!!"), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/updateSubCategory")
    public ResponseEntity updateSubCategory(@RequestParam(name = "subCategoryId") int subCategoryId, @RequestParam(name = "categoryId") int categoryId, @RequestParam(name = "subCategoryName") String subCategoryName, @RequestParam(name = "file") MultipartFile file) {
        if (subCategoryId > 0) {
            return subCategoryImp.updateSubCategory(subCategoryId, categoryId, subCategoryName, file);
        } else {
            return new ResponseEntity(new MessageResponse(false, "Provide valid Id!!"), HttpStatus.OK);
        }
    }

    @GetMapping("/testdelete")
    public String deleteTest(@RequestParam(name = "delete") String url) {
        return subCategoryImp.deletefiletest(url);
    }


}
