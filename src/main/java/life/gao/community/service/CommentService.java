package life.gao.community.service;


import life.gao.community.enums.CommentTypeEnum;
import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import life.gao.community.mapper.CommentMapper;
import life.gao.community.mapper.QuestionMapper;
import life.gao.community.model.Comment;
import life.gao.community.model.Question;
import life.gao.community.model.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null ||comment.getParentId()==0){
        throw new CustomizeException(CustomizeErrorCode.Target_PARAM_NOT_FOUND);
        }

        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType()) ){
            throw  new CustomizeException(CustomizeErrorCode.Target_PARAM_NOT_FOUND);


        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){ // 枚举类中的comment成员
            //回复评论
            Comment dbComment=commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FIND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //评论数+1
            inCommentCount(question.getId());


        }



    }
        public void inCommentCount(Long id) {
            Question question=questionMapper.selectByPrimaryKey(id);

            Question updateQuestion = new Question();
            updateQuestion.setCommentCount(question.getCommentCount()+1);
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(id);
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
}
