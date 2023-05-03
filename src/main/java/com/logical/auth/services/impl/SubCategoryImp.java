package com.logical.auth.services.impl;

import com.logical.auth.entity.CategoryData;
import com.logical.auth.entity.SubCategory;
import com.logical.auth.entity.VideoData;
import com.logical.auth.model.response.MessageResponse;
//import com.logical.auth.model.response.SubCategoryResponse;
import com.logical.auth.model.response.SubCategoryResponse;
import com.logical.auth.repository.CategoryRepo;
import com.logical.auth.repository.SubCategoryRepo;
import com.logical.auth.repository.VideoRepository;
import com.logical.auth.services.StorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SubCategoryImp {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    StorageServices storageServices;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoService videoService;
    @Autowired
    SubCategoryRepo subCategoryRepo;

    @Value("${project.image}")
    String path;

    public ResponseEntity<?> createSubCategory(String subCategoryName, int categoryId, MultipartFile file) throws IOException {
        if (categoryId > 0) {
            if (categoryRepo.existsById(categoryId)) {
                SubCategory subcate = new SubCategory();
                subcate.setCategoryId(categoryId);
                subcate.setSubCategoryName(subCategoryName);
                if (file.isEmpty()) {
//                    subcate.setSubCategoryImgUrl("https://seven02.s3.ap-south-1.amazonaws.com/defaultCategoryImage.png");
                } else {
                    subcate.setSubCategoryImgUrl(this.storageServices.uploadFile(path, file));
                }
                subCategoryRepo.save(subcate);
                return new ResponseEntity<>(new MessageResponse(true, "Successfully added....."), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "This type of category doesn't exists provide Valid Id!!"), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Provide Valid Id!!"), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> showSubCategorywithPage() {
        List<SubCategory> all = subCategoryRepo.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public ResponseEntity deleteSubCategoryById(int subId) {
        if (subCategoryRepo.existsById(subId)) {
            SubCategory subCategory = subCategoryRepo.findById(subId).get();
            String subCategoryImgUrl = subCategory.getSubCategoryImgUrl();
            if (!subCategoryImgUrl.isEmpty()) {
                storageServices.deleteFile(subCategoryImgUrl);
            }
            List<VideoData> bySubCategoryId = videoRepository.findBySubCategoryId(subId);
            if (!bySubCategoryId.isEmpty()) {
                for (VideoData videoData : bySubCategoryId) {
                    int videoId = videoData.getVideoId();
//                String videoUrl = videoData.getVideoUrl();
//                String thumbNailUrl = videoData.getThumbNailUrl();
//                if (!videoUrl.isEmpty()) {
//                    storageServices.deleteFile(videoUrl);
//                }
//                if (!thumbNailUrl.isEmpty()) {
//                    storageServices.deleteFile(thumbNailUrl);
//                }
//                videoRepository.deleteById(videoId);
                    videoService.deleteVideo(videoId);
                }
            }

            subCategoryRepo.deleteById(subId);
            return new ResponseEntity(new MessageResponse(true, "Delete Successfully.."), HttpStatus.OK);
        } else {
            return new ResponseEntity(new MessageResponse(false, "This id doesn't exist!!"), HttpStatus.OK);
        }
    }

    public String deletefiletest(String url) {
        return storageServices.deleteFile(url);
    }

    public ResponseEntity<?> getSubCategoryByCategoryId(int categoryId) {
        List<SubCategory> byCategoryId = subCategoryRepo.findByCategoryId(categoryId);
        if (!byCategoryId.isEmpty()) {
            return new ResponseEntity<>(new SubCategoryResponse(true, "Successfully done...", byCategoryId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "No such type of sub category here!!!"), HttpStatus.OK);
        }

//        return new ResponseEntity<>(byCategoryId,HttpStatus.OK);
    }

    public ResponseEntity updateSubCategory(int subCategoryId, int categoryId, String subCategoryName, MultipartFile file) {
        if (subCategoryRepo.existsById(subCategoryId)) {
            SubCategory subCategory = subCategoryRepo.findById(subCategoryId).get();
            subCategory.setCategoryId(categoryId);
            if (!subCategoryName.isEmpty()) {
                subCategory.setSubCategoryName(subCategoryName);
            }
            if (!file.isEmpty()) {
                String url = subCategory.getSubCategoryImgUrl();
                if(url!=null) {
                    storageServices.deleteFile(url);
                }
                subCategory.setSubCategoryImgUrl(storageServices.uploadFile(path, file));
            } else {
                subCategory.setSubCategoryImgUrl(subCategory.getSubCategoryImgUrl());
            }
            subCategoryRepo.save(subCategory);
            return new ResponseEntity(new MessageResponse(true, "Successfully Updated..."), HttpStatus.OK);
        } else {
            return new ResponseEntity(new MessageResponse(false, "This id doesn't exist!!"), HttpStatus.OK);
        }
    }


}
