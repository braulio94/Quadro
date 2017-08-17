package com.braulio.cassule.designfocus;

/**
 * Created by Braulio on 11/18/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipul.hp_hp.timelineview.TimelineView;

import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    String[] titles = {"Educacao", "Experiencia", "Habilidades",  "Cursos", "Linguas Faladas"};

    public TimeLineAdapter(List<TimeLineModel> feedList, Orientation orientation) {
        mFeedList = feedList;
        mOrientation = orientation;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view;
            view = View.inflate(parent.getContext(), R.layout.item_timeline, null);


        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);
        holder.title.setText(titles[position]);
        holder.description.setText( timeLineModel.getDescription());

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public  TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_view_title);
            description = (TextView) itemView.findViewById(R.id.text_view_description);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }
    }
}