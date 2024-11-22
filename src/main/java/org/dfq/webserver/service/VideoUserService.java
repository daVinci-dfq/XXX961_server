package org.dfq.webserver.service;

import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.dfq.webserver.repository.UserRepository;
import org.dfq.webserver.repository.VideoRepository;
import org.dfq.webserver.repository.VideoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.dfq.webserver.models.VideoUserRelation;

@Service
public class VideoUserService {

    @Autowired
    private VideoUserRepository videoUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    //保存relation
    public VideoUserRelation videoUserSave(Integer userId, Integer videoId) {
        VideoUserRelation videoUserRelation = new VideoUserRelation();
        User user;
        Video video;


        return videoUserRepository.save(videoUserRelation);
    }
}
