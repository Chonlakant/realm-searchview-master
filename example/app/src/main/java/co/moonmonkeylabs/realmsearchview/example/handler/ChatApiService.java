package co.moonmonkeylabs.realmsearchview.example.handler;


import java.util.HashMap;

import co.moonmonkeylabs.realmsearchview.example.model.media.ChatHistory;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;


/**
 * Created by matthewlogan on 9/3/14.
 */
public interface ChatApiService {



    @GET("/api/chat/2/history")
    public  void getChatHistory(Callback<ChatHistory> responseJson);


}
