package life.gao.community.controller;

import life.gao.community.dto.FileDTO;
import life.gao.community.provider.AliyunProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {

    @Autowired
    private  AliyunProvider aliyunProvider;



    @RequestMapping("file/upload")
    @ResponseBody
        public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");


            String url = aliyunProvider.upload(file, "imag");

            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);


            fileDTO.setUrl(url);


            return fileDTO;
        }


}
