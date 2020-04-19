package life.gao.community.mapper;

import life.gao.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int inCommentCount(Question record);
    List<Question> selectRelated(Question question);
}
