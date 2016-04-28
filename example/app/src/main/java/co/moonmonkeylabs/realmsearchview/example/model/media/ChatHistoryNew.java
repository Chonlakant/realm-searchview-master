package co.moonmonkeylabs.realmsearchview.example.model.media;

import io.realm.RealmObject;

/**
 * Created by root1 on 4/28/16.
 */
public class ChatHistoryNew extends RealmObject {


    /**
     * id : 641
     * conversationId : 2
     * senderId : 3
     * message : uvuguvvvvv่ขน่นืนิิิ
     * messageType : 0
     */

    private int id;
    private int conversationId;
    private int senderId;
    private String message;
    private int messageType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
