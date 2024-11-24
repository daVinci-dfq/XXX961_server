package org.dfq.webserver.models;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "videos")
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;  // 视频 ID

    private String videoName;  // 视频名称
    @ManyToOne
    private User publisher;   // 发布者

    private String videoUrl;    // 视频 URL

    private String thumbnailUrl; // 视频封面 URL


}
