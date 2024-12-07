package org.dfq.webserver.controllers;

import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.dfq.webserver.security.Response;
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
    public Response uploadVideo(
            @RequestParam("videoName") String videoName,
            @RequestParam("publisher") User publisher,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile) throws IOException {

        Video video = new Video();
        video.setVideoName(videoName);
        video.setPublisher(publisher);

        Video uploadedVideo = videoService.uploadVideo(video, videoFile, thumbnailFile);
        return Response.ok(uploadedVideo);
    }
}
