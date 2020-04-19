package life.gao.community.controller;


import life.gao.community.dto.CommentDTO;
import life.gao.community.dto.QuestionDTO;
import life.gao.community.enums.CommentTypeEnum;
import life.gao.community.model.Question;
import life.gao.community.service.CommentService;
import life.gao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

        @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id,
                           Model model){

     QuestionDTO questionDTO= questionService.getById(id);
            List<QuestionDTO> relatedQuestions=questionService.selectRelated(questionDTO);
     //添加阅读数
     questionService.incView(id);
            List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

            model.addAttribute("question",questionDTO);
            model.addAttribute("comments",comments);
            model.addAttribute("relateQuestions",relatedQuestions);
        return "question";
    }
}
