package life.gao.community.controller;

import life.gao.community.dto.NotificationDTO;
import life.gao.community.dto.PaginationDTO;
import life.gao.community.enums.NotificationEnum;
import life.gao.community.mapper.NotificationMapper;
import life.gao.community.model.User;
import life.gao.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {


        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return  "redirect:/";
        }

     NotificationDTO notificationDTO= notificationService.read(id,user);
        if(NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType() ||
        NotificationEnum.REPLY_COMMENT.getType()==notificationDTO.getType()){

        return "redirect:/question/"+notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }






    }
}
