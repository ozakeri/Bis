package com.gap.bis_inspection.adapter.advert;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.util.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraphAdapter extends RecyclerView.Adapter<GraphAdapter.CustomViewHolder> {

    private List<JSONObject> jsonObjectList;
    private BarData barData;

    public GraphAdapter(List<JSONObject> jsonObjectList) {
        this.jsonObjectList = jsonObjectList;
    }

    public GraphAdapter() {
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_items, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        JSONObject json = jsonObjectList.get(position);

        try {

            if (!json.isNull("countLabel")) {
                holder.countLabel.setText(" تعداد کل " + " : " + json.getString("countLabel"));
            }

            if (!json.isNull("titlelabel")) {
                holder.titlelabel.setText(json.getString("titlelabel"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (!json.isNull("chartStr")) {
            String chartStr = null;
            String str_0 = null;
            int str_1 = 0;
            try {
                chartStr = json.getString("chartStr");
                JSONObject chartStrJsonObject = new JSONObject(chartStr);
                List<BarEntry> entries = new ArrayList<BarEntry>();
                List<String> xValues = new ArrayList<String>();
                if (!chartStrJsonObject.isNull("value")) {
                    JSONArray valueJSONArray = (JSONArray) chartStrJsonObject.getJSONArray("value");
                    System.out.println("valueJSONArray=====" + valueJSONArray.length());
                    for (int j = 1; j < valueJSONArray.length(); j++) {
                        System.out.println("valueJSONArray=====" + valueJSONArray);
                        JSONArray valueObjectJSONArray = (JSONArray) valueJSONArray.get(j);
                        str_0 = (String) valueObjectJSONArray.get(0);
                        str_1 = (int) valueObjectJSONArray.get(1);
                        String str_2 = (String) valueObjectJSONArray.get(2);
                        entries.add(new BarEntry(str_1, j - 1));
                        xValues.add(str_0);

                    }

                    BarDataSet dataSet = new BarDataSet(entries, "Label");
                    dataSet.setColor(Color.rgb(0, 155, 0));
                    dataSet.setValueTextColor(Color.rgb(0, 155, 0));
                    dataSet.setValueFormatter(new MyValueFormatter());

                    barData = new BarData(xValues, dataSet);
                    holder.chart.setData(barData);
                    holder.chart.setDescription("");
                    holder.chart.invalidate();
                    holder.chart.animateXY(1000, 1000);
                    holder.chart.setDrawBarShadow(false);
                    holder.chart.setDrawValueAboveBar(true);
                    holder.chart.setPinchZoom(true);
                    holder.chart.setDrawGridBackground(false);
                    holder.chart.getAxisRight().setEnabled(false);
                    holder.chart.getAxisLeft().setEnabled(false);
                    holder.chart.enableScroll();
                    holder.chart.setHorizontalScrollBarEnabled(true);
                    XAxis xl = holder.chart.getXAxis();
                    xl.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xl.setDrawLabels(true);
                    xl.setDrawAxisLine(true);
                    xl.setDrawGridLines(false);
                    xl.setSpaceBetweenLabels(0);

                    Legend l = holder.chart.getLegend();
                    l.setEnabled(false);
                    // l.setFormSize(8f);
                    l.setXEntrySpace(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public int getItemCount() {
        return jsonObjectList.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        private BarChart chart;
        private TextView titlelabel, countLabel;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            chart = itemView.findViewById(R.id.chart);
            titlelabel = itemView.findViewById(R.id.titlelabel);
            countLabel = itemView.findViewById(R.id.countLabel);
        }
    }
}
