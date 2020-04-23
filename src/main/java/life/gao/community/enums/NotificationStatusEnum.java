package life.gao.community.enums;

public enum NotificationStatusEnum {
    UREAD(0),Read(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
