package com.vector.update_app;

import org.json.JSONObject;

/**
 * 新版本版本检测回调
 */
public class UpdateCallback {

    /**
     * 解析json,自定义协议
     *
     * @param json 服务器返回的json
     * @return UpdateAppBean
     */
    protected UpdateAppBean parseJson(String json) {
        UpdateAppBean updateAppBean = new UpdateAppBean();


        try {

            JSONObject res = new JSONObject(json);
            JSONObject jsonObject = res.getJSONObject("result");
            updateAppBean.setUpdate(jsonObject.optBoolean("update"))
                    //存放json，方便自定义解析
                    .setOriginRes(json)
                    .setNewVersion(jsonObject.optString("version"))
                    .setApkFileUrl(jsonObject.optString("appUrl"))
                    .setUpdateLog(jsonObject.optString("updateLog"))
                    .setConstraint(jsonObject.optInt("constraints") == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateAppBean;
    }

    /**
     * 有新版本
     *
     * @param updateApp        新版本信息
     * @param updateAppManager app更新管理器
     */
    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
        updateAppManager.showDialogFragment();
    }

    /**
     * 网路请求之后
     */
    protected void onAfter() {
    }


    /**
     * 没有新版本
     *
     * @param error HttpManager实现类请求出错返回的错误消息，交给使用者自己返回，有可能不同的应用错误内容需要提示给客户
     */
    protected void noNewApp(String error) {
    }

    /**
     * 网络请求之前
     */
    protected void onBefore() {
    }

}
