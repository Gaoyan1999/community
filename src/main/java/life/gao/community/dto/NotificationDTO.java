package life.gao.community.dto;

import life.gao.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Integer status;
    private Long gmtCreate;
    private String notifierName;
    private String OuterTitle;
    private Long outerid;
    private String typeName;
    private  Integer  type;

}
