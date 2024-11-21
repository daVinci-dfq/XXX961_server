package org.dfq.webserver.repository;

import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.dfq.webserver.models.VideoUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface VideoUserRepository extends JpaRepository<VideoUserRelation, Integer>{

    List<User> findByVideo_VideoId(Integer videoId);

    List<Video> findByUser_UserId(Integer userId);

}


