package team.wucaipintu.pinyipin.bean;

import java.util.ArrayList;

public class PostDetail {
    public String title;//标题
    public String content;//内容
    public String datatime;//发布时间
    public String nikeName;//用户名
    public String deadline;//截至时间
    public String population;//目标人群
    public String location;//发布地点
    public int needNum;//需求人数
    public int numNow;//当前人数
    public int groupId;
    public ArrayList<Comment> comments;
}
