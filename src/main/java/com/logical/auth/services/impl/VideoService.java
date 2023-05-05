package com.logical.auth.services.impl;

import com.logical.auth.entity.*;
import com.logical.auth.enums.VideoType;
import com.logical.auth.model.Request.PushNotificationRequest;
import com.logical.auth.model.Request.UpdateVideoRequest;
import com.logical.auth.model.Request.UploadVideoRequest;
import com.logical.auth.model.response.*;
import com.logical.auth.repository.*;
import com.logical.auth.services.StorageServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.logical.auth.enums.VideoType.Global;
import static com.logical.auth.enums.VideoType.Private;

@Service
public class VideoService {
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MyDownloadRepo myDownloadRepo;
    @Autowired
    LikesRepo likesRepo;
    @Autowired
    SubscribeRepo subscribeRepo;
    @Autowired
    StorageServices fileService;
    @Autowired
    HisoryRepo hisoryRepo;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    SubCategoryRepo subCategoryRepo;
    //    @Autowired
    //    FileService fileService;
    @Value("${project.videos}")
    String path;
    @Value("${project.thumbnail}")
    String thumbpath;
    @Autowired
    PrimeRepo primeRepo;
    //    @Autowired
//    RestTemplate restTemplate;
//    @Autowired
//    FCMService fcmService;
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    ModelMapper modelMapper;
    public ResponseEntity<?> uploadVideo(MultipartFile videoFile, MultipartFile thumbFile, Long userId, int categoryId, int subCategoryId, String videoTitle, String description, String tag, VideoType videoType) throws IOException, ExecutionException, InterruptedException {
        if (userId > 0) {
            if (categoryId > 0 && subCategoryId > 0) {
                if (userRepository.existsById(userId)) {
                    if (!(thumbFile.isEmpty())) {
                        if (!(thumbFile.isEmpty())) {
                            UserData userData = userRepository.findById(userId).get();
                            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
                            VideoData videoData = new VideoData();

//                            videoData.setVideoUrl(this.fileService.uploadFile(path, videoFile));
                            videoData.setVideoUrl(this.fileService.uploadFile(path, videoFile));
//                            videoData.setThumbNailUrl(this.fileService.uploadFile(thumbpath, thumbFile));;
                            videoData.setThumbNailUrl(this.fileService.uploadFile(thumbpath, thumbFile));
                            videoData.setUserId(userId);
                            videoData.setUserName(userData.getFirstName() + " " + userData.getLastName());
                            videoData.setCategoryId(categoryId);
                            videoData.setSubCategoryId(subCategoryId);
                            videoData.setDescription(description);
                            videoData.setTag(tag);
                            videoData.setVideoTitle(videoTitle);
                            videoData.setVideoType(videoType);
                            videoData.setUploadDate(new Date());
                            videoData.setTotalViews(0);
                            videoData.setTotalLikes(0);
                            videoData.setLikeStatus(false);
                            videoData.setSubscribeStatus(false);
                            videoData.setUserProfileUrl("");
//                            if(videoType.toString().equals("Private")){
//                                PrimeVideoData primeVideoData=this.modelMapper.map(videoData,PrimeVideoData.class);
//                                primeRepo.save(primeVideoData);
//                            }else
//                            {
                                videoRepository.save(videoData);
//                            }
                            userData.setTotalUploadedVideos((userData.getTotalUploadedVideos()) + 1);
                            userRepository.save(userData);
                            List<SubscribeData> subscribeDataList = subscribeRepo.getListAllSubscriberByUserId(userId);
                            String name = userData.getFirstName();
                            if (!name.isEmpty()) {
                                if (!(subscribeDataList.isEmpty())) {
                                    for (SubscribeData subscribeData : subscribeDataList) {
                                        NotificationData notificationData = new NotificationData();
                                        String message = name + " uploaded " + videoTitle;
                                        notificationData.setUserId(subscribeData.subscriberUserId);
                                        notificationData.setNotificationMessage(message);
                                        notificationData.setCreatedAt(new Date());
                                        notificationRepo.save(notificationData);
                                        //                                pushNotificationRequest.setMessage(message);
                                        //                                fcmService.sendMessageToToken(pushNotificationRequest);
                                    }
                                }
                            }
                            return new ResponseEntity<>(new MessageResponse(true, "Video uploaded successfully"), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(new MessageResponse(false, "Please...select video for upload...!"), HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Please...select thumbfile...!"), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId "), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid categoryId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getVideosByUserId(long userId) {
        //   GetListVideosByUserIdResponse getListVideosByUserIdResponse=new GetListVideosByUserIdResponse();
        List<VideoData> listVideos = this.videoRepository.getVideosByUserId(userId);
        if (!(listVideos.isEmpty())) {
            return new ResponseEntity<>(new GetListVideosByUserIdResponse(true, "Success", listVideos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "There is no videos with this userId...!"), HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> updateVideoByUserId(MultipartFile videoFile, MultipartFile thumbFile, Long userId, int videoId, int categoryId, int subCategoryId, String videoTitle, String description, String tag, VideoType videoType) throws IOException {
        if (videoId > 0) {
            if (userId > 0) {
                if (categoryId > 0) {
                    if (userRepository.existsById(userId)) {
                        if (videoRepository.existsById(videoId)) {
                            VideoData videoData = videoRepository.findById(videoId).orElseThrow();
                            UserData userData = userRepository.findById(userId).get();
                            if (videoData.getUserId() == userId) {
//                                videoData.setVideoUrl(this.fileService.uploadFile(path, videoFile));
                                if (!videoFile.isEmpty()) {
                                    String url = videoData.getVideoUrl();
                                    fileService.deleteFile(url);
                                    videoData.setVideoUrl(this.fileService.uploadFile(path, videoFile));
                                } else {
                                    videoData.setVideoUrl(videoData.getVideoUrl());
                                }
                                if (!thumbFile.isEmpty()) {
                                    String url = videoData.getThumbNailUrl();
                                    fileService.deleteFile(url);
//                                videoData.setThumbNailUrl(this.fileService.uploadFile(thumbpath, thumbFile));
                                    videoData.setThumbNailUrl(this.fileService.uploadFile(thumbpath, thumbFile));
                                } else {
                                    videoData.setThumbNailUrl(videoData.getThumbNailUrl());
                                }
                                videoData.setUserId(userId);
                                videoData.setUserName(userData.getFirstName() + " " + userData.getLastName());
                                videoData.setCategoryId(categoryId);
                                if (subCategoryId > 0) {
                                    videoData.setSubCategoryId(subCategoryId);
                                }
                                videoData.setDescription(description);
                                videoData.setTag(tag);
                                videoData.setVideoTitle(videoTitle);
                                videoData.setVideoType(videoType);
                                videoData.setUploadDate(new Date());
                                videoRepository.save(videoData);
                                return new ResponseEntity<>(new UpdateVideoResponse(true, "success", videoData), HttpStatus.OK);
                            } else {
                                return new ResponseEntity<>(new MessageResponse(false, "User does not belongs to this videoId...!"), HttpStatus.NOT_FOUND);
                            }
                        } else {
                            return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId...!"), HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId...!"), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "Please provide valid categoryId...!"), HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> deleteVideo(int videoId) {
        if (videoId > 0) {
            if (videoRepository.existsById(videoId)) {
                VideoData videoData = videoRepository.findById(videoId).get();
//                UserData userData=userRepository.findById(videoData.getUserId()).get();
//                userData.setTotalUploadedVideos((userData.getTotalUploadedVideos())-1);
                String videoUrl = videoData.getVideoUrl();
                String thumbNailUrl = videoData.getThumbNailUrl();
                if (!videoUrl.isEmpty()) {
                    fileService.deleteFile(videoUrl);
                }
                if (!thumbNailUrl.isEmpty()) {
                    fileService.deleteFile(thumbNailUrl);
                }
                videoRepository.deleteById(videoId);
                return new ResponseEntity<>(new MessageResponse(true, "Video deleted successfully with this videoId...! "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId...!"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getVideoByVideoId(int videoId) {
        if (videoId > 0) {
            if (videoRepository.existsById(videoId)) {
                VideoData videoData = videoRepository.findById(videoId).get();
                return new ResponseEntity<>(new GetVideoByVideoIdResponse(true, "Success", videoData), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId...!"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getListVideosByCategoryId(int categoryId) {
        if (categoryId > 0) {
            List<VideoData> videoList = videoRepository.findByCategoryIdAndVideoType(categoryId,Global);
                    //videoRepository.getVideosByCategoryId(categoryId,Global);
            Collections.reverse(videoList);
            if (videoList.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse(false, "Videos does not exist with categoryId...!"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(new VideosListResponse(true, "Success", videoList), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid categoryId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getListVideosBySubCategoryId(int subCategoryId) {
        if (subCategoryId > 0) {
            List<VideoData> videoList = videoRepository.findBySubCategoryId(subCategoryId);
            Collections.reverse(videoList);
            if (videoList.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse(false, "Videos does not exist with subCategoryId...!"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(new VideosListResponse(true, "Success", videoList), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid subCategoryId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getTotalView(int videoId, long userId) {
        if (videoId > 0) {
            if (userId > 0) {
                if (userRepository.existsById(userId)) {
                    if (videoRepository.existsById(videoId)) {
                        VideoData videoData = videoRepository.findById(videoId).get();
                        if (videoData != null) {
                            long views = videoData.getTotalViews();
                            views = views + 1;
                            videoData.setTotalViews(views);
                            videoRepository.save(videoData);
                            return new ResponseEntity<>(new TotalViewsResponse(true, "Success", videoData.getTotalViews()), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(new TotalViewsResponse(true, "Success", videoData.getTotalViews()), HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId...!"), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getListVideos() {
        List<VideoData> videoDataList = videoRepository.findAll();
        List<VideoData>listNonPrimeVideos=new ArrayList<>();
        for(VideoData videoData:videoDataList){
            if(videoData.getVideoType()!=Private){
                listNonPrimeVideos.add(videoData);
            }
        }
        if (videoDataList.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse(false, "No Videos...Please upload video"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new VideosListResponse(true, "Success", listNonPrimeVideos), HttpStatus.OK);
        }
    }
    public ResponseEntity<?> getTotalLikes(int videoId, long userId) throws ExecutionException, InterruptedException {
        if (videoId > 0) {
            if (userId > 0) {
                if (userRepository.existsById(userId)) {
                    if (videoRepository.existsById(videoId)) {
                        LikesData likesData = likesRepo.getLikesDataByVideoId(videoId);
                        VideoData videoData = videoRepository.findById(videoId).get();
                        UserData userData = userRepository.findById(userId).get();
                        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
                        if (likesData != null) {
                            //if (likesData.getUserId() != userId) {
                            if (likesData.getUserId() == userId) {
                                long likes = videoData.getTotalLikes();
                                likes = likes - 1;
                                if (likes >= 0) {
                                    videoData.setTotalLikes(likes);
                                    videoData.setLikeStatus(false);
                                    likesData.setUserId(0);
                                    likesData.setLikeStatus(false);
                                    videoRepository.save(videoData);
                                    String name = userData.getFirstName();
                                    if (!name.isEmpty()) {
                                        //                                    NotificationData notificationData=new NotificationData();
                                        String message = name + " unlike your video ";
                                        //                                    notificationData.setUserId(videoData.getUserId());
                                        //                                    notificationData.setNotificationMessage(message);
                                        //                                    notificationData.setCreatedAt(new Date());
                                        //                                    notificationRepo.save(notificationData);
                                        //                                    pushNotificationRequest.setMessage(message);
                                        //                                    fcmService.sendMessageToToken(pushNotificationRequest);
                                    }
                                    // totalViewsResponse.setTotalViews(views);
                                    likesData = this.likesRepo.save(likesData);
                                    return new ResponseEntity<>(new TotalLikesResponse(true, "Success", videoData.getTotalLikes(), videoData.likeStatus), HttpStatus.OK);
                                } else {
                                    videoData.setTotalLikes(0);
                                    videoData.setLikeStatus(false);
                                    videoRepository.save(videoData);

                                    String name = userData.getFirstName();
                                    if (!name.isEmpty()) {
                                        //                                    NotificationData notificationData=new NotificationData();
                                        String message = name + " unlike your video ";
                                        //                                    notificationData.setUserId(videoData.getUserId());
                                        //                                    notificationData.setNotificationMessage(message);
                                        //                                    notificationData.setCreatedAt(new Date());
                                        //                                    notificationRepo.save(notificationData);
                                        //                                    pushNotificationRequest.setMessage(message);
                                        //                                    fcmService.sendMessageToToken(pushNotificationRequest);
                                    }
                                    return new ResponseEntity<>(new TotalLikesResponse(true, "Success", videoData.getTotalLikes(), videoData.likeStatus), HttpStatus.OK);
                                }
                            } else {
                                long likes = videoData.getTotalLikes();
                                likes = likes + 1;
                                likesData.setUserId(userId);
                                likesData.setLikeStatus(true);
                                likesData.setTotalLikes(likes);
                                videoData.setTotalLikes(likes);
                                videoData.setLikeStatus(true);
                                likesRepo.save(likesData);
                                videoRepository.save(videoData);
                                String name = userData.getFirstName();
                                if (!name.isEmpty()) {
                                    NotificationData notificationData = new NotificationData();
                                    String message = name + " likes your video ";
                                    notificationData.setUserId(videoData.getUserId());
                                    notificationData.setNotificationMessage(message);
                                    notificationData.setCreatedAt(new Date());
                                    notificationRepo.save(notificationData);
                                    //                                pushNotificationRequest.setMessage(message);
                                    //                                fcmService.sendMessageToToken(pushNotificationRequest);
                                }
                                // totalViewsResponse.setTotalViews(views);
                                return new ResponseEntity<>(new TotalLikesResponse(true, "Success", videoData.getTotalLikes(), videoData.likeStatus), HttpStatus.OK);
                            }
                        } else {
                            LikesData likesData1 = new LikesData(videoId, userId, 1, true);
                            long like = videoData.getTotalLikes();
                            like = like + 1;
                            videoData.setTotalLikes(like);
                            videoData.setLikeStatus(true);
                            videoRepository.save(videoData);
                            likesData1 = this.likesRepo.save(likesData1);
                            String name = userData.getFirstName();
                            //                        if (!name.isEmpty()) {
                            //                            pushNotificationRequest.setMessage(name + " likes your video ");
                            //                            fcmService.sendMessageToToken(pushNotificationRequest);
                            //                        }
                            return new ResponseEntity<>(new TotalViewsResponse(true, "Success", videoData.getTotalLikes()), HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId : "), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId... "), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getMyDownloadVideos(int videoId, long userId) {
        if (videoId > 0) {
            if (userId > 0) {
                if (userRepository.existsById(userId)) {
                    if (videoRepository.existsById(videoId)) {
                        // VideoData videoData=videoRepository.findById(videoId).get();
                        MyDownloadData myDownloadData = myDownloadRepo.findByVideoIdAndUserId(videoId, userId);
                        //getMyDownloadDataByVideoId(videoId,userId);
                        VideoData videoData = videoRepository.findById(videoId).get();
                        if (myDownloadData != null) {
                            if (myDownloadData.getUserId() == userId) {
                                // myDownloadData=null;
                                return new ResponseEntity<>(new MessageResponse(false, "Already Downloaded...!"), HttpStatus.BAD_REQUEST);
                            } else {
                                MyDownloadData myDownloadData1 = new MyDownloadData(videoId, userId, true);
                                videoData.setDownloadStatus(true);
                                videoRepository.save(videoData);
                                myDownloadRepo.save(myDownloadData1);
                                return new ResponseEntity<>(new MessageResponse(true, "Downloaded Successfully"), HttpStatus.OK);
                            }
                        } else {
                            MyDownloadData myDownloadData1 = new MyDownloadData(videoId, userId, true);
                            videoData.setDownloadStatus(true);
                            videoRepository.save(videoData);
                            myDownloadRepo.save(myDownloadData1);
                            return new ResponseEntity<>(new MessageResponse(true, "Downloaded Successfully"), HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId..."), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }

        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> subScribe(long userId, long subscriberUserId) throws ExecutionException, InterruptedException {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                if (userRepository.existsById(subscriberUserId)) {
                    if (userId != subscriberUserId) {
                        SubscribeData subscribeData = subscribeRepo.findByUserIdAndSubscriberUserId(userId, subscriberUserId);
                        VideoData videoData = new VideoData();
                        UserData userData = userRepository.findById(subscriberUserId).get();
                        UserData userData1 = userRepository.findById(userId).get();
                        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
                        List<VideoData> listVideos = this.videoRepository.getVideosByUserId(userId);
                        if (listVideos.isEmpty()) {
                            return new ResponseEntity<>(new MessageResponse(false, "No Videos of this user..."), HttpStatus.BAD_REQUEST);
                        } else {
                            if (subscribeData != null) {
                                int subscribeId = subscribeData.getSubscribeId();
                                int i = 0;
                                for (VideoData videoData1 : listVideos
                                ) {
                                    videoData1 = listVideos.get(i);
                                    videoData1.setSubscribeStatus(false);
                                    // userData1.setTotalSubscriber(userData1.getTotalSubscriber()-1);
                                    userRepository.save(userData1);
                                    videoRepository.save(videoData1);
                                    i++;
                                }
                                subscribeRepo.deleteById(subscribeId);
                                //                        String name = userData.getFirstName();
                                //                        if (!name.isEmpty()) {
                                //                            pushNotificationRequest.setMessage(name + " Unsbscribed your video ");
                                //                            pushNotificationRequest.setToken("https://oauth2.googleapis.com/token");
                                //                            fcmService.sendMessageToToken(pushNotificationRequest);
                                //                        }
                                userData1.setTotalSubscriber(userData1.getTotalSubscriber() - 1);
                                userRepository.save(userData1);
                                return new ResponseEntity<>(new MessageResponse(true, "Unsbscribed successfully"), HttpStatus.OK);
                            } else {
                                SubscribeData subscribeData1 = new SubscribeData(userId, subscriberUserId, true);
                                subscribeRepo.save(subscribeData1);
                                userData1.setTotalSubscriber(userData1.getTotalSubscriber() + 1);
                                userRepository.save(userData1);
                                int i = 0;
                                for (VideoData videoData1 : listVideos
                                ) {
                                    videoData1 = listVideos.get(i);
                                    videoData1.setSubscribeStatus(true);
                                    videoRepository.save(videoData1);
                                    i++;
                                }
                                //                        String name = userData.getFirstName();
                                //                        if (!name.isEmpty()) {
                                //                            pushNotificationRequest.setMessage(name + " subscribe your video ");
                                //                            pushNotificationRequest.setToken("https://oauth2.googleapis.com/token");
                                //                            fcmService.sendMessageToToken(pushNotificationRequest);
                                //                        }
                                userData1.setTotalSubscriber((userData1.getTotalSubscriber()) + 1);
                                return new ResponseEntity<>(new MessageResponse(true, "subscribe successfully..."), HttpStatus.OK);
                            }
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "you can't subscribe...because its your own chanel"), HttpStatus.NOT_ACCEPTABLE);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "SubsCriber user does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getHistoryVideos(int videoId, long userId) {
        if (videoId > 0) {
            if (userId > 0) {
                if (userRepository.existsById(userId)) {
                    if (videoRepository.existsById(videoId)) {
                        HistoryData historyData = hisoryRepo.findByVideoIdAndUserId(videoId, userId);
                        VideoData videoData = this.videoRepository.findById(videoId).get();
                        if (historyData != null) {
                            return new ResponseEntity<>(new MessageResponse(false, "Already in history"), HttpStatus.BAD_REQUEST);
                        } else {
                            UserData userData = userRepository.findById(userId).get();
                            String username = userData.getFirstName() + " " + userData.getLastName();
                            HistoryData historyData1 = new HistoryData(videoId, userId, username, videoData.getVideoUrl());
                            hisoryRepo.save(historyData1);
                            return new ResponseEntity<>(new MessageResponse(true, "Video added in history"), HttpStatus.OK);
                        }
                    } else {
                        HistoryData historyData = hisoryRepo.findByVideoIdAndUserId(videoId, userId);
                        if (historyData != null) {
                            int historyId = historyData.getHistoryId();
                            hisoryRepo.deleteById(historyId);
                        }
                        return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId..."), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> deleteVideoFromHistory(int videoId, long userId) {
        if (videoId > 0) {
            if (userId > 0) {
                if (userRepository.existsById(userId)) {
                    if (videoRepository.existsById(videoId)) {
                        HistoryData historyData = hisoryRepo.findByVideoIdAndUserId(videoId, userId);
                        if (historyData != null) {
                            int historyId = historyData.getHistoryId();
                            hisoryRepo.deleteById(historyId);
                            return new ResponseEntity<>(new MessageResponse(true, "Video deleted successfully from history"), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(new MessageResponse(false, "Video not in your history"), HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Video does not exist with this videoId..."), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid videoId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> getListMyHistory(long userId) {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                List<HistoryData> historyDataList = hisoryRepo.findListHistoryByUserId(userId);
                if (historyDataList.isEmpty()) {
                    return new ResponseEntity<>(new MessageResponse(false, "There is nothing in your history"), HttpStatus.NOT_FOUND);
                }
                VideoData videoData = new VideoData();
                List<VideoData> videoDataList = new ArrayList<>();
                // HistoryData historyData=new HistoryData();
                int i = 0;
                for (HistoryData historyData : historyDataList
                ) {
                    historyData = historyDataList.get(i);
                    int videoId = historyData.getVideoId();
                    videoData = videoRepository.findById(videoId).get();
                    if (videoData != null) {
                        videoDataList.add(videoData);
                    }
                    i++;
                }
                return new ResponseEntity<>(new ListMyHistoryResponse(true, "Success", videoDataList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId "), HttpStatus.NOT_ACCEPTABLE);
        }
    }

//    public ResponseEntity<?> getListVideosByCategory() {
//        List<VideoData> videoDataList = videoRepository.findAll();
//        //CategoryList categoryList=new CategoryList();
//        Map<String, List<VideoData>> map = new HashMap<>();
//        List<Map<String, List<VideoData>>> list = new ArrayList<>();
//        ListVideosByCategoryNameResponse listVideosByCategoryNameResponse = new ListVideosByCategoryNameResponse();
//        VideoData videoData = new VideoData();
//        for (VideoData data : videoDataList) {
//            videoData = data;
//            int catId = videoData.getCategoryId();
//            CategoryData categoryData = categoryService.getCategoryById(catId);
//            if (categoryData != null) {
//                String categoryName = categoryData.getCategoryName();
//                List<VideoData> videoData1 = videoRepository.getVideosByCategoryId(catId);
//                map.put(categoryName, videoData1);
//                list.add(map);
//            }
//        }
//        return new ResponseEntity<>(new ListVideosByCategoryNameResponse(true, "Success", map), HttpStatus.OK);
//    }

    public ResponseEntity<?> getVideoLikesByUserId(long userid) {
        if (userid > 0) {
            if (userRepository.existsById(userid)) {
                List<LikesData> myLikesByUserId = likesRepo.getMyLikesByUserId(userid);
                List<VideoData> list = new ArrayList<>();
                if (myLikesByUserId.isEmpty()) {
                    return new ResponseEntity<>(new MessageResponse(false, "This user id isn't there please provide valid user id!!!"), HttpStatus.NOT_FOUND);
                } else {
                    int i = 0;
                    while (i < myLikesByUserId.size()) {
                        LikesData likesData = myLikesByUserId.get(i);
                        VideoData vd = videoRepository.findById(likesData.getVideoId()).get();
                        //                    String videoUrl=vd.getVideoUrl();
                        list.add(vd);
                        i++;
                    }
                    return new ResponseEntity<>(new ListOfLikesByUserId(true, "success..", list), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(new MessageResponse(false, "provide valid user id!!!"), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getListTrendingVideos(String sort) {
        List<VideoData> dataList = new ArrayList<>();
        dataList = videoRepository.findAll(Sort.by(sort));
        if (dataList.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse(false, "There no videos in trending...!"), HttpStatus.NOT_FOUND);
        } else {
            Collections.reverse(dataList);
            return new ResponseEntity<>(new TrendingVideosResponse(true, "Success", dataList), HttpStatus.OK);
        }
    }
    public ResponseEntity<?> getAboutUs() {
        AboutUsResponse aboutUsResponse = new AboutUsResponse();
        aboutUsResponse.setResult(true);
        aboutUsResponse.setMessage("Success");
        aboutUsResponse.setData("about: editable content is available under the terms of the GFDL and the CC By-SA License. All non-editable content and all content in the Learn section are copyrighted by AboutUs.");
        return ResponseEntity.ok(aboutUsResponse);
    }
    public ResponseEntity<?> getListVideosByKeyword(String keyword) {
        if (!(keyword.isEmpty())) {
            List<VideoData> videoDataList = videoRepository.getListVideosByKeyword(keyword);
            if (videoDataList.isEmpty()) {
                String s = "" + keyword.charAt(0);
                videoDataList = videoRepository.getListVideosByKeywords(s);
                return new ResponseEntity<>(new SearchVideosByKeywordResponse(true, "Success", videoDataList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new SearchVideosByKeywordResponse(true, "Success", videoDataList), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Keyword should not be empty...!"), HttpStatus.NOT_FOUND);
        }
    }

//    public ResponseEntity<?> getListVideosAccordingExplore() {
//        List<VideoData> videoDataList = videoRepository.findAll();
//        Map<String, List<VideoData>> map = new HashMap<>();
//        List<Map<String, List<VideoData>>> list = new ArrayList<>();
//        ListExploreVideosResponse listExploreVideosResponse = new ListExploreVideosResponse();
//        VideoData videoData = new VideoData();
//        for (VideoData data : videoDataList) {
//            videoData = data;
//            int catId = videoData.getCategoryId();
//            CategoryData categoryData = categoryService.getCategoryById(catId);
//            if (categoryData != null) {
//                String categoryName = categoryData.getCategoryName();
//                List<VideoData> videoData1 = videoRepository.getVideosByCategoryId(catId);
//                map.put(categoryName, videoData1);
//                list.add(map);
//            }
//        }
//        return new ResponseEntity<>(new ListExploreVideosResponse(true, "Success", map), HttpStatus.OK);
//    }

    public ResponseEntity<?> getListNotificationByUserId(long userId) {
        if (userId > 0) {
            if (userRepository.existsById(userId)) {
                List<NotificationData> listNotification = notificationRepo.findListNotificationByUserId(userId);
                if (listNotification.isEmpty()) {
                    return new ResponseEntity<>(new MessageResponse(false, "There is no notification of the given user...!"), HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>(new NotificationListByUserIdResponse(true, "Success", listNotification), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this UserId...!"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId...!"), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public String showVideoByVideoId(int videoId) {
        if (videoRepository.existsById(videoId)) {
            VideoData videoData = videoRepository.findById(videoId).get();
            String videoUrl = videoData.getVideoUrl();
            String url = "https://seven02.s3.ap-south-1.amazonaws.com/" + videoUrl;
            return url;
        } else {
            return "This video id doesn't exist provide valid id!!";
        }
    }
    public ResponseEntity<?> getListVideosByCategorysTest() {
        List<VideoData> videoDataList = videoRepository.findAll();
        GetListVideosByCategoryNameResponse []getListVideosByCategoryNameResponse=new GetListVideosByCategoryNameResponse[videoDataList.size()];
        Set<GetListVideosByCategoryNameResponse []>listEx=new HashSet<>();
        Set<Integer>set=new HashSet<>();
        set.add(0);
        int j=0;
        VideoData videoData = new VideoData();
        for(int i=0;i<videoDataList.size();i++){

            videoData = videoDataList.get(i);
            int catId = videoData.getCategoryId();
            CategoryData categoryData = categoryService.getCategoryById(catId);
            if (categoryData != null) {
                if (!(set.contains(catId))) {
                    List<VideoData> videoData1 = videoRepository.findByCategoryIdAndVideoType(catId,Global);
                    set.add(catId);
                    Set<VideoData>s=new HashSet<>();
                    for (VideoData v:videoData1){
                        if(v!=null){
                            s.add(v);
                        }
                    }
                    getListVideosByCategoryNameResponse[i] = new GetListVideosByCategoryNameResponse( catId, categoryData.categoryName,s);
                    listEx.add(getListVideosByCategoryNameResponse);
                    j++;
                }
            }
        }
        int k=0;
        GetListVideosByCategoryNameResponse []getListVideosByCategoryNameResponse2=new GetListVideosByCategoryNameResponse[j];
        for(int i=0;i<getListVideosByCategoryNameResponse.length;i++){

            if(getListVideosByCategoryNameResponse[i]!=null){
                getListVideosByCategoryNameResponse2[k]=getListVideosByCategoryNameResponse[i];
                k++;
            }
        }
        return new ResponseEntity<>(new ListOfVideosAccordingToCategory1(true, "Success",getListVideosByCategoryNameResponse2 ), HttpStatus.OK);
    }
//    public ResponseEntity<?> getListVideosByCategorys() {
//        Map<String,List<Map<String,List<VideoData>>>>categorydata=new HashMap<>();
//        List<Map<String,List<VideoData>>>listMap=new ArrayList<>();
//        List<VideoData> videoDataList = videoRepository.findAll();
//
//        GetListVideosByCategoryNameResponse []getListVideosByCategoryNameResponse=new GetListVideosByCategoryNameResponse[videoDataList.size()];
//        Set<GetListVideosByCategoryNameResponse []>listEx=new HashSet<>();
//        Set<Integer>set=new HashSet<>();
//        set.add(0);
//
//        //CategoryList categoryList=new CategoryList();
//        Map<String, List<VideoData>> map = new HashMap<>();
//        List<Map<String, List<VideoData>>> list = new ArrayList<>();
//        ListVideosByCategoryNameResponse listVideosByCategoryNameResponse = new ListVideosByCategoryNameResponse();
//
//
//        VideoData videoData = new VideoData();
//
//        for(int i=0;i<videoDataList.size();i++){
//            //  if (i < videoDataList.size()) {
//            videoData = videoDataList.get(i);
//            int catId = videoData.getCategoryId();
//            CategoryData categoryData = categoryService.getCategoryById(catId);
//
//            if (categoryData != null) {
//                if (!(set.contains(catId))) {
//                    //String categoryName = categoryData.getCategoryName();
//                    List<VideoData> videoData1 = videoRepository.getVideosByCategoryId(catId);
//                    set.add(catId);
//                    Set<VideoData>s=new HashSet<>();
//                    s.addAll(videoData1);
//                    getListVideosByCategoryNameResponse[i] = new GetListVideosByCategoryNameResponse( catId, categoryData.categoryName,s);
//                    listEx.add(getListVideosByCategoryNameResponse);
//                    //i++;
//                }
//                //  map.put(categoryName, videoData1);
//                // list.add(map);
//            }
//            // i++;
//            // listEx.add(listVideosByCategoryName2s);
//            // }
////            listEx.add(listVideosByCategoryName2s);
//        }
//
////        for (VideoData data : videoDataList) {
////            videoData = data;
////            int catId = videoData.getCategoryId();
////            CategoryData categoryData = categoryService.getCategoryById(catId);
////            if (categoryData != null) {
////                videoData.setCategoryName(categoryData.getCategoryName());
////                String categoryName = categoryData.getCategoryName();
////                List<VideoData> videoData1 = videoRepository.getVideosByCategoryId(catId);
////                map.put(categoryName, videoData1);
////                list.add(map);
////            }
////        }
////        listMap.add(map);
////        categorydata.put("CategoryData",listMap);
//        //return new ResponseEntity<>(new ListVideosByCategoryNameResponse(true, "Success", map), HttpStatus.OK);
//        return new ResponseEntity<>(new ListOfVideosAccordingToCategory1(true, "Success",getListVideosByCategoryNameResponse ), HttpStatus.OK);
//    }
    public ResponseEntity<?> getListVideosByexplores() {
        List<VideoData> videoDataList = videoRepository.findAll();
        ListVideosByCategoryName2 []listVideosByCategoryName2s=new ListVideosByCategoryName2[videoDataList.size()];
        //  Set<ListVideosByCategoryName2 []>listEx=new HashSet<>();
        Set<Integer>set=new HashSet<>();
        set.add(0);
        int j=0,k=0;
        VideoData videoData = new VideoData();
        for(int i=0;i<videoDataList.size();i++){
            videoData = videoDataList.get(i);
            int catId = videoData.getCategoryId();
            CategoryData categoryData = categoryService.getCategoryById(catId);

            if (categoryData != null) {
                if (!(set.contains(catId))) {
                    List<VideoData> videoData1 = videoRepository.findByCategoryIdAndVideoType(catId,Global);
                            //videoRepository.getVideosByCategoryId(catId,Global);
                    set.add(catId);
                    Set<VideoData>s=new HashSet<>();
                    s.addAll(videoData1);
                    listVideosByCategoryName2s[i] = new ListVideosByCategoryName2(videoData1.size(), categoryData.getCategoryImageUrl(), catId, categoryData.categoryName,s);
                    // listEx.add(listVideosByCategoryName2s);
                    j++;
                }
            }
        }
        ListVideosByCategoryName2 []listVideosByCategoryName3s=new ListVideosByCategoryName2 [j];
        for(int i=0;i<listVideosByCategoryName2s.length;i++){
            if(listVideosByCategoryName2s[i]!=null){
                listVideosByCategoryName3s[k]=listVideosByCategoryName2s[i];
                k++;
            }
        }
        return new ResponseEntity<>(new ListExploreVideosResponse(true, "Success",listVideosByCategoryName3s ), HttpStatus.OK);
    }
//    public ResponseEntity<?> getListVideosByexplore() {
//        List<VideoData> videoDataList = videoRepository.findAll();
//        ListVideosByCategoryName2 []listVideosByCategoryName2s=new ListVideosByCategoryName2[videoDataList.size()];
//        Set<ListVideosByCategoryName2 []>listEx=new HashSet<>();
//        Set<Integer>set=new HashSet<>();
//        set.add(0);
//        VideoData videoData = new VideoData();
//        for(int i=0;i<videoDataList.size();i++){
//            videoData = videoDataList.get(i);
//            int catId = videoData.getCategoryId();
//            CategoryData categoryData = categoryService.getCategoryById(catId);
//
//            if (categoryData != null) {
//                if (!(set.contains(catId))) {
//                    List<VideoData> videoData1 = videoRepository.getVideosByCategoryId(catId);
//                    set.add(catId);
//                    Set<VideoData>s=new HashSet<>();
//                    s.addAll(videoData1);
//                    listVideosByCategoryName2s[i] = new ListVideosByCategoryName2(videoData1.size(), categoryData.getCategoryImageUrl(), catId, categoryData.categoryName,s);
//                    listEx.add(listVideosByCategoryName2s);
//                }
//            }
//        }
//        return new ResponseEntity<>(new ListExploreVideosResponse(true, "Success", listEx), HttpStatus.OK);
//    }
    public ResponseEntity<?>getListPrimeVideo(){
        List<VideoData> videoDataList = videoRepository.findAll();
        List<VideoData>listPrimeVideo=new ArrayList<>();
        for(VideoData videoData:videoDataList){
            if(videoData.getVideoType()==Private){
                listPrimeVideo.add(videoData);
            }
        }
        if (videoDataList.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse(false, "No Videos...Please upload video"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new VideosListResponse(true, "Success", listPrimeVideo), HttpStatus.OK);
        }
    }
}