package com.gap.bis_inspection.fragment.driver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.activity.driver.DriverComplaintDetailActivity;
import com.gap.bis_inspection.activity.driver.DriverChartComplaintActivity;
import com.gap.bis_inspection.activity.report.ReportActivity;
import com.gap.bis_inspection.adapter.ComplaintAdapter;
import com.gap.bis_inspection.app.AppController;
import com.gap.bis_inspection.common.CalendarUtil;
import com.gap.bis_inspection.common.CommonUtil;
import com.gap.bis_inspection.common.Constants;
import com.gap.bis_inspection.common.Data;
import com.gap.bis_inspection.common.HejriUtil;
import com.gap.bis_inspection.db.enumtype.EntityNameEn;
import com.gap.bis_inspection.db.manager.DatabaseManager;
import com.gap.bis_inspection.db.manager.IDatabaseManager;
import com.gap.bis_inspection.db.objectmodel.DeviceSetting;
import com.gap.bis_inspection.exception.WebServiceException;

import com.gap.bis_inspection.widget.persiandatepicker.PersianDatePicker;
import com.gap.bis_inspection.service.CoreService;
import com.gap.bis_inspection.util.DateUtils;
import com.gap.bis_inspection.util.PersianDate;
import com.gap.bis_inspection.webservice.MyPostJsonService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.view.View.VISIBLE;

/**
 * Created by Mohamad Cheraghi on 07/05/2016.
 */
public class DriverComplaintFragment extends Fragment {
    private Spinner spinner;
    EditText startET, endET, dailyET;
    RelativeLayout layoutDate, layoutSpinner, linearLayout, layoutDaily, layoutAddIcon, layoutOptionText;
    LinearLayout layoutMonthly, rel, layoutCounter, layoutReportStatistical;
    private IDatabaseManager databaseManager;
    private CoreService coreService;
    ProgressDialog progressBar;
    String code, driverId;
    String strFromDate, strToDate;
    TextView driverNameTV, selectSpinnerDateTV, selectSpinnerDate1TV, counterTV;
    ListView listView;
    ImageView backIcon, searchIcon,statistical;
    RadioGroup ComplainteReportTypeOP;
    Date fromDate = null;
    Date toDate = null;
    String selectedMonth = null;
    String selectedYear = null;
    ASync myTask = null;
    String displayName = null;
    String addIcon1 = "driver";
    String jsonStr = "driverId";

    public DriverComplaintFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_complaint, container, false);
        init(view);
        Spinner yearSpinner = (Spinner) view.findViewById(R.id.spinner_year);
        Spinner monthSpinner = (Spinner) view.findViewById(R.id.spinner_month);
        getDriverId();

        PersianDate date = new PersianDate();
        dailyET.setText(date.todayShamsi());

        List<Integer> categories = CommonUtil.easyIntegerList(1, 2, 3);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        HejriUtil hejriUtil = new HejriUtil();
        hejriUtil.decodeHejriDate(new Date());
        Integer currentMonth = hejriUtil.getMonth();
        Integer currentYear = hejriUtil.getYear();
        final List<Data> monthDataList = CommonUtil.getMonthDataList(getActivity());
        ArrayAdapter<Data> adapter_month = new ArrayAdapter<Data>(getActivity(), android.R.layout.simple_spinner_item, monthDataList);
        monthSpinner.setAdapter(adapter_month);
        monthSpinner.setSelection(currentMonth - 1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        Data d = monthDataList.get(position);
                        selectedMonth = d.getKey();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        final List<Integer> yearList = CommonUtil.getNYearBeforeList(currentYear, 5);
        ArrayAdapter<Integer> adapter_year = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, yearList);
        yearSpinner.setAdapter(adapter_year);
        yearSpinner.setSelection(4);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        Integer d = yearList.get(position);
                        selectedYear = d.toString();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );


        statistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDriverId();
                Intent intent = new Intent(getActivity(), DriverChartComplaintActivity.class);
                intent.putExtra("Id", driverId);
                intent.putExtra("Code", code);
                intent.putExtra("jsonStr", jsonStr);
                intent.putExtra("complaintLabel", getActivity().getResources().getString(R.string.driver_label_violation));
                //intent.putExtra("driver_label", getActivity().getResources().getString(R.string.driver_label));
                startActivity(intent);
            }
        });

        ComplainteReportTypeOP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.incident_report_type_multi_month) {
                    linearLayout.setVisibility(VISIBLE);
                    layoutSpinner.setVisibility(VISIBLE);
                    layoutDate.setVisibility(View.INVISIBLE);
                    layoutMonthly.setVisibility(View.INVISIBLE);
                    layoutDaily.setVisibility(View.INVISIBLE);

                } else if (i == R.id.incident_report_type_periodic) {
                    linearLayout.setVisibility(VISIBLE);
                    layoutSpinner.setVisibility(View.INVISIBLE);
                    layoutDate.setVisibility(VISIBLE);
                    layoutMonthly.setVisibility(View.INVISIBLE);
                    layoutDaily.setVisibility(View.INVISIBLE);


                } else if (i == R.id.incident_report_type_daily) {
                    linearLayout.setVisibility(VISIBLE);
                    layoutDaily.setVisibility(VISIBLE);
                    layoutDate.setVisibility(View.INVISIBLE);
                    layoutSpinner.setVisibility(View.INVISIBLE);
                    layoutMonthly.setVisibility(View.INVISIBLE);

                } else if (i == R.id.incident_report_type_monthly) {
                    linearLayout.setVisibility(VISIBLE);
                    layoutMonthly.setVisibility(VISIBLE);
                    layoutDate.setVisibility(View.INVISIBLE);
                    layoutSpinner.setVisibility(View.INVISIBLE);
                    layoutDaily.setVisibility(View.INVISIBLE);
                }
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.motion, R.anim.motion2);
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutOptionText.setVisibility(View.GONE);
                layoutReportStatistical.setVisibility(VISIBLE);
                fromDate = null;
                toDate = null;
                int selectedIncidentType = ComplainteReportTypeOP.getCheckedRadioButtonId();
                if (selectedIncidentType == R.id.incident_report_type_monthly) {
                    String selectedDate = selectedYear + "/" + selectedMonth + "/01";
                    fromDate = HejriUtil.hejriToChris(selectedDate);
                    HejriUtil currentHejriUtil = new HejriUtil(new Date());
                    if (Integer.valueOf(selectedYear).equals(currentHejriUtil.getYear()) && Integer.valueOf(selectedMonth).equals(currentHejriUtil.getMonth())) {
                        toDate = CalendarUtil.midnight(new Date());
                    } else {
                        toDate = HejriUtil.add(fromDate, Calendar.MONTH, 1);
                    }

                } else if (selectedIncidentType == R.id.incident_report_type_daily) {
                    if (dailyET.getText() != null) {
                        //toDate = HejriUtil.hejriToChris(dailyET.getText().toString());
                        //fromDate = HejriUtil.add(toDate, Calendar.DAY_OF_MONTH, -1);
                        fromDate = HejriUtil.hejriToChris(startET.getText().toString());
                        toDate = HejriUtil.hejriToChris(endET.getText().toString());
                    }

                } else if (selectedIncidentType == R.id.incident_report_type_periodic) {
                    if (startET.getText() != null && endET.getText() != null) {
                        fromDate = HejriUtil.hejriToChris(startET.getText().toString());
                        toDate = HejriUtil.hejriToChris(endET.getText().toString());
                    }
                } else if (selectedIncidentType == R.id.incident_report_type_multi_month) {
                    toDate = CalendarUtil.firstDayOfMonth(new Date(), 0);
                    selectSpinnerDate1TV.setVisibility(VISIBLE);
                    if (spinner.getSelectedItem().equals(1)) {
                        fromDate = CalendarUtil.firstDayOfMonth(new Date(), -1);
                    } else if (spinner.getSelectedItem().equals(2)) {
                        fromDate = CalendarUtil.firstDayOfMonth(new Date(), -2);
                    } else if (spinner.getSelectedItem().equals(3)) {
                        fromDate = CalendarUtil.firstDayOfMonth(new Date(), -3);
                    } else {
                        Toast.makeText(getActivity(), "Please selected report type", Toast.LENGTH_LONG).show();
                        return;
                    }

                    long diff = toDate.getTime() - fromDate.getTime();
                    selectSpinnerDateTV.setText(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                }

                if (fromDate != null && toDate != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    strFromDate = simpleDateFormat.format(fromDate);
                    strToDate = simpleDateFormat.format(toDate);
                    Calendar startCalendar = new GregorianCalendar();
                    startCalendar.setTime(fromDate);
                    Calendar endCalendar = new GregorianCalendar();
                    endCalendar.setTime(toDate);
                    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
                    int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

                    boolean hasError = false;
                    if (fromDate.compareTo(new Date()) > 0 || toDate.compareTo(new Date()) > 0) {
                        showErrorDialog(getActivity().getResources().getString(R.string.error_dateIsGreaterThanCurrent));
                        hasError = true;
                    } else {
                        if (DateUtils.dateDiff(toDate, fromDate, Calendar.DAY_OF_MONTH) > 93) {
                            showErrorDialog(getActivity().getResources().getString(R.string.error_selectedMonthIsBigerThanTwo));
                            hasError = true;
                        }
                    }

                    if (!hasError) {
                        String driverProfile = getArguments().getString("driverProfile");
                        try {
                            JSONObject driverProfileJsonObject = new JSONObject(driverProfile);
                            code = driverProfileJsonObject.getString("driverCode");
                            driverId = driverProfileJsonObject.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myTask = new ASync();
                        myTask.execute();

                        rel.setVisibility(VISIBLE);
                    }
                }
            }
        });

        startET.setFocusableInTouchMode(false);
        endET.setFocusableInTouchMode(false);
        dailyET.setFocusableInTouchMode(false);
        startET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianDatePicker.showDatePicker(getActivity(), inflater, startET);
            }
        });
        startET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                PersianDatePicker.showDatePicker(getActivity(), inflater, startET);
            }
        });

        endET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianDatePicker.showDatePicker(getActivity(), inflater, endET);
            }
        });
        endET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                PersianDatePicker.showDatePicker(getActivity(), inflater, endET);
            }
        });
        dailyET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianDatePicker.showDatePicker(getActivity(), inflater, dailyET);
            }
        });

        dailyET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                PersianDatePicker.showDatePicker(getActivity(), inflater, dailyET);
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (myTask != null && myTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                        myTask.cancel(true);
                    }
                    getActivity().finish();
                    return true;
                }

                return false;
            }
        });

        layoutAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtra("entityNameEn", EntityNameEn.DriverProfile.ordinal());
                intent.putExtra("entityId", Long.valueOf(driverId));
                intent.putExtra("displayName", displayName);
                intent.putExtra("addIcon", addIcon1);
                startActivity(intent);
            }
        });
        return view;
    }

    private void init(View view) {
        backIcon = (ImageView) view.findViewById(R.id.backIcon);
        layoutAddIcon = (RelativeLayout) view.findViewById(R.id.addIcon);
        startET = (EditText) view.findViewById(R.id.start_ET);
        endET = (EditText) view.findViewById(R.id.end_ET);
        layoutSpinner = (RelativeLayout) view.findViewById(R.id.layout_Spinner);
        layoutDaily = (RelativeLayout) view.findViewById(R.id.layout_Daily);
        layoutOptionText = (RelativeLayout) view.findViewById(R.id.layout_OptionText);
        layoutMonthly = (LinearLayout) view.findViewById(R.id.layout_Monthly);
        rel = (LinearLayout) view.findViewById(R.id.rel);
        layoutReportStatistical = (LinearLayout) view.findViewById(R.id.layout_ReportStatistical);
        layoutCounter = (LinearLayout) view.findViewById(R.id.layout_counter);
        linearLayout = (RelativeLayout) view.findViewById(R.id.linearLayout);
        layoutDate = (RelativeLayout) view.findViewById(R.id.layout_Date);
        searchIcon = (ImageView) view.findViewById(R.id.search_Icon);
        databaseManager = new DatabaseManager(getActivity());
        coreService = new CoreService(databaseManager);
        driverNameTV = (TextView) view.findViewById(R.id.driverName_TV);
        dailyET = (EditText) view.findViewById(R.id.daily_TV);
        statistical = (ImageView) view.findViewById(R.id.statistical_img);
        counterTV = (TextView) view.findViewById(R.id.counter_TV);
        selectSpinnerDateTV = (TextView) view.findViewById(R.id.selectSpinnerDate_TV);
        selectSpinnerDate1TV = (TextView) view.findViewById(R.id.selectSpinnerDate1_TV);
        ComplainteReportTypeOP = (RadioGroup) view.findViewById(R.id.ComplainteReportType_OP);
        spinner = (Spinner) view.findViewById(R.id.spinner);
    }

    private void getDriverId() {
        String driverProfile = getArguments().getString("driverProfile");
        try {
            JSONObject driverProfileJsonObject = new JSONObject(driverProfile);
            JSONObject personJsonObject = driverProfileJsonObject.getJSONObject("person");
            code = driverProfileJsonObject.getString("driverCode");
            driverId = driverProfileJsonObject.getString("id");
            displayName = personJsonObject.getString("name");

            if (displayName == null) {
                displayName = personJsonObject.getString("family");
            } else {
                displayName += " " + personJsonObject.getString("family");
            }
            String driverCodeStr = getActivity().getResources().getString(R.string.label_driverCode_val2);
            driverNameTV.setText(driverCodeStr + " " + code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class ASync extends AsyncTask<Void, Void, Void> {
        private String result;
        private String errorMsg;

        @SuppressLint("StringFormatInvalid")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = ProgressDialog.show(getActivity(), null, getActivity().getResources().getString(R.string.label_progress_dialog, true), true);
            listView = (ListView) getActivity().findViewById(R.id.complaint_list);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.dismiss();
            if (result != null) {
                logLargeString(result);
                try {
                    JSONObject resultJson = new JSONObject(result);
                    System.out.println("Result===" + result);
                    if (errorMsg == null && !resultJson.isNull(Constants.SUCCESS_KEY)) {
                        if (!resultJson.isNull(Constants.RESULT_KEY)) {
                            JSONObject jsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);

                            if (!jsonObject.isNull("complaintList")) {
                                final JSONArray complaintListListJsonArray = jsonObject.getJSONArray("complaintList");
                                List<JSONObject> jsonObjectList = new ArrayList<>();
                                for (int i = 0; i < complaintListListJsonArray.length(); i++) {
                                    JSONObject complaintListJsonObject = complaintListListJsonArray.getJSONObject(i);
                                    jsonObjectList.add(complaintListJsonObject);
                                }
                                ComplaintAdapter ComplaintListAdapter = new ComplaintAdapter(getActivity(), R.layout.fragment_complaint_item, jsonObjectList);
                                listView.setAdapter(ComplaintListAdapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        try {
                                            JSONObject complaintListObject = complaintListListJsonArray.getJSONObject(i);
                                            Intent intent = new Intent(getActivity(), DriverComplaintDetailActivity.class);
                                            intent.putExtra("complaintList", complaintListObject.toString());
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                layoutCounter.setVisibility(VISIBLE);
                                counterTV.setText(String.valueOf(listView.getCount()));
                            }
                        }
                    } else {
                        if (errorMsg == null) {
                            errorMsg = resultJson.getString(Constants.ERROR_KEY);
                        }
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.d("RegistrationFragment", e.getMessage());
                    Toast.makeText(getActivity(), getResources().getString(R.string.Some_error_accor_contact_admin), Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(getActivity(), (errorMsg != null) ? errorMsg : getResources().getString(R.string.Some_error_accor_contact_admin), Toast.LENGTH_LONG).show();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            if (isDeviceDateTimeValid()) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    AppController application = (AppController) Objects.requireNonNull(getActivity()).getApplication();
                    jsonObject.put("username", application.getCurrentUser().getUsername());
                    jsonObject.put("tokenPass", application.getCurrentUser().getBisPassword());
                    //jsonObject.put("driverCode", code);
                    jsonObject.put("driverId", driverId);
                    jsonObject.put("fromDate", strFromDate);
                    jsonObject.put("toDate", strToDate);

                    MyPostJsonService postJsonService = new MyPostJsonService(databaseManager, getActivity());
                    try {
                        result = postJsonService.sendData("getDriverComplaintList", jsonObject, true);
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

        private boolean isDeviceDateTimeValid() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
            try {
                JSONObject jsonObjectParam = new JSONObject();
                MyPostJsonService postJsonService = new MyPostJsonService(databaseManager, getActivity());
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
            } catch (SocketTimeoutException e) {
                errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
            } catch (SocketException e) {
                errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
            } catch (JSONException e) {
                errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                Log.d("SyncActivity", e.getMessage());
            } catch (WebServiceException e) {
                errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                Log.d("SyncActivity", e.getMessage());
            } catch (ParseException e) {
                errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                Log.d("SyncActivity", e.getMessage());
            }
            return false;
        }
    }

    public void showErrorDialog(String errorMsg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getActivity().getResources().getString(R.string.label_error));
        alertDialogBuilder.setMessage(errorMsg);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getActivity().getResources().getString(R.string.label_close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @SuppressLint("LongLogTag")
    public void logLargeString(String str) {
        String Tag = "resultJsonDriverComplaint = ";
        if(str.length() > 3000) {
            Log.i(Tag, str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.i(Tag, str); // continuation
        }
    }
}
