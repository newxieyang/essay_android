package com.cullen.tatu.view.essay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.cullen.tatu.R;
import com.cullen.tatu.model.EssayModel;
import com.cullen.tatu.view.main.BaseActivity;
import com.vondear.rxtool.RxTimeTool;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class EssayDetailActivity extends BaseActivity {

    @BindView(R.id.contentView)
    AppCompatTextView contentView;

/*    @BindView(R.id.dateView)
    TextView dateView;

    @BindView(R.id.nameView)
    TextView nameView;*/

    @BindView(R.id.activity_header)
    Toolbar toolbar;

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

        Intent intent = getIntent();
        //接收从First_Activity中传输的数据
        EssayModel data = (EssayModel) intent.getSerializableExtra("essay");
        String content = "   " + data.getContent();
        contentView.setText(content);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener((View view)->finish());

        String dateString = RxTimeTool.milliseconds2String(data.getCreateTime(), new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()));

        String tag = "路人甲 于" + dateString;

        toolbar.setTitle(tag);

     /*   nameView.setText("路人甲");
        String dateString = RxTimeTool.milliseconds2String(data.getCreateTime(), new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()));
        dateView.setText(dateString);*/

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();
    }
}
