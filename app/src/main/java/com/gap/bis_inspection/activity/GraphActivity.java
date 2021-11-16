package com.gap.bis_inspection.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.common.Constants;
import com.gap.bis_inspection.service.Services;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphActivity extends AppCompatActivity {

    private RecyclerView graph_recyclerView;
    private Services services;
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph_recyclerView = findViewById(R.id.graph_recyclerView);
        services = new Services(getApplicationContext());
        graph_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        System.out.println("getChartValue=====" + services.getChartValue());

        try {
            JSONObject resultJson = new JSONObject(services.getChartValue());
            if (!resultJson.isNull(Constants.RESULT_KEY)) {
                JSONObject jsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                if (!jsonObject.isNull("timeSeriesJsonArrayTest")) {
                    JSONArray array = (JSONArray) jsonObject.getJSONArray("timeSeriesJsonArrayTest");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) array.get(i);
                        if (!jsonObject1.isNull("chartStr")) {
                            JSONObject chartStrJsonObject = jsonObject1.getJSONObject("chartStr");
                            if (!chartStrJsonObject.isNull("value")) {
                                JSONArray valueJSONArray = (JSONArray) chartStrJsonObject.getJSONArray("value");
                                System.out.println("chartStrJSONArray=====" + valueJSONArray.length());
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // graph_recyclerView.setAdapter(new GraphAdapter());
    }
}