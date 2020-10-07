package com.tatu.essay.api;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.model.ResumeModel;
import com.tatu.essay.ui.App;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;

/**
 * @author cullen
 * @title: ResumeApi
 * @projectName essay_android
 * @description: TODO
 * @date 2020/10/311:42
 */
public class ResumeApi extends Api {


    public static final String  resume= "";




    public static void getResume() {
        Log.e("get resume", "获取简历基础信息");
        OkGo.<String>get(resume_url  + Api.authorId).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if (response.code == 20) {
                    ResumeModel user = new Gson().fromJson(response.data.toString(), ResumeModel.class);
                }
                App.instance.manager.sendBroadcast(new Intent(EnumAction.AccountLoad.getAction()));
            }
        });
    }

    public static void updateResume() {
        Log.e("get resume", "获取简历基础信息");
        HttpParams params = new HttpParams();

        OkGo.<String>put(resume_url).params(params).execute(new JsonCallback(){

            @Override
            protected void onResponse(ResponseApi response) {
                if (response.code == 20) {
                    ResumeModel user = new Gson().fromJson(response.data.toString(), ResumeModel.class);
                }
                App.instance.manager.sendBroadcast(new Intent(EnumAction.AccountLoad.getAction()));
            }
        });
    }

    public static void addResume() {
        Log.e("get resume", "获取简历基础信息");
        HttpParams params = new HttpParams();

        OkGo.<String>post(resume_url).params(params).execute(new JsonCallback(){

            @Override
            protected void onResponse(ResponseApi response) {
                if (response.code == 20) {
                    ResumeModel user = new Gson().fromJson(response.data.toString(), ResumeModel.class);
                }
                App.instance.manager.sendBroadcast(new Intent(EnumAction.AccountLoad.getAction()));
            }
        });
    }


    public static void addWorkExperience() {

    }

    public static void updateWorkExperience() {

    }

    public static void deleteWorkExperience() {

    }

    public static void getWorkExperiences() {

    }


    public static void addSkillTools() {

    }

    public static void updateSkillTools() {

    }

    public static void deleteWorkSkillTools() {

    }

    public static void getWorkSkillTools() {

    }


    public static void addEducation() {

    }

    public static void updateEducation() {

    }

    public static void deleteEducation() {

    }

    public static void getEducations() {

    }
}
