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

    private final String videoUploadDir = "uploads/videos/";   // 本地存储路径
    private final String thumbnailUploadDir = "uploads/thumbnails/";   // 缩略图存储路径


    //为了避免每次上传时都创建目录，可以在应用启动时（或第一次上传时）检查并创建目录。如果目录已存在，可以跳过创建过程。
    public void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 视频上传
    public Video uploadVideo(Video video, MultipartFile videoFile, MultipartFile thumbnailFile) throws IOException {
        // 创建目录

        createDirectoryIfNotExists(videoUploadDir);
        createDirectoryIfNotExists(thumbnailUploadDir);

        // 上传视频文件
        //直接使用了原始文件名来保存上传的文件，可能存在文件名冲突的风险
        String videoFilePath = videoUploadDir + videoFile.getOriginalFilename();
        videoFile.transferTo(new File(videoFilePath));
        video.setVideoUrl(videoFilePath);

        // 上传封面文件
        String thumbnailFilePath = thumbnailUploadDir + thumbnailFile.getOriginalFilename();
        thumbnailFile.transferTo(new File(thumbnailFilePath));
        video.setThumbnailUrl(thumbnailFilePath);

        return videoRepository.save(video);//上传的 Video 对象会被保存到数据库
    }
}
