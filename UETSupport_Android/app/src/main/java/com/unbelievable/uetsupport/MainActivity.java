package com.unbelievable.uetsupport;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gcm.GCMRegistrar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.unbelievable.uetsupport.common.SlidingTabLayout;
import com.unbelievable.uetsupport.objects.Department;
import com.unbelievable.uetsupport.objects.News;
import com.unbelievable.uetsupport.objects.Recruitment;
import com.unbelievable.uetsupport.objects.Teacher;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private ViewPager pagers;
    private com.unbelievable.uetsupport.adapter.PagerAdapter pagerAdapter;
    private SlidingTabLayout tabs;
    public ArrayList<News> newsArrayList;
    public ArrayList<Teacher> teacherArrayList;
    public ArrayList<Department> departmentArrayList;
    public ArrayList<String> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        setContentView(R.layout.activity_main);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        pagers = (ViewPager) findViewById(R.id.pagers);
        pagerAdapter = new com.unbelievable.uetsupport.adapter.PagerAdapter(getSupportFragmentManager(), this);
        pagers.setAdapter(pagerAdapter);
        newsArrayList = new ArrayList<>();
        teacherArrayList = new ArrayList<>();
        departmentArrayList = new ArrayList<>();
        listDataChild = new ArrayList<>();
        registerClient();
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setCustomTabView(R.layout.custom_tab_view, 0);
        tabs.setViewPager(pagers);
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

    /**
     * This registerClient() method checks the current device, checks the
     * manifest for the appropriate rights, and then retrieves a registration id
     * from the GCM cloud. If there is no registration id, GCMRegistrar will
     * register this device for the specified project, which will return a
     */
    public void registerClient() {
        try {
            // Check that the device support GCM (should be in a try/catch)
            GCMRegistrar.checkDevice(this);

            // Check the manifest to be sure this app has all the required
            // permissions.
            GCMRegistrar.checkManifest(this);

            // Get the existing registration id, if it exists.
            String regId = GCMRegistrar.getRegistrationId(this);
            if (regId.equals("")) {
                GCMRegistrar.register(this, "610765904940");
                regId = GCMRegistrar.getRegistrationId(this);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
