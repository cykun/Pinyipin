package team.wucaipintu.pinyipin.bean;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {
    private String id;
    private String text;
    private User author;
    private Date createdAt;

    public Message(String id, String text, User author, Date createAt){
        this.id=id;
        this.text=text;
        this.author=author;
        this.createdAt=createAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public User getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}
