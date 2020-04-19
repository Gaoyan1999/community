package life.gao.community.cache;

import life.gao.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js", "php", "css", "html", "java", "node"));
        tagDTOs.add(program);


        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring", "laravel", "django", "ruby-on-rails"));
        tagDTOs.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "apache", "unix", "tomcat"));
        tagDTOs.add(server);

        TagDTO dataBase = new TagDTO();
        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("mysql", "redis", "mongodb", "oracle"));
        tagDTOs.add(dataBase);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "maven", "ide"));
        tagDTOs.add(tool);


        return tagDTOs;
    }


    public static String  filterInvalid(String tags) {
        String[] splits = StringUtils.split(tags, ',');
        List<TagDTO> tagDTOS = get();
        //获得二维数组中的所有tag
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(splits).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));


        return invalid;


    }
}




