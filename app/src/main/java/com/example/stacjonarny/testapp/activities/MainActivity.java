package com.example.stacjonarny.testapp.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.stacjonarny.testapp.R;
import com.example.stacjonarny.testapp.adapters.RecyclerViewArticlesAdapter;
import com.example.stacjonarny.testapp.model.TestSingleton;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;

@EActivity
public class MainActivity extends AppCompatActivity {
    private final static String TAG="MAINACTIVITY";

    private RecyclerView mRecyclerViewArticles;
    private GridView mGridViewVideos;
    private RecyclerView.LayoutManager mRVLayoutManager;
    private static DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String drawerAdapterItems[] = {"Gallery","Weather","Articles","Notify","Logout"};
    @Bean
    TestSingleton testSingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testSingleton.setAsd(12);


        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        ImageView mNavHeaderImage = new ImageView(this);
        mNavHeaderImage.setImageResource(R.drawable.dixi_1);
        mNavHeaderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);


        int x = convertDptoPX(this,240);
        int y = convertDptoPX(this,60);

        createDrawerWithHeaderImage(mNavHeaderImage, x, y);



        mRecyclerViewArticles = (RecyclerView) findViewById(R.id.main_list_articles);
        mRecyclerViewArticles.setHasFixedSize(true); //more perf if size not changing dynamically
        mRVLayoutManager = new LinearLayoutManager(this); //Linear manager
        mRecyclerViewArticles.setLayoutManager(mRVLayoutManager);
        final RecyclerViewArticlesAdapter rvAdapter = new RecyclerViewArticlesAdapter();
        mRecyclerViewArticles.setAdapter(rvAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Data has been refreshed", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                rvAdapter.notifyDataSetChanged();



            }
        });

    }

    private void createDrawerWithHeaderImage(ImageView mNavHeaderImage, int x, int y) {
        mNavHeaderImage.setLayoutParams(new AbsListView.LayoutParams(x,y));
        mDrawerList.addHeaderView(mNavHeaderImage);
        ArrayAdapter drawerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,drawerAdapterItems);
        mDrawerList.setAdapter(drawerAdapter);
        setDrawerOnItemClickListener(mDrawerList,this);
    }

    private static void setDrawerOnItemClickListener(ListView list, final Activity a){

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = null;

                switch(position){
                    case 1:
                        mDrawerLayout.closeDrawers();
                        i=makeActivityIntent(a.getApplicationContext(),VideoGalleryActivity_.class);
                        a.startActivity(i);
                        break;
                    case 2:
                        mDrawerLayout.closeDrawers();
                        i=makeActivityIntent(a.getApplicationContext(),WeatherActivity.class);
                        a.startActivity(i);
                        break;
                    case 3:
                        mDrawerLayout.closeDrawers();
                        i=makeActivityIntent(a.getApplicationContext(),ScrollingActivity.class);
                        a.startActivity(i);
                        break;
                    case 4:
                        i = new Intent(a.getApplicationContext(), VideoDetailsActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(a.getApplicationContext(),(int)System.currentTimeMillis(),i,0);

                        Notification n = new Notification.Builder(a.getApplicationContext())
                                .setContentTitle("New thing")
                                .setContentText("This is some content")
                                .setAutoCancel(true)
                                .addAction(R.drawable.dixi_1,"Click",pendingIntent)
                                .setContentIntent(pendingIntent).build();

                        NotificationManager notificationManager = (NotificationManager) a.getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.notify(0,n);
                        break;
                    default:
                        Log.d(TAG, "DEFAULT NO ACTION DESIGNED");

                }
            }
        });
    }
    private static Intent makeActivityIntent(Context c, Class activityClass){
        Intent i = new Intent(c,activityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return i;
    }
    private int convertDptoPX(Context c, float dp){
        Resources r = c.getResources();
        dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());


        return Math.round(dp);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
