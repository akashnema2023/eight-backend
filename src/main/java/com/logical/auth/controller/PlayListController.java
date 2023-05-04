package com.logical.auth.controller;

import com.logical.auth.model.response.MessageResponse;
import com.logical.auth.services.impl.PlayListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/playlist")
public class PlayListController {
    @Autowired
    PlayListService playListService;
    private Logger logger = LoggerFactory.getLogger(PlayListController.class);
    @PostMapping("/createPlayList")
    public ResponseEntity<?>createPlayList(@RequestParam(name = "userId",required = true,defaultValue = "0")long userId, @RequestParam(name="playListName", required = true)String playListName, @RequestParam(name="playListImg",required = true)MultipartFile file){
        try{
            return playListService.createPlayList(userId,playListName,file);
        }
        catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);        }
    }
    @PostMapping("/addVideoInPlayList")
    public ResponseEntity<?>addVideoInPlayList(@RequestParam(name = "playListId",required = true)int playListId,@RequestParam(name="userId",required=true)long userId,@RequestParam(name="videoId",required=true)int videosId){
        try{
             return playListService.addVideoInPlayList(playListId,userId,videosId);
        } catch (Exception e) {
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);        }
    }
    @DeleteMapping("/deletePlaylist")
    public ResponseEntity<?>deletePlaylist(@RequestParam(name = "playListId",required = true) int playListId){
        try{
            return playListService.deletePlayList(playListId);
        }catch (Exception e){
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);        }
    }
    @DeleteMapping("/deleteVideoPresentInPlayList")
    public ResponseEntity<?>deleteVideoPresentInPlayList(@RequestParam(name = "playListId",required = true)int playListId,@RequestParam(name="userId",required=true)long userId,@RequestParam(name="videoId",required=true)int videosId){
        try{
            return playListService.deleteVideoPresentInPlayList(playListId,userId,videosId);
        }catch (Exception e){
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);        }
    }
    @GetMapping("/getAllVideosPresentInPlayList")
    public ResponseEntity<?>getListVideosPresentInPlayList(@RequestParam(name = "playListId",required = true,defaultValue = "0")int playListId,@RequestParam(name = "UserId",required = true,defaultValue = "0")long userId){
        try{
            return playListService.getListVideosFromPlayList(playListId,userId);
        }catch (Exception e){
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);        }
    }
    @GetMapping("/getAllPlayListWithVideosByUserId")
    public ResponseEntity<?>getAllPlayListWithVideosByUserId(@RequestParam(name="userId",required = true,defaultValue = "0") long userId){
        try{
             return playListService.getListOfPlayListWithVideosByUserId(userId);
        }catch (Exception e){
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListPlayListByUserId")
    public ResponseEntity<?>getListPlayListByUserId(@RequestParam(name="userId",required = true,defaultValue = "0") long userId){
        try{
            return playListService.getListOfPlayListByUserIds(userId);
        }catch (Exception e){
            logger.info(" "+e);
            return new ResponseEntity<>(new MessageResponse(false,"Something went wrong...Don't very we are figuring out what went wrong...!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
