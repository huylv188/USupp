package uetsupport.dtui.uet.edu.uetsupport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import uetsupport.dtui.uet.edu.uetsupport.asynctask.AnnouncementAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.asynctask.LoginAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.asynctask.NewsAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.fragments.AnnounceFragment;
import uetsupport.dtui.uet.edu.uetsupport.fragments.NewsFragment;
import uetsupport.dtui.uet.edu.uetsupport.fragments.ProfileFragment;
import uetsupport.dtui.uet.edu.uetsupport.fragments.SupportFragment;
import uetsupport.dtui.uet.edu.uetsupport.models.News;
import uetsupport.dtui.uet.edu.uetsupport.models.User;
import uetsupport.dtui.uet.edu.uetsupport.receiver.NotificationEventReceiver;
import uetsupport.dtui.uet.edu.uetsupport.session.Session;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

public class MainActivity extends AppCompatActivity {

//    Toolbar toolbar;
    NewsFragment newsFragment;
    AnnounceFragment announceFragment;
    ProfileFragment profileFragment;
    SupportFragment supportFragment;
    ViewPager viewPager;
    FloatingActionMenu famTinTuc;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        newsFragment = new NewsFragment(this);
        announceFragment = new AnnounceFragment();
        profileFragment = new ProfileFragment(this);
        supportFragment = new SupportFragment();
        famTinTuc = (FloatingActionMenu)findViewById(R.id.famTinTuc);


        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_web_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_notifications_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_profile_fragment);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_help_outline_white_24dp);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        addFakeNewsData();
        checkUser();
//        loadNews();
        loadAnnouncement();
        loadExamSchedule();
        //tu dong cap nhat tin tuc
//        NotificationEventReceiver.setupAlarm(getApplicationContext());
        try {
            copyXmlToSP();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void addFakeNewsData() {
        News n = new News();
        newsFragment.newsArrayList.add(n);

        for(int i=1;i<8;i++){
            n = new News(getResources().getIdentifier("nt"+i,"string",getPackageName()), getResources().getIdentifier("thumbnail"+i,"mipmap",getPackageName()));
            newsFragment.newsArrayList.add(n);
        }

    }

    private void loadExamSchedule() {
        LoginAsyncTask lat = new LoginAsyncTask(this, "12020171");
        lat.execute();
    }

    public void copyXmlToSP() throws IOException {
        String name="FAQ.xml";
        String path = "/data/data/" + getPackageName()+"/shared_prefs/";  // Your application path
        File f = new File(path+name);
        (new File(path)).mkdirs();
        f.createNewFile();
        OutputStream myOutput = new FileOutputStream(path + name);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = getAssets().open(name);
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }



    private void loadAnnouncement() {
        AnnouncementAsyncTask aat = new AnnouncementAsyncTask(this,announceFragment,Util.CURRENT_PAGE_ANNOUNCEMENT);
        aat.execute();
    }

    private void loadNews() {
        NewsAsyncTask nat = new NewsAsyncTask(this,newsFragment,Util.CURRENT_PAGE_NEWS);
        nat.execute();
    }

    private void checkUser() {
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        String value = sp.getString("User", null);
        if(value!=null) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            User u = gson.fromJson(value, User.class);
            Session.setUser(u);
            Util.LOGGED_IN=true;
        }else{
            Util.LOGGED_IN=false;
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(newsFragment,R.mipmap.ic_web_white_24dp);
        adapter.addFrag(announceFragment,R.mipmap.ic_notifications_white_24dp);
        adapter.addFrag(profileFragment,R.mipmap.ic_profile_fragment);
        adapter.addFrag(supportFragment,R.mipmap.ic_help_outline_white_24dp);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    famTinTuc.showMenuButton(true);
                }else{
                    famTinTuc.hideMenuButton(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<Integer> mFragmentIconList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, int resId) {
            mFragmentList.add(fragment);
            mFragmentIconList.add(resId);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Log.e("cxz","sssssss");
                Intent i = new Intent(this,SettingActivity.class);
                startActivity(i);
                break;
        }
        return false;
    }
}
