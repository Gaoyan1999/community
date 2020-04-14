package life.gao.community.advice;


import com.alibaba.fastjson.JSON;
import life.gao.community.dto.ResultDTO;
import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            //返回json
            if (ex instanceof CustomizeException) {
                resultDTO=ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO=ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
            PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        return  null;
        } else {
            //页面跳转

            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {

                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }

        }

            return new ModelAndView("error");
    }


}
