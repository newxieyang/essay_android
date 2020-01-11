package com.cullen.tatu.view.fleet;


import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cullen.tatu.R;
import com.cullen.tatu.api.Api;
import com.cullen.tatu.api.ApiFleet;
import com.cullen.tatu.logic.NavigationStyle;
import com.cullen.tatu.view.main.BaseActivity;
import com.cullen.tatu.model.JsonBean;
import com.cullen.tatu.utils.GetJsonDataUtil;
import com.cullen.tatu.utils.IconResources;
import com.cullen.tatu.utils.http.JsonCallback;
import com.cullen.tatu.utils.http.ResponseApi;
import com.cullen.tatu.view.ui.LineEditText;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class FleetCreateActivity extends BaseActivity {


    @BindView(R.id.et_departure)
    EditText etDeparture;

    @BindView(R.id.et_destination)
    EditText etDestination;

    @BindView(R.id.et_departureTime)
    EditText etDepartureTime;

    @BindView(R.id.et_carNum)
    EditText etCarNum;

    @BindView(R.id.et_cost)
    EditText etCost;

    @BindView(R.id.et_vacancy)
    EditText etVacancy;

    @BindView(R.id.et_contact)
    EditText etContact;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_desc)
    LineEditText etDesc;


    boolean desFlag = true;

    private TimePickerView timePickerView;
    private Date departureTime;

    private OptionsPickerView cityPickerView;
    private List<JsonBean> pvItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_fleet_create;
    }

    @Override
    protected void initView() {

        initJsonData();

        iRightButton.setImageDrawable(IconResources.getOkIcon());
        iRightButton.setVisibility(View.VISIBLE);
        iRightButton.setOnClickListener(view -> save());

        initLunarPicker();

        etDepartureTime.setOnClickListener(view -> timePickerView.show());

        etDeparture.setOnClickListener(view -> {
            desFlag = true;
            cityPickerView.show();
        });

        etDestination.setOnClickListener(view -> {
            desFlag = false;
            cityPickerView.show();
        });


        initCityPickerView();
    }

    @Override
    protected String initTitleText() {
        return "添加返乡信息";
    }

    @Override
    protected NavigationStyle navigationStyle() {
        return NavigationStyle.Back;
    }


    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2069, 2, 28);
        //时间选择器 ，自定义布局
        timePickerView = new TimePickerBuilder(this, (Date date, View v) -> {//选中事件回调
            departureTime = date;
            etDepartureTime.setText(getTime(date));
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, v -> {
                        v.findViewById(R.id.tv_finish).setOnClickListener(view -> {
                                timePickerView.returnData();
                                timePickerView.dismiss();
                            }
                        );
                        v.findViewById(R.id.iv_cancel).setOnClickListener(view -> timePickerView.dismiss());
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void initCityPickerView() {// 弹出选择器

        cityPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = pvItems.size() > 0 ?
                        pvItems.get(options1).getPickerViewText() : "";

                String opt2tx = cityItems.size() > 0
                        && cityItems.get(options1).size() > 0 ?
                        cityItems.get(options1).get(options2) : "";

                String tx = opt1tx + opt2tx;
                if(desFlag) {
                    etDeparture.setText(tx);
                } else {
                    etDestination.setText(tx);
                }
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        cityPickerView.setPicker(pvItems, cityItems);//二级选择器

    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        pvItems = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
            }

            /**
             * 添加城市数据
             */
            cityItems.add(cityList);

        }


    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    private void save() {

        String departure = etDeparture.getText().toString();
        if (TextUtils.isEmpty(departure)) {
            showErrorMsg("出发地不能为空");
            return;
        }

        String destination = etDestination.getText().toString();
        if (TextUtils.isEmpty(departure)) {
            showErrorMsg("目的地不能为空");
            return;
        }

//        String departureTime = etDepartureTime.getText().toString();
        if (TextUtils.isEmpty(etDepartureTime.getText().toString())) {
            showErrorMsg("出发时间不能为空");
            return;
        }

        String carNum = etCarNum.getText().toString();


        String cost = etCost.getText().toString();


        String vacancy = etVacancy.getText().toString();


        String contact = etContact.getText().toString();
        if (TextUtils.isEmpty(departure)) {
            showErrorMsg("联系人不能为空");
            return;
        }

        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(departure)) {
            showErrorMsg("手机号码不能为空");
            return;
        }

        String desc = etDesc.getText().toString();


        HttpParams params = new HttpParams();
        params.put("departure", departure);
        params.put("destination", destination);
        params.put("departureTime", departureTime.getTime());
        params.put("carNum", carNum);
        params.put("cost", cost);
        params.put("vacancy", vacancy);
        params.put("contact", contact);
        params.put("phone", phone);
        params.put("desc", desc);
        params.put("authorId", Api.authorId);


        ApiFleet.save(params, new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if (response.code == 200) {
                    showInfo("恭喜您， 提交成功^_^");
                    FleetCreateActivity.this.finish();
                } else {
                    showErrorMsg(response.errMsg);
                }
            }
        });


    }
}
