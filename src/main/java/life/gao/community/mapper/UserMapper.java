package life.gao.community.mapper;


import life.gao.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
   // @Select("SELECT * FROM CITY WHERE state = #{state}")
   // City findByState(@Param("state") String state);
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) " +
            " values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
