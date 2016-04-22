package co.moonmonkeylabs.realmsearchview.example;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import co.moonmonkeylabs.realmsearchview.example.event.ContentInfoEvent;
import co.moonmonkeylabs.realmsearchview.example.event.ContentInfoSuccess;
import co.moonmonkeylabs.realmsearchview.example.handler.ActivityResultBus;
import co.moonmonkeylabs.realmsearchview.example.handler.ApiBus;
import co.moonmonkeylabs.realmsearchview.example.model.Blog;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private RealmSearchView realmSearchView;
    private BlogRecyclerViewAdapter adapter;
    private Realm realm;
    List<Blog> box = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ApiBus.getInstance().post(new ContentInfoEvent());
        resetRealm();
        //loadBlogData();

        realmSearchView = (RealmSearchView) findViewById(R.id.search_view);

        realm = Realm.getInstance(this);
        adapter = new BlogRecyclerViewAdapter(this, realm, "title");
        realmSearchView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private void loadBlogData() {
        SimpleDateFormat formatIn = new SimpleDateFormat("MMMM d, yyyy");
        SimpleDateFormat formatOut = new SimpleDateFormat("MM/d/yy");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();
        Random random = new Random();
        try {
            JsonParser jsonParserBlog = jsonFactory.createParser(getResources().openRawResource(R.raw.blog));
            List<Blog> entries = objectMapper.readValue(jsonParserBlog, new TypeReference<List<Blog>>() {});
            Log.e("cccc",entries.toString());
            JsonParser jsonParserEmoji =
                    jsonFactory.createParser(getResources().openRawResource(R.raw.emoji));
            List<String> emojies = objectMapper.readValue(jsonParserEmoji, new TypeReference<List<String>>() {});

            int numEmoji = emojies.size();
            for (Blog blog : entries) {
                blog.setEmoji(emojies.get(random.nextInt(numEmoji)));
                try {
                    blog.setDate(formatOut.format(formatIn.parse(blog.getDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Realm realm = Realm.getInstance(this);
            realm.beginTransaction();
            realm.copyToRealm(entries);
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            throw new IllegalStateException("Could not load blog data.");
        }
    }

    private void resetRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(realmConfig);
    }

    public class BlogRecyclerViewAdapter extends RealmSearchAdapter<Blog, BlogRecyclerViewAdapter.ViewHolder> {

        public BlogRecyclerViewAdapter(
                Context context,
                Realm realm,
                String filterColumnName) {
            super(context, realm, filterColumnName);
        }

        public class ViewHolder extends RealmSearchViewHolder {

            private BlogItemView blogItemView;

            public ViewHolder(FrameLayout container, TextView footerTextView) {
                super(container, footerTextView);
            }

            public ViewHolder(BlogItemView blogItemView) {
                super(blogItemView);
                this.blogItemView = blogItemView;
            }
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            ViewHolder vh = new ViewHolder(new BlogItemView(viewGroup.getContext()));
            return vh;
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
            final Blog blog = realmResults.get(position);
            viewHolder.blogItemView.bind(blog);
        }

        @Override
        public ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
            View v = inflater.inflate(R.layout.footer_view, viewGroup, false);
            return new ViewHolder(
                    (FrameLayout) v,
                    (TextView) v.findViewById(R.id.footer_text_view));
        }

        @Override
        public void onBindFooterViewHolder(ViewHolder holder, int position) {
            super.onBindFooterViewHolder(holder, position);
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }
            );
        }
    }

    @Subscribe
    public void getListPhoto(ContentInfoSuccess event) {
        if (event != null) {
            Log.e("qqqq", event.response.getContent().size() + "");
            for(int i =0 ; i <event.response.getContent().size();i++){
                Blog ok = new Blog();
                ok.setTitle(event.response.getContent().get(i).getMessage());
                ok.setContent(event.response.getContent().get(i).getMessage());
                ok.setDate("111");
                box.add(ok);
                Realm realm = Realm.getInstance(this);
                realm.beginTransaction();
                realm.copyToRealm(box);
                realm.commitTransaction();
                realm.close();
            }




        }

    }
}
