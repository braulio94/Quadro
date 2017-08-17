package com.braulio.cassule.designfocus;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipul.hp_hp.timelineview.TimelineView;

import java.util.List;

/**
 * Created by Braulio on 11/18/2016.
 **/

 class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    List<TimeLineModel> mFeedList;
    private String[] titles = {"Educacao", "Experiencia", "Habilidades",  "Cursos", "Linguas Faladas"};

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLineViewHolder(View.inflate(parent.getContext(), R.layout.item_timeline, null), viewType);
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

     class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        TextView description;
        TimelineView mTimelineView;

        TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_view_title);
            description = (TextView) itemView.findViewById(R.id.text_view_description);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }
    }
}