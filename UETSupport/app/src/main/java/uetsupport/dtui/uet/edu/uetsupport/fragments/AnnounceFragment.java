package uetsupport.dtui.uet.edu.uetsupport.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.adapters.AnnouncementAdapter;
import uetsupport.dtui.uet.edu.uetsupport.asynctask.AnnouncementAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.asynctask.NewsAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.models.Announcement;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 08/12/2015.
 */
public class AnnounceFragment extends Fragment {

    public RecyclerView rvAnnouncement;
    public AnnouncementAdapter announcementAdapter;
    public ArrayList<Announcement> announcementArrayList;
    public ProgressBar pbAnnouncement;
    LinearLayoutManager llm;
    public SwipeRefreshLayout srlAnnouncement;

    /////////////////
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    ////////////////////////

    public AnnounceFragment(){
        announcementArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_annouce,container,false);


        pbAnnouncement = (ProgressBar)v.findViewById(R.id.pbAnnouncement);
        rvAnnouncement = (RecyclerView)v.findViewById(R.id.rvAnnouncement);
        rvAnnouncement.setHasFixedSize(true);
        srlAnnouncement = (SwipeRefreshLayout)v.findViewById(R.id.srlAnnouncement);
        srlAnnouncement.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        llm = new LinearLayoutManager(getActivity());
        rvAnnouncement.setLayoutManager(llm);

        announcementAdapter = new AnnouncementAdapter(getActivity(),announcementArrayList);
        rvAnnouncement.setAdapter(announcementAdapter);

        addListener();

        if(announcementArrayList.size()>0){
            srlAnnouncement.setVisibility(View.VISIBLE);
            pbAnnouncement.setVisibility(View.GONE);
        }else{
            srlAnnouncement.setVisibility(View.GONE);
            pbAnnouncement.setVisibility(View.VISIBLE);
        }
        return v;
    }

    private void addListener() {
        srlAnnouncement.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AnnouncementAsyncTask nat = new AnnouncementAsyncTask(getActivity(),AnnounceFragment.this,-1);
                nat.execute();
            }

        });

        rvAnnouncement.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvAnnouncement.getChildCount();
                totalItemCount = llm.getItemCount();
                firstVisibleItem = llm.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.e("cxz", "end called");

                    // Do something
                    AnnouncementAsyncTask nat = new AnnouncementAsyncTask(getActivity(),AnnounceFragment.this, Util.CURRENT_PAGE_ANNOUNCEMENT);
                    nat.execute();
                    loading = true;
                }
            }
        });
    }
}
