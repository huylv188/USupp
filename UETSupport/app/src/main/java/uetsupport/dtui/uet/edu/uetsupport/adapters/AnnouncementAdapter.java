package uetsupport.dtui.uet.edu.uetsupport.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.AnnouncementActivity;
import uetsupport.dtui.uet.edu.uetsupport.NewsActivity;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.models.Announcement;

/**
 * Created by huylv on 08/12/2015.
 */
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private ArrayList<Announcement> announcementArrayList;
    Context context;

    public AnnouncementAdapter(Context c,ArrayList<Announcement> announcementArrayList){
        context=c;
        this.announcementArrayList =announcementArrayList;
    }

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annoucement, parent, false);
        AnnouncementViewHolder avh = new AnnouncementViewHolder(v);

        return avh;
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder holder, final int position) {
        holder.tvItemAnnouncementTitle.setText(announcementArrayList.get(position).getAnnouncementTitle());
        holder.tvItemAnnouncementDescription.setText(announcementArrayList.get(position).getAnnouncementDesc());
        holder.llAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AnnouncementActivity.class);
                i.putExtra("url",announcementArrayList.get(position).getAnnouncementLink());
                i.putExtra("title",announcementArrayList.get(position).getAnnouncementTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return announcementArrayList.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llAnnouncement;
        TextView tvItemAnnouncementTitle;
        TextView tvItemAnnouncementDescription;

        public AnnouncementViewHolder(View itemView) {
            super(itemView);
            llAnnouncement = (LinearLayout)itemView.findViewById(R.id.llAnnouncement);
            tvItemAnnouncementTitle = (TextView)itemView.findViewById(R.id.tvItemAnnouncementTitle);
            tvItemAnnouncementDescription = (TextView)itemView.findViewById(R.id.tvItemAnnouncementDescription);

        }
    }

}
