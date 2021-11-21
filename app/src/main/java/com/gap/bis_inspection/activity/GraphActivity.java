package com.gap.bis_inspection.activity;

import static com.gap.bis_inspection.util.Config.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.activity.advert.AdvertActivity;
import com.gap.bis_inspection.activity.advert.SearchAdvertActivity;
import com.gap.bis_inspection.adapter.advert.GraphAdapter;
import com.gap.bis_inspection.app.AppController;
import com.gap.bis_inspection.common.CalendarUtil;
import com.gap.bis_inspection.common.CommonUtil;
import com.gap.bis_inspection.common.Constants;
import com.gap.bis_inspection.common.HejriUtil;
import com.gap.bis_inspection.db.manager.DatabaseManager;
import com.gap.bis_inspection.db.objectmodel.DeviceSetting;
import com.gap.bis_inspection.exception.WebServiceException;
import com.gap.bis_inspection.service.CoreService;
import com.gap.bis_inspection.service.Services;
import com.gap.bis_inspection.util.DateUtils;
import com.gap.bis_inspection.webservice.MyPostJsonService;
import com.gap.bis_inspection.widget.persiandatepicker.util.PersianCalendarUtils;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private ImageView img_right, img_left;
    private RelativeLayout backIcon;
    private ProgressBar processBar;
    private CoreService coreService;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph_recyclerView = findViewById(R.id.graph_recyclerView);
        txt_selectDate = findViewById(R.id.txt_selectDate);
        img_right = findViewById(R.id.img_right);
        img_left = findViewById(R.id.img_left);
        backIcon = findViewById(R.id.backIcon);
        processBar = findViewById(R.id.processBar);
        services = new Services(getApplicationContext());
        graph_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hejriUtil = new HejriUtil();
        databaseManager = new DatabaseManager(getApplicationContext());
        coreService = new CoreService(databaseManager);

        processBar.setVisibility(View.VISIBLE);
        String strDate = CalendarUtil.convertPersianDateTime(new Date(), "yyyy/MM/dd");
        txt_selectDate.setText(CommonUtil.latinNumberToPersian(strDate));


        getChartList(String.valueOf(new Date()));

        /*try {
            JSONObject resultJson = new JSONObject(services.getChartValue(String.valueOf(new Date())));
            if (!resultJson.isNull(Constants.RESULT_KEY)) {
                processBar.setVisibility(View.GONE);
                graph_recyclerView.setVisibility(View.VISIBLE);
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
*/
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

                               /* try {
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
                                }*/

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

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // graph_recyclerView.setAdapter(new GraphAdapter());
    }

    private void getChartList(String date) {

        class GetChartList extends AsyncTask<Void, Void, Void> {
            private String result;
            private String errorMsg;

            @SuppressLint("StringFormatInvalid")
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                processBar.setVisibility(View.GONE);
                graph_recyclerView.setVisibility(View.VISIBLE);
                if (result != null) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result);
                        if (!resultJson.isNull(Constants.RESULT_KEY)) {
                            processBar.setVisibility(View.GONE);
                            graph_recyclerView.setVisibility(View.VISIBLE);
                            JSONObject jsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                            if (!jsonObject.isNull("timeSeriesJsonArrayTest")) {
                                graphArray = new ArrayList<>();
                                JSONArray array = (JSONArray) jsonObject.getJSONArray("timeSeriesJsonArrayTest");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject1 = (JSONObject) array.get(i);
                                    graphArray.add(jsonObject1);
                                    graph_recyclerView.setAdapter(new GraphAdapter(graphArray, getApplicationContext()));
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                if (isDeviceDateTimeValid()) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        AppController application = (AppController) getApplication();
                        jsonObject.put("username", application.getCurrentUser().getUsername());
                        jsonObject.put("tokenPass", application.getCurrentUser().getBisPassword());
                        jsonObject.put("reportDate", date);
                        //jsonObject.put("carInfoType", carInfoType);
                        MyPostJsonService postJsonService = new MyPostJsonService(null, GraphActivity.this);
                        try {
                            result = postJsonService.sendData("getChartValueList", jsonObject, true);
                        } catch (SocketTimeoutException | SocketException e) {
                            errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                        } catch (WebServiceException e) {
                            Log.d("RegistrationFragment", e.getMessage());
                        }

                    } catch (JSONException e) {
                        Log.d("RegistrationFragment", e.getMessage());
                    }
                }
                return null;
            }


            ////******getServerDateTime*******////

            private boolean isDeviceDateTimeValid() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
                try {
                    JSONObject jsonObjectParam = new JSONObject();
                    MyPostJsonService postJsonService = new MyPostJsonService(null, GraphActivity.this);
                    result = postJsonService.sendData("getServerDateTime", jsonObjectParam, true);

                    if (result != null) {
                        JSONObject resultJson = new JSONObject(result);
                        if (!resultJson.isNull(Constants.SUCCESS_KEY)) {
                            JSONObject jsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                            Date serverDateTime = simpleDateFormat.parse(jsonObject.getString("serverDateTime"));
                            if (DateUtils.isValidDateDiff(new Date(), serverDateTime, Constants.VALID_SERVER_AND_DEVICE_TIME_DIFF)) {
                                DeviceSetting deviceSetting = coreService.getDeviceSettingByKey(Constants.DEVICE_SETTING_KEY_LAST_CHANGE_DATE);
                                if (deviceSetting == null) {
                                    deviceSetting = new DeviceSetting();
                                    deviceSetting.setKey(Constants.DEVICE_SETTING_KEY_LAST_CHANGE_DATE);
                                }
                                deviceSetting.setValue(simpleDateFormat.format(new Date()));
                                deviceSetting.setDateLastChange(new Date());
                                coreService.saveOrUpdateDeviceSetting(deviceSetting);
                                return true;
                            } else {
                                errorMsg = getResources().getString(R.string.Invalid_Device_Date_Time);
                            }
                        }
                    }
                } catch (SocketTimeoutException | SocketException e) {
                    errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                } catch (JSONException | ParseException | WebServiceException e) {
                    errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                    Log.d("SyncActivity", e.getMessage());
                }
                return false;
            }
        }

        new GetChartList().execute();
    }

}