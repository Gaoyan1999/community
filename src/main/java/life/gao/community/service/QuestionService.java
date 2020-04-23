package life.gao.community.service;

import life.gao.community.dto.PaginationDTO;
import life.gao.community.dto.QuestionDTO;
import life.gao.community.dto.QuestionQueryDTO;
import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import life.gao.community.mapper.QuestionExtMapper;
import life.gao.community.mapper.QuestionMapper;
import life.gao.community.mapper.UserMapper;
import life.gao.community.model.Question;
import life.gao.community.model.QuestionExample;
import life.gao.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;
    public PaginationDTO list(String search,Integer page, Integer size) {
        if(StringUtils.isNotBlank(search)){
            String[] tags=StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1) {
            page = 1;
        }
        if (page > totalPage ) {
            page = totalPage;
        }


        paginationDTO.setPagenation(totalPage, page);
        //size *(page-1)
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);

        List<Question> questions =questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOS = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        paginationDTO.setData(questionDTOS);


        return paginationDTO;

    }

    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int )questionMapper.countByExample(questionExample);


        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1) {
            page = 1;
        }
        if (page > totalPage ) {
            page = totalPage;
        }


        paginationDTO.setPagenation(totalPage, page);
        //size *(page-1)
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        example.setOrderByClause("gmt_create desc");


        List<Question> questions =questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        paginationDTO.setData(questionDTOS);


        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
         Question  question=questionMapper.selectByPrimaryKey(id);

         if(question ==null){
             throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
         }

         QuestionDTO questionDTO=new QuestionDTO();

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);


         BeanUtils.copyProperties(question,questionDTO);

         return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() ==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated !=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);

        Question updateQuestion = new Question();
        updateQuestion.setViewCount(question.getViewCount()+1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, example);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }

        String [] tags=StringUtils.split(queryDTO.getTag(),',');
        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question =new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());


        return questionDTOS;
    }
}
