package org.dfq.webserver.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.dfq.webserver.models.User;
import org.dfq.webserver.models.Video;
import org.springframework.data.relational.core.sql.In;

/**
 *视频与用户关系类(点赞,发布)
 *relationId(主键)
 *userID和videoId
 */
@Data
@Entity
public class VideoUserRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer relationId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "videoId")
    private Video video;


}
