package org.dfq.webserver.controllers;

import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.dfq.webserver.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<Video> uploadVideo(
            @RequestParam("videoName") String videoName,
            @RequestParam("publisher") User publisher,
            @RequestParam("label") String label,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile) throws IOException {


        Video video = new Video();
        video.setVideoName(videoName);
        video.setPublisher(publisher);
        video.setLabel(label);

        Video uploadedVideo = videoService.uploadVideo(video, videoFile, thumbnailFile);
        return ResponseEntity.ok(uploadedVideo);
    }
}
