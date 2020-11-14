package com.example.myapp.view.Fragment.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.ThingSpeakLineChart;

import java.util.Calendar;

import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartFragment extends Fragment {
    View view;
    private ThingSpeakChannel tsChannel1;
    private ThingSpeakLineChart tsChart1,tsChart2,tsChart3;
    private LineChartView chartView1,chartView2,chartView3;

    public ChartFragment() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chart, container, false);


        tsChannel1 = new ThingSpeakChannel(1065872,"XV3PSSKDE9TWINUK");
        tsChannel1.loadChannelFeed();




        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);


        chartView1 = view.findViewById(R.id.chart);
        chartView2 = view.findViewById(R.id.chart2);
        chartView3 = view.findViewById(R.id.chart3);


        chartView1.setZoomEnabled(true);
        chartView1.setValueSelectionEnabled(true);


        tsChart1 = new ThingSpeakLineChart(1065872,1, "XV3PSSKDE9TWINUK");

        tsChart1.setNumberOfEntries(50);

        tsChart1.setValueAxisLabelInterval(2);

        tsChart1.setDateAxisLabelInterval(1);

        tsChart1.useSpline(true);

        tsChart1.setLineColor(Color.parseColor("#D32F2F"));

        tsChart1.setAxisColor(Color.parseColor("#455a64"));

        tsChart1.setChartStartDate(calendar.getTime());

        tsChart1.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {

                chartView1.setLineChartData(lineChartData);

                chartView1.setMaximumViewport(maxViewport);

            }
        });

        tsChart1.loadChartData();

        chartView2.setZoomEnabled(true);
        chartView2.setValueSelectionEnabled(true);


        tsChart2 = new ThingSpeakLineChart(1065872, 2,"XV3PSSKDE9TWINUK");

        tsChart2.setNumberOfEntries(50);

        tsChart2.setValueAxisLabelInterval(2);

        tsChart2.setDateAxisLabelInterval(1);

        tsChart2.useSpline(true);

        tsChart2.setLineColor(Color.parseColor("#166EE5"));

        tsChart2.setAxisColor(Color.parseColor("#455a64"));

        tsChart2.setChartStartDate(calendar.getTime());

        tsChart2.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {

                chartView2.setLineChartData(lineChartData);

                chartView2.setMaximumViewport(maxViewport);

                chartView2.setCurrentViewport(initialViewport);


            }
        });

        tsChart2.loadChartData();

        chartView3.setZoomEnabled(true);
        chartView3.setValueSelectionEnabled(true);


        tsChart3 = new ThingSpeakLineChart(1065872, 3,"XV3PSSKDE9TWINUK");

        tsChart3.setNumberOfEntries(50);

        tsChart3.setValueAxisLabelInterval(2);

        tsChart3.setDateAxisLabelInterval(1);

        tsChart3.useSpline(true);

        tsChart3.setLineColor(Color.parseColor("#7E412E"));

        tsChart3.setAxisColor(Color.parseColor("#455a64"));

        tsChart3.setChartStartDate(calendar.getTime());

        tsChart3.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {

                chartView3.setLineChartData(lineChartData);

                chartView3.setMaximumViewport(maxViewport);

                chartView3.setCurrentViewport(initialViewport);


            }
        });

        tsChart3.loadChartData();

        return view;
    }
}
