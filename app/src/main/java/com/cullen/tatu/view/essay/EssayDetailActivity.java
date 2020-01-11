package com.cullen.tatu.view.essay;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.cullen.tatu.R;
import com.cullen.tatu.logic.NavigationStyle;
import com.cullen.tatu.view.main.BaseActivity;
import com.cullen.tatu.model.EssayModel;
import com.vondear.rxtool.RxTimeTool;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class EssayDetailActivity extends BaseActivity {

    @BindView(R.id.contentView)
    TextView contentView;

    @BindView(R.id.dateView)
    TextView dateView;


    @BindView(R.id.nameView)
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_essay_detail;
    }

    @Override
    protected void initView() {

        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/cao2.ttf");//仿宋
        Intent intent = getIntent();
        //接收从First_Activity中传输的数据
        EssayModel data = (EssayModel) intent.getSerializableExtra("essay");
        String content = "   " + data.getContent();
        contentView.setText(content);

        nameView.setTypeface(tf);
        nameView.setText("路人甲");
        String dateString = RxTimeTool.milliseconds2String(data.getCreateTime(), new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()));
        dateView.setText(dateString);

        findViewById(R.id.close).setOnClickListener(view -> finish());
    }



    @Override
    protected String initTitleText() {
        return null;
    }

    @Override
    protected NavigationStyle navigationStyle() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();
    }
}
