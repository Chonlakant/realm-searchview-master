package co.moonmonkeylabs.realmsearchview.example.event;


import co.moonmonkeylabs.realmsearchview.example.model.media.ChatHistory;
import co.moonmonkeylabs.realmsearchview.example.model.media.ChatHistoryNew;
import co.moonmonkeylabs.realmsearchview.example.model.media.TheMessageObject;

/**
 * Created by Mac on 8/6/15.
 */
public class ContentInfoSuccess {
    public ChatHistory response;

    public ContentInfoSuccess(ChatHistory response) {
        this.response = response;
    }
}
