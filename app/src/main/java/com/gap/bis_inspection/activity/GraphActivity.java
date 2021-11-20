package com.gap.bis_inspection.activity;

import static com.gap.bis_inspection.util.Config.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.adapter.advert.GraphAdapter;
import com.gap.bis_inspection.common.CalendarUtil;
import com.gap.bis_inspection.common.CommonUtil;
import com.gap.bis_inspection.common.Constants;
import com.gap.bis_inspection.common.HejriUtil;
import com.gap.bis_inspection.service.Services;
import com.gap.bis_inspection.widget.persiandatepicker.util.PersianCalendarUtils;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;

public class GraphActivity extends AppCompatActivity {

    private RecyclerView graph_recyclerView;
    private Services services;
    private BarChart chart;
    private List<JSONObject> graphArray;
    private TextView txt_selectDate;
    private PersianDatePickerDialog persianDatePickerDialog;
    private HejriUtil hejriUtil;
    private ImageView img_right,img_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph_recyclerView = findViewById(R.id.graph_recyclerView);
        txt_selectDate = findViewById(R.id.txt_selectDate);
        img_right = findViewById(R.id.img_right);
        img_left = findViewById(R.id.img_left);
        services = new Services(getApplicationContext());
        graph_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hejriUtil = new HejriUtil();

        String strDate = CalendarUtil.convertPersianDateTime(new Date(), "yyyy/MM/dd");
        txt_selectDate.setText(CommonUtil.latinNumberToPersian(strDate));

        try {
            JSONObject resultJson = new JSONObject(services.getChartValue(String.valueOf(new Date())));
            if (!resultJson.isNull(Constants.RESULT_KEY)) {
                JSONObject jsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                if (!jsonObject.isNull("timeSeriesJsonArrayTest")) {
                    graphArray = new ArrayList<>();
                    JSONArray array = (JSONArray) jsonObject.getJSONArray("timeSeriesJsonArrayTest");
                    for (int i = 0; i < array.length(); i++) {
                        System.out.println("timeSeriesJsonArrayTest====" + i);
                        JSONObject jsonObject1 = (JSONObject) array.get(i);
                        graphArray.add(jsonObject1);
                        graph_recyclerView.setAdapter(new GraphAdapter(graphArray));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        txt_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persianDatePickerDialog = new PersianDatePickerDialog(GraphActivity.this)
                        .setPositiveButtonString("تایید")
                        .setNegativeButton("بستن")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1400)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                        .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                        .setInitDate(hejriUtil.getYear(), hejriUtil.getMonth(), hejriUtil.getDay())
                        .setActionTextColor(Color.GRAY)
                        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                        .setShowInBottomSheet(true)
                        .setListener(new PersianPickerListener() {
                            @Override
                            public void onDateSelected(PersianPickerDate persianPickerDate) {
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                                Log.d(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                                Toast.makeText(GraphActivity.this, persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay(), Toast.LENGTH_SHORT).show();
                                txt_selectDate.setText(CommonUtil.latinNumberToPersian(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay()));
                                System.out.println(persianPickerDate.getGregorianDate());

                                try {
                                    JSONObject resultJson = new JSONObject(services.getChartValue(String.valueOf(persianPickerDate.getGregorianDate())));
                                    if (!resultJson.isNull(Constants.RESULT_KEY)) {
                                        JSONObject jsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                                        if (!jsonObject.isNull("timeSeriesJsonArrayTest")) {
                                            graphArray = new ArrayList<>();
                                            JSONArray array = (JSONArray) jsonObject.getJSONArray("timeSeriesJsonArrayTest");
                                            for (int i = 0; i < array.length(); i++) {
                                                System.out.println("timeSeriesJsonArrayTest====" + i);
                                                JSONObject jsonObject1 = (JSONObject) array.get(i);
                                                graphArray.add(jsonObject1);
                                                graph_recyclerView.setAdapter(new GraphAdapter(graphArray));
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                persianDatePickerDialog.show();
            }
        });


        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // graph_recyclerView.setAdapter(new GraphAdapter());
    }
}