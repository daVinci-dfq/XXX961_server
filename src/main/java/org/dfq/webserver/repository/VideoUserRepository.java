package org.dfq.webserver.repository;

import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.dfq.webserver.models.VideoUserRelation;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface VideoUserRepository extends JpaRepository<VideoUserRelation, Integer>{

    List<User> findByVideoId(Integer videoId);

    List<Video> findByUserId(Integer userId);

}


