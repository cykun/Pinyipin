package team.wucaipintu.pinyipin.bean;

public class NewFriendRequest {
	
    private int headId;
    private String name;
    private int request;

    public NewFriendRequest(){}

    public NewFriendRequest(int headId,String name,int request){
        this.headId=headId;
        this.name=name;
        this.request=request;
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

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }
}
