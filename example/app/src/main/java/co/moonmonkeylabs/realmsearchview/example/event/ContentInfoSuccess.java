package co.moonmonkeylabs.realmsearchview.example.event;


import co.moonmonkeylabs.realmsearchview.example.model.media.ChatHistory;

/**
 * Created by Mac on 8/6/15.
 */
public class ContentInfoSuccess {
    public ChatHistory response;

    public ContentInfoSuccess(ChatHistory response) {
        this.response = response;
    }
}
