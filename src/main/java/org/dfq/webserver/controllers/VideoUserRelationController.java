package org.dfq.webserver.controllers;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.dfq.webserver.service.UserService;
import org.dfq.webserver.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/videoAndUser")
public class VideoUserRelationController {

    @Autowired
    private VideoService videoService;
    private UserService userService;

}
