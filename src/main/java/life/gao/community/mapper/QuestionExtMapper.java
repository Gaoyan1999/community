package life.gao.community.mapper;

import life.gao.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int inCommentCount(Question record);
}
