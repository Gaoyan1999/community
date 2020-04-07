package life.gao.community.mapper;

import life.gao.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create (Question question);



}
