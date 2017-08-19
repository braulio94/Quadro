package com.braulio.cassule.designfocus.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braulio.cassule.designfocus.R;
import com.braulio.cassule.designfocus.model.TimeLineModel;
import com.braulio.cassule.designfocus.ui.TimeLineAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment {

    private RecyclerView mRecyclerView;
    TimeLineAdapter mTimeLineAdapter;
    List<TimeLineModel> mDataList = new ArrayList<>();

    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        initView();
        return view;
    }

    private void initView() {
        TimeLineModel model = new TimeLineModel();
        for(int i = 0;i <5;i++) {



            model.setDescription("Description"+i);
            mDataList.add(model);
        }
        /*
        for(int a= 0;a==1;a++){
            String[] titles = {"Education", "Experience", "Skills",  "Training", "Spoken Languages"};
            model.setTitle(titles[a]);
        }
        */
        mTimeLineAdapter = new TimeLineAdapter();
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

}
