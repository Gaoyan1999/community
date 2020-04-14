package life.gao.community.dto;

import life.gao.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private long id;
    private String title;
    private String description;
    private String tag;
    private  long  gmtCreate;
    private  long gmtModified;
    private long creator;
    private Integer  viewCount;
    private Integer  commentCount;
    private Integer  likeCount;
    private User user;


}
