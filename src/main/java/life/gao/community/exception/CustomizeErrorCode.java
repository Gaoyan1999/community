package life.gao.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题不在了，换一个吧");
    private String message;


    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message){
        this.message=message;
    }
}
