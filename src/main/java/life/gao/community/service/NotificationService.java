package life.gao.community.service;


import life.gao.community.dto.NotificationDTO;
import life.gao.community.dto.PaginationDTO;
import life.gao.community.dto.QuestionDTO;
import life.gao.community.enums.NotificationEnum;
import life.gao.community.enums.NotificationStatusEnum;
import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import life.gao.community.mapper.NotificationMapper;
import life.gao.community.mapper.UserMapper;
import life.gao.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {


    @Autowired
    private NotificationMapper notificationMapper;


    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<NotificationDTO>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria();

        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);


        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }


        paginationDTO.setPagenation(totalPage, page);
        //size *(page-1)
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();

            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOS);


        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);

    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null) {
            if (notification.getReceiver() != user.getId()) {
                throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
            }
        }
        notification.setStatus(NotificationStatusEnum.Read.getStatus());
        notificationMapper.updateByPrimaryKey(notification);


        NotificationDTO notificationDTO=new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }
}
