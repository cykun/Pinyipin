package team.wucaipintu.pinyipin.bean;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {
    private String id;
    private String text;
    private Author author;
    private Date createdAt;

    public Message(String id,String text,Author author,Date createAt){
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
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}
