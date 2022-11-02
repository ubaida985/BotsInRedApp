package com.example.botsinred.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.database.Data;
import com.example.botsinred.models.ScheduleModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GraphFragment extends Fragment {


    private GraphView graph;
    private Data data;
    private ArrayList<ScheduleModel> schedules;
    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialization
        initialize();

        //adding listeners
        addListeners();

        //creating graph
        plotGraph();
    }

    private void plotGraph() {
        DataPoint[] data = new DataPoint[schedules.size()];
        for( int i = 0; i < schedules.size(); ++ i){
            //showMessage("Completed: "+schedules.get(i).isCompleted());
            data[i] = new DataPoint(i, schedules.get(i).isCompleted() ? 1 : 0);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);

        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);

        // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScalableY(true);

        // activate vertical scrolling
        graph.getViewport().setScrollableY(true);


    }

    private void addListeners() {
    }

    private void initialize() {
        graph = getView().findViewById(R.id.graph);
        data = new Data();
        schedules = data.getSchedule();
    }

    private void showMessage(String message) {
       // Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}