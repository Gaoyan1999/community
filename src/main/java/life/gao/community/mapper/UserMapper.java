package life.gao.community.mapper;


import life.gao.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
   // @Select("SELECT * FROM CITY WHERE state = #{state}")
   // City findByState(@Param("state") String state);
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) " +
            " values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);


}
