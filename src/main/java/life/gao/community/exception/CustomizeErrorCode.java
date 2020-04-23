package life.gao.community.exception;

import life.gao.community.model.Notification;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，换一个吧"),
    Target_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行恢复"),
    NO_LOGIN(2003,"未登录，请先登录再重试"),
    SYS_ERROR(2004, "服务错误啦，稍后再试"),
    COMMENT_TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FIND(2006,"该评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容为空"),
    READ_NOTIFICATION_FAIL(2008,"消息读取错误"),
    NOTIFICATION_NOT_FOUND(2009,"消息不见了"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败");



    private String message;
    private  Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.message=message;
        this.code=code;
    }
}
