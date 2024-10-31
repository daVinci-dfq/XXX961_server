package org.dfq.webserver.service;

import org.dfq.webserver.models.Video;
import org.dfq.webserver.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    private final String videoUploadDir = "uploads/videos/";
    private final String thumbnailUploadDir = "uploads/thumbnails/";

    // 视频上传
    public Video uploadVideo(Video video, MultipartFile videoFile, MultipartFile thumbnailFile) throws IOException {
        // 创建目录
        new File(videoUploadDir).mkdirs();
        new File(thumbnailUploadDir).mkdirs();

        // 上传视频文件
        String videoFilePath = videoUploadDir + videoFile.getOriginalFilename();
        videoFile.transferTo(new File(videoFilePath));
        video.setVideoUrl(videoFilePath);

        // 上传封面文件
        String thumbnailFilePath = thumbnailUploadDir + thumbnailFile.getOriginalFilename();
        thumbnailFile.transferTo(new File(thumbnailFilePath));
        video.setThumbnailUrl(thumbnailFilePath);

        return videoRepository.save(video);
    }
}
