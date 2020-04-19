package life.gao.community.dto;


import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import lombok.Data;

import javax.xml.transform.Result;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String  message;
    private T data;


    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);

        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(errorCode.getCode());
        resultDTO.setMessage(errorCode.getMessage());
        return resultDTO;
    }

    public  static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException ex) {

        return errorOf(ex.getCode(),ex.getMessage());
    }


    public static <T> ResultDTO okOf(T t){
            ResultDTO resultDTO=new ResultDTO();
            resultDTO.setCode(200);
            resultDTO.setMessage("请求成功");
            resultDTO.setData(t);
        return resultDTO;
    }
}
