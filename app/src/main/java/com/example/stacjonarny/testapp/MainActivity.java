package com.example.stacjonarny.testapp;

import android.app.Activity;
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

public class MainActivity extends AppCompatActivity {
    private final static String TAG="MAINACTIVITY";

    private RecyclerView mRecyclerViewArticles;
    private GridView mGridViewVideos;
    private RecyclerView.LayoutManager mRVLayoutManager;
    private static DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private String drawerAdapterItems[] = {"Gallery","Weather","Articles","Login","Logout"};
    private String webUrl = "https://www.joemonster.org";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//LOAD DATA has to be in async task because of being in main thread
//        trying to parse data from web ! USELESS
//        Elements elements = null;
//        Document doc = null;
//        try {
//            doc = new AsyncTaskGetArticles(doc).execute("http://joemonster.org/art/37436/").get();
////            elements = doc.select("a[href~=\\/art\\/[0-9]+\\/]");
//            elements = doc.select(".art-wrapper > h1");
//            String title = Jsoup.parse(elements.html()).toString();
//            System.out.println("title of the page = "+title);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        ImageView mNavHeaderImage = new ImageView(this);
        mNavHeaderImage.setImageResource(R.drawable.dixi_1);
        mNavHeaderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);


        int x = convertDptoPX(this,240);
        int y = convertDptoPX(this,60);

//        mNavHeaderImage.setLayoutParams(new ViewGroup.LayoutParams(x,y));//CHUJOSTWO NIE DZIALA

        createDrawerWithHeaderImage(mNavHeaderImage, x, y);

//        mGridViewVideos = (GridView) findViewById(R.id.main_grid_videos);
//        mGridViewVideos.setAdapter(new VideoGridAdapter(this));

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

//        Spinner spinner = (Spinner) findViewById(R.id.spinner);   //old spinner
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.items, android.R.layout.simple_spinner_item);
//
//        spinner.setAdapter(adapter);
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
                        i=makeActivityIntent(a.getApplicationContext(),VideoGalleryActivity.class);
                        a.startActivity(i);
                        break;
                    case 2:
                        mDrawerLayout.closeDrawers();
                        i=makeActivityIntent(a.getApplicationContext(),WeatherActivity.class);
                        a.startActivity(i);
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
