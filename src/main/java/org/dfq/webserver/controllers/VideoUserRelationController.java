package org.dfq.webserver.controllers;

import org.dfq.webserver.models.VideoUserRelation;
import org.dfq.webserver.service.UserService;
import org.dfq.webserver.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videoAndUser")
public class VideoUserRelationController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/videoUserSave")
    public ResponseEntity<String> videoUserSave(
            @RequestParam("userID") Integer userId,
            @RequestParam("videoId") Integer videoId
    ) {
        VideoUserRelation videoUserRelation = new VideoUserRelation();



        String msg = "保存成功。";
        return ResponseEntity.ok(msg);
    }
}
