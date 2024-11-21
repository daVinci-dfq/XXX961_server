package org.dfq.webserver.controllers;

import org.dfq.webserver.service.UserServiceImpl;
import org.dfq.webserver.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videoAndUser")
public class VideoUserRelationController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserServiceImpl userServiceImpl;

}
