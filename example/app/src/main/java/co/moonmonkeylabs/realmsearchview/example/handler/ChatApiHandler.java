package co.moonmonkeylabs.realmsearchview.example.handler;

import android.content.Context;
import android.util.Log;


import com.squareup.otto.Subscribe;

import java.util.HashMap;

import co.moonmonkeylabs.realmsearchview.example.event.ContentInfoEvent;
import co.moonmonkeylabs.realmsearchview.example.event.ContentInfoSuccess;
import co.moonmonkeylabs.realmsearchview.example.model.media.ChatHistory;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ChatApiHandler {

    public Context context;
    private ChatApiService api;
    private ApiBus apiBus;

    public ChatApiHandler(Context context, ChatApiService api,
                          ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }


    @Subscribe
    public void onGetContentInfo(ContentInfoEvent event) {


        api.getChatHistory(new Callback<ChatHistory>() {
            @Override
            public void success(ChatHistory content, Response response) {
                if (content != null) {
                    Log.e("bbbbb",content.getContent().size()+"");
                    ApiBus.getInstance().postQueue(new ContentInfoSuccess(content));

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("COnten", error.getUrl());
                Log.e("COnten", error.getLocalizedMessage());
            }
        });
    }



}