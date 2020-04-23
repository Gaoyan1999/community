package life.gao.community.service;


import life.gao.community.dto.CommentDTO;
import life.gao.community.enums.CommentTypeEnum;
import life.gao.community.enums.NotificationEnum;
import life.gao.community.enums.NotificationStatusEnum;
import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import life.gao.community.mapper.CommentMapper;
import life.gao.community.mapper.NotificationMapper;
import life.gao.community.mapper.QuestionMapper;
import life.gao.community.mapper.UserMapper;
import life.gao.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional //事务
    public void insert(Comment comment,User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.Target_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.Target_PARAM_NOT_FOUND);


        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) { // 枚举类中的comment成员
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FIND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);

            //创建通知
            createNotify(comment,dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_COMMENT,question.getId());

        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //创建通知
            createNotify(comment,question.getCreator(),commentator.getName(), question.getTitle(),NotificationEnum.REPLY_QUESTION,question.getId());


            //评论数+1
            inCommentCount(question.getId());


        }


    }

    public void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationEnum notificationEnum,Long outerId) {
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());

        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);



        notificationMapper.insert(notification);
    }

    public void inCommentCount(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);

        Question updateQuestion = new Question();
        updateQuestion.setCommentCount(question.getCommentCount() + 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, example);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取评论人（去重）
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转化为map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换 comment ---> commentdto
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(userMap.get(comment.getCommentator()));
                    return commentDTO;
                }

        ).collect(Collectors.toList());

        return commentDTOs;
    }
}
