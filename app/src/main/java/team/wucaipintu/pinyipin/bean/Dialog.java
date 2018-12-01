package team.wucaipintu.pinyipin.bean;

public class Dialog {
    private int headId;
    private String name;
    private String content;
    private String datatime;

    public Dialog(){}

    public Dialog(int headId, String name, String content, String datatime){
        this.headId=headId;
        this.name=name;
        this.content=content;
        this.datatime=datatime;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
