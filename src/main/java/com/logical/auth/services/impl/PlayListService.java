package com.logical.auth.services.impl;

import com.logical.auth.client.PlayListVideosData;
import com.logical.auth.entity.PlayListData;
import com.logical.auth.entity.VideoData;
import com.logical.auth.model.Request.CreatePlayListRequest;
import com.logical.auth.model.response.GetVideosFromPlayListResponse;
import com.logical.auth.model.response.ListOfPlayListByUserIdResponse;
import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.repository.PlayListRepo;
import com.logical.auth.repository.PlayListVideosRepo;
import com.logical.auth.repository.UserRepository;
import com.logical.auth.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class PlayListService {
   @Autowired
   PlayListRepo playListRepo;
   @Autowired
   VideoRepository videoRepository;
   @Autowired
   UserRepository userRepository;
   @Autowired
    PlayListVideosRepo playListVideosRepo;
   public ResponseEntity<?>createPlayList(CreatePlayListRequest createPlayListRequest){
       if(createPlayListRequest.getUserId()>0) {
           if(userRepository.existsById(createPlayListRequest.getUserId())){
               //   PlayListVideos playListVideos=new PlayListVideos();
               PlayListData playListData = new PlayListData();
               playListData.setPlayListName(createPlayListRequest.getPlayListName());
               playListData.setUserId(createPlayListRequest.getUserId());
               playListRepo.save(playListData);
               return new ResponseEntity<>(new MessageResponse(true, "PlayList created successfully"), HttpStatus.OK);
           } else {
               return new ResponseEntity<>(new MessageResponse(false, "User does not exist with this userId..!"), HttpStatus.NOT_ACCEPTABLE);
           }
       } else {
           return new ResponseEntity<>(new MessageResponse(false, "Please provide valid userId...!"), HttpStatus.NOT_ACCEPTABLE);
       }
   }
   public ResponseEntity<?>deletePlayList(int playListId){
       if(playListId>0){
           if(playListRepo.existsById(playListId)){
               List<PlayListVideosData>playListVideosData=playListVideosRepo.findByPlayListId(playListId);
               if(playListVideosData.isEmpty()){
                   playListRepo.deleteById(playListId);
                   return new ResponseEntity<>(new MessageResponse(true,"PlayList deleted successfully "),HttpStatus.OK);
               }else{
                   for (PlayListVideosData playListVideosData1:playListVideosData){
                       int videosId=playListVideosData1.getTempVideoId();
                       playListVideosRepo.deleteById(videosId);
                   }
                   playListRepo.deleteById(playListId);
                   return new ResponseEntity<>(new MessageResponse(true,"PlayList deleted successfully "),HttpStatus.OK);
               }
           } else{
               return new ResponseEntity<>(new MessageResponse(false,"PlayList does not exist with this playListId"),HttpStatus.NOT_FOUND);
           }
       }else{
           return new ResponseEntity<>(new MessageResponse(false,"Please provide valid playListId"),HttpStatus.NOT_ACCEPTABLE);
       }
   }

    public ResponseEntity<?>addVideoInPlayList(int playListId,long userId,int videoId){
        if(playListId>0){
            if(userId>0) {
                if (videoId > 0) {
                    if(videoRepository.existsById(videoId)){
                        if (userRepository.existsById(userId)){
                            if (playListRepo.existsById(playListId)) {
                                PlayListData playListData = playListRepo.findByplayListIdAndUserId(playListId, userId);
                                VideoData videoData = videoRepository.findById(videoId).get();
                                PlayListVideosData playListVideos=new PlayListVideosData();
        //                        playListVideos =this.modelMapper.map(videoData, PlayListVideosData.class);
        //                        playListVideos.setPlayListId(playListId);
        //                        playListVideos.setPlayListUserId(userId);
                                playListVideos.setOriginalvideoId(videoId);
                                playListVideos.setUserId(videoData.getUserId());
                                playListVideos.setCategoryId(videoData.getCategoryId());
                                playListVideos.setVideoTitle(videoData.getVideoTitle());
                                playListVideos.setDescription(videoData.getDescription());
                                playListVideos.setTag(videoData.getTag());
                                playListVideos.setVideoType(videoData.getVideoType());
                                playListVideos.setUploadDate(videoData.getUploadDate());
                                playListVideos.setVideoUrl(videoData.getVideoUrl());
                                playListVideos.setThumbNailUrl(videoData.getThumbNailUrl());
                                playListVideos.setTotalViews(videoData.getTotalViews());
                                playListVideos.setTotalLikes(videoData.totalLikes);
                                playListVideos.setLikeStatus(videoData.isLikeStatus());
                                playListVideos.setDownloadStatus(videoData.isDownloadStatus());
                                playListVideos.setSubscribeStatus(videoData.isSubscribeStatus());
                                playListVideos.setUserProfileUrl(videoData.getUserProfileUrl());
                                playListVideos.setPlayListId(playListId);
                                playListVideos.setPlayListUserId(userId);
                                playListVideosRepo.save(playListVideos);
                                playListData.setThumbNailImgUrl(videoData.getThumbNailUrl());
                                playListRepo.save(playListData);
                                return new ResponseEntity<>(new MessageResponse(true, "Successfully added"), HttpStatus.OK);
                            } else {
                                return new ResponseEntity<>(new MessageResponse(false, "PlayList does exist with this playListId "), HttpStatus.NOT_FOUND);
                            }
                        } else {
                            return new ResponseEntity<>(new MessageResponse(false, "User does exist with this userId"), HttpStatus.NOT_ACCEPTABLE);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Video does exist with this videoId"), HttpStatus.NOT_ACCEPTABLE);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "Please provide videoId"), HttpStatus.NOT_ACCEPTABLE);
                }
            }else{
                return new ResponseEntity<>(new MessageResponse(false,"Please provide valid userId"),HttpStatus.NOT_ACCEPTABLE);
            }
        }else{
            return new ResponseEntity<>(new MessageResponse(false,"Please provide valid playListId...!"),HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?>getListVideosFromPlayList(int playListId,long userId){
        if(playListId>0){
            if(userId>0){
                if (userRepository.existsById(userId)){
                    if(playListRepo.existsById(playListId)){
                        List<PlayListVideosData> videoDataList=playListVideosRepo.findByPlayListIdAndPlayListUserId(playListId,userId);
                        if(!videoDataList.isEmpty()){
                            return new ResponseEntity<>(new GetVideosFromPlayListResponse(true,"Success",videoDataList),HttpStatus.OK);
                        }else{
                            return new ResponseEntity<>(new MessageResponse(false,"no videos in playlist...!"),HttpStatus.NOT_FOUND);
                        }
                    }else{
                        return new ResponseEntity<>(new MessageResponse(false,"PlayList does not exist with this playListId...!"),HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "User does exist with this userId"), HttpStatus.NOT_ACCEPTABLE);
                }
            }else{
                return new ResponseEntity<>(new MessageResponse(false,"Please....provide valid userId...!"),HttpStatus.NOT_ACCEPTABLE);
            }
        }else{
            return new ResponseEntity<>(new MessageResponse(false,"Please...provide valid playlistId...!"),HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?>getListOfPlayListByUserId(long userId){
        if(userId>0){
            if (userRepository.existsById(userId)){
                List<PlayListVideosData>playListVideosData=playListVideosRepo.findAll();
                Map<String,List<PlayListVideosData>>map=new HashMap<>();
                List<Map<String,  List<PlayListVideosData>>> list = new ArrayList<>();
                ListOfPlayListByUserIdResponse listOfPlayListByUserIdResponse=new ListOfPlayListByUserIdResponse();
                PlayListVideosData playListData=new PlayListVideosData();
                if(playListVideosData.isEmpty()){
                    return new ResponseEntity<>(new MessageResponse(false,"There is no playList with this userId...!"),HttpStatus.NOT_FOUND);
                }else {
                    for(PlayListVideosData playListVideosData1:playListVideosData){
                        playListData=playListVideosData1;
                       // String playListName=playListData.getPlayListName();
                        int playListId=playListData.getPlayListId();
                        PlayListData playListData1=this.playListRepo.findById(playListId).get();
                        if(playListData1 != null){
                            String playListName=playListData1.getPlayListName();
                            List<PlayListVideosData>playListVideosData2=this.playListVideosRepo.findByPlayListId(playListId);
                            map.put(playListName,playListVideosData2);
                            list.add(map);
                        }
                    }
                    return new ResponseEntity<>(new ListOfPlayListByUserIdResponse(true,"Success",map),HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(false, "User does exist with this userId"), HttpStatus.NOT_ACCEPTABLE);
            }
        }else{
            return new ResponseEntity<>(new MessageResponse(false,"Please....provide valid userId...!"),HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public ResponseEntity<?> deleteVideoPresentInPlayList(int playListId, long userId, int videoId) {
        if(playListId>0){
            if(userId>0) {
                if(videoRepository.existsById(videoId)){
                    if (userRepository.existsById(userId)){
                    if (videoId > 0) {
                        if (playListRepo.existsById(playListId)) {
                            List<PlayListVideosData> playListVideosData = playListVideosRepo.findByPlayListId(playListId);
                            if (playListVideosData.isEmpty()) {
                                return new ResponseEntity<>(new MessageResponse(false, "There is no videos in playList...!"), HttpStatus.NOT_FOUND);
                            } else {
                                boolean result = false;
                                for (PlayListVideosData playListVideosData1 : playListVideosData) {
                                    if (playListVideosData1.getTempVideoId() == videoId) {
                                        playListVideosRepo.deleteById(videoId);
                                        result = true;
                                        break;
                                    }
                                }
                                if (result) {
                                    return new ResponseEntity<>(new MessageResponse(true, "Video deleted successfully"), HttpStatus.OK);
                                }
                                return new ResponseEntity<>(new MessageResponse(false, "Video does not exist in the playList...!"), HttpStatus.NOT_FOUND);
                            }
                        }else {
                            return new ResponseEntity<>(new MessageResponse(false, "PlayList does exist with this playListId "), HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "Please provide videoId"), HttpStatus.NOT_ACCEPTABLE);
                    }
                    } else {
                        return new ResponseEntity<>(new MessageResponse(false, "User does exist with this userId"), HttpStatus.NOT_ACCEPTABLE);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(false, "Video does exist with this videoId"), HttpStatus.NOT_ACCEPTABLE);
                }
            }else{
                return new ResponseEntity<>(new MessageResponse(false,"Please provide valid userId"),HttpStatus.NOT_ACCEPTABLE);
            }
        }else{
            return new ResponseEntity<>(new MessageResponse(false,"Please provide valid playListId...!"),HttpStatus.NOT_ACCEPTABLE);
        }
    }
}