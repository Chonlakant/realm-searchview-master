package co.moonmonkeylabs.realmsearchview.example;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import co.moonmonkeylabs.realmsearchview.example.handler.ApiBus;
import co.moonmonkeylabs.realmsearchview.example.handler.ChatApiHandler;
import co.moonmonkeylabs.realmsearchview.example.handler.ChatApiService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class WOUApp extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = WOUApp.class
            .getSimpleName();
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MwnR2AAqrkmX1YkEIQE40fvFL";
    private static final String TWITTER_SECRET = "PbWgBifCFvchZ4AnZG3yBVsjRHnZGEBKcfXq6wNjXU7YUqpzrE";

    public static final String APP_ID = "1524384201124196";

    public static final String NOTI_ENDPOINT = "http://candychat.net/gcm/v1";
    public static final String SOCIAL_ENDPOINT = "http://candychat.net";
    public static final String CHAT_ENDPOINT = "http://chat.candychat.net";
    public static final String API_ENDPOINT = "http://api.candychat.net";

    public static Activity currentActivity;

    private static WOUApp Instance;
    public static volatile Handler applicationHandler = null;



    public Bitmap cropped = null;

    public static String USER_TOKEN;
    public static final String APP_PERMISSIONS = "email,public_profile,user_friends";
    private static OkHttpClient sHttpClient;
    private static Activity mFbHandleActivity;
    private static Context sContext = null;


    public static Typeface CustomFontTypeFace() {
        return Typeface.createFromAsset(getAppContext().getAssets(), "fonts/SWZ721BR.ttf");
    }

    private ChatApiHandler chatApiHandlerMain;

    String applicationID = "5UDvYSr2ngfrUVKo5G3cQUaaiTGakrIngAlXNhqC";
    String clientKey = "f0RqCB5EYYuTVoGghacM2ITIxWHST5iUipg5y6vs";

    public void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }



    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Kitkat and lower has a bug that can cause in correct strict mode
            // warnings about expected activity counts
            enableStrictMode();
        }


        sContext = this;
        Instance = this;

        applicationHandler = new Handler(getInstance().getMainLooper());

        chatApiHandlerMain = new ChatApiHandler(this, buildApiChatApiService(), ApiBus.getInstance());
        chatApiHandlerMain.registerForEvents();


    }

    public static ApiBus theBus = ApiBus.getInstance();;


    public static Context getAppContext() {
        return sContext;
    }


    public static OkHttpClient getHttpClient() {
        if (sHttpClient == null) {
            sHttpClient = new OkHttpClient();
            int cacheSize = 10 * 1024 * 1024;
            File cacheLocation = new File(StorageUtils.getIdealCacheDirectory(WOUApp.getAppContext()).toString());
            cacheLocation.mkdirs();
            com.squareup.okhttp.Cache cache = new com.squareup.okhttp.Cache(cacheLocation, cacheSize);
            sHttpClient.setCache(cache);
        }
        return sHttpClient;
    }



    ChatApiService buildApiChatApiService() {

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(CHAT_ENDPOINT)

                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                    }
                })
                .build()
                .create(ChatApiService.class);
    }


    public static void removeInstallation(int userId) {
//        final ParseInstallation installation = ParseInstallation
//                .getCurrentInstallation();
//
//        installation.deleteInBackground();
    }

    public static WOUApp get(Context context) {
        return (WOUApp) context.getApplicationContext();
    }

    public static WOUApp getInstance() {
        return Instance;
    }


    public static boolean applicationOnPause = false;

    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {
        currentActivity = arg0;
        Log.e("VMVMVM", "onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("VMVMVM", "onActivityDestroyed ");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        applicationOnPause = false;
        Log.e("VMVMVM", "onActivityResumed " + activity.getClass());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        applicationOnPause = true;
        Log.e("VMVMVM", "onActivityPaused " + activity.getClass());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("VMVMVM", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("VMVMVM", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("VMVMVM", "onActivityDestroyed ");
    }


}
