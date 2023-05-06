package com.logical.auth.controller;
import com.logical.auth.enums.VideoType;
import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.services.impl.VideoService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.ExecutionException;
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoService videoService;
    private Logger logger = LogManager.getLogger(VideoController.class);
    @PostMapping("/uploadVideo")
    public ResponseEntity<?> uploadVideo(@RequestParam(name = "videoFile", required = true) MultipartFile videoFile, @RequestParam(name = "thumbFile", required = false) MultipartFile thumbFile, @RequestParam(name = "userId", required = true) Long userId, @RequestParam(name = "categoryId", required = true) int categoryId, @RequestParam(name = "subCategoryId", required = true) int subCategoryId, @RequestParam(name = "videoTitle", required = false) String videoTitle, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "tag", required = false) String tag, @RequestParam(name = "videoType", required = true) VideoType videoType) throws IOException {
        try {
            return videoService.uploadVideo(videoFile, thumbFile, userId, categoryId, subCategoryId, videoTitle, description, tag, videoType);
        } catch (UncheckedIOException e1) {
            logger.error(e1.getMessage());
            logger.info("---- " + e1);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...Don't very we are figuring out what went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("-------------" + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllVideosByUserId")
    public ResponseEntity<?> getAllVideosByUserId(@RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            if (userId > 0) {
//                    List<VideoData>listVideos=videoService.getVideosByUserId(userId);
//                    if(listVideos.isEmpty()){
//                        return new ResponseEntity<>(new MessageResponse( false,"Videos not available with this UserId : "+userId), HttpStatus.NOT_FOUND);
//                    }else {
//                        return ResponseEntity.ok(listVideos);
//                    }
                return videoService.getVideosByUserId(userId);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateVideoByUserId")
    public ResponseEntity<?> updateVideoByUserId(@RequestParam(name = "videoFile", required = true) MultipartFile videoFile, @RequestParam(name = "thumbFile", required = true) MultipartFile thumbFile, @RequestParam(name = "userId", required = true) Long userId, @RequestParam(name = "videoId", required = true) int videoId, @RequestParam(name = "categoryId", required = true) int categoryId, @RequestParam(name = "subCategoryId", required = false) int subCategoryId, @RequestParam(name = "videoTitle", required = false) String videoTitle, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "tag", required = false) String tag, @RequestParam(name = "videoType", required = false) VideoType videoType) {
        try {
            return videoService.updateVideoByUserId(videoFile, thumbFile, userId, videoId, categoryId, subCategoryId, videoTitle, description, tag, videoType);
            //    public ResponseEntity<?> uploadVideo(@RequestParam(name="videoFile",required = true,defaultValue ="null") MultipartFile videoFile, @RequestParam(name="thumbFile",required = true,defaultValue ="null") MultipartFile thumbFile,@RequestPart(name = "userId",required =true)Long userId,@RequestPart(name = "videoId",required =true)int videoId,@RequestPart(name = "categoryId",required =true)int categoryId,@RequestPart(name = "videoTitle",required =false)String videoTitle,@RequestPart(name = "description",required =false)String description,@RequestPart(name = "tag",required =false)String tag,@RequestPart(name = "videoType",required =false) VideoType videoType) throws IOException {
            //  return videoService.uploadVideo(videoFile, thumbFile,userId,videoId,categoryId,videoTitle,description,tag,videoType);

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteVideoByVideoId")
    public ResponseEntity<?> deleteVideoByVideoId(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId) {
        try {
            return videoService.deleteVideo(videoId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getVideoByVideoId")
    public ResponseEntity<?> getVideoByVideoId(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId) {
        try {
            return videoService.getVideoByVideoId(videoId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllVideosByCategoryId")
    public ResponseEntity<?> getListVideosByCategoryId(@RequestParam(name = "categoryId", required = true, defaultValue = "0") int categoryId) {
        try {
            return videoService.getListVideosByCategoryId(categoryId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllVideosBySubCategoryId")
    public ResponseEntity<?> getListVideosBySubCategoryId(@RequestParam(name = "subCategoryId", required = true) int subCategoryId) {
        try {
            return videoService.getListVideosBySubCategoryId(subCategoryId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTotalLikes")
    public ResponseEntity<?> getTotalLikes(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId, @RequestParam(name = "userId", required = true, defaultValue = "0") long userId) throws ExecutionException, InterruptedException {
        try {
            return videoService.getTotalLikes(videoId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTotalViews")
    public ResponseEntity<?> getTotalViews(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId, @RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            return videoService.getTotalView(videoId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllVideos")
    public ResponseEntity<?> getListAllVideos() {
        try {
            return videoService.getListVideos();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadVideo")
    public ResponseEntity<?> downloadVideo(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId, @RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            return videoService.getMyDownloadVideos(videoId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam(name = "userId", required = true, defaultValue = "0") long userId, @RequestParam(name = "subscriberUserId", required = true, defaultValue = "0") long subscriberUserId) throws ExecutionException, InterruptedException {
        try {
            return videoService.subScribe(userId, subscriberUserId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTotalsubscribe")
    public ResponseEntity<?> subscribe(@RequestParam(name = "userId", required = true, defaultValue = "0") long userId){
        try {
            return videoService.getTotalSubScribe(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/addVideoInHistory")
    public ResponseEntity<?> addVideoInHistory(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId, @RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            return videoService.getHistoryVideos(videoId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleteVideoFromHistory")
    public ResponseEntity<?> deleteVideoFromHistory(@RequestParam(name = "videoId", required = true, defaultValue = "0") int videoId, @RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            return videoService.deleteVideoFromHistory(videoId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListMyHistory")
    public ResponseEntity<?> getListMyHistory(@RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            return videoService.getListMyHistory(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListVideosAccordingCategoryNameResponse")
    public ResponseEntity<?> getListVideosAccordingCategoryName() {
        try {
            return videoService.getListVideosByCategory();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListVideosLikesByUser")
    public ResponseEntity<?> getListVideosLikesByUser(@RequestParam(name = "userId") long userId) {
        try {
            ResponseEntity<?> videoLikesByUserId = videoService.getVideoLikesByUserId(userId);
            return videoLikesByUserId;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trending")
    public ResponseEntity<?> getListTrendingVideos(@RequestParam(value = "sortBy", defaultValue = "totalViews", required = false) String sort) {
        try {
            return videoService.getListTrendingVideos(sort);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/aboutUs")
    public ResponseEntity<?> getAboutUs() {
        try {
            return videoService.getAboutUs();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByKeyword")
    public ResponseEntity<?> getListVideosByKeyword(@RequestParam(name = "searchKeyword", required = true) String keyword) {
        try {
            return videoService.getListVideosByKeyword(keyword);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/explore")
    public ResponseEntity<?> getListExploreVideos() {
        try {
            return videoService.getListVideosByexplores();
            //return videoService.getListVideosByexplore();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListNotificationByUserId")
    public ResponseEntity<?> getListNotificationByUserId(@RequestParam(name = "userId", required = true, defaultValue = "0") long userId) {
        try {
            return videoService.getListNotificationByUserId(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" " + e);
            return new ResponseEntity<>(new MessageResponse(false, "Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/showVideoByVideoId")
    public String showVideoByVedId(@RequestParam(name = "videoId", required = true) int videoId) {
        if (videoId > 0) {
            return videoService.showVideoByVideoId(videoId);
        } else {
            return "provide valid id!!";
        }
    }
    @GetMapping("/getListVideosAccordingCategoryName")
    public ResponseEntity<?>getListVideosAccordingCategoryNametest1(){
        try{
            return videoService.getListVideosByCategorysTest();
        //    return videoService.getListVideosByCategorys();
//            return videoService.getVideoAccordingToCategory1();

        }catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse( false,"Something went wrong...!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getListPrimeVideo")
    public ResponseEntity<?>getListPrimeVideo(){
        return videoService.getListPrimeVideo();
    }
    @GetMapping("/getListPrimeVideoAccordingCategoryName")
    public ResponseEntity<?> getListPrimeVideoAccordingCategoryName(){
        return videoService.getListPrimeVideoByCategoryName();
    }

}
