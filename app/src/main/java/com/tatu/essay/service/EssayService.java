package com.tatu.essay.service;

import com.tatu.essay.ui.App;
import com.tatu.essay.api.Api;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.model.gen.EssayModelDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class EssayService extends BaseService {


    /**
     * 获取数据库最大的id
     *
     * @return
     */
    public static EssayModel getLastEssay() {
        return App.instance.getEssayDao().queryBuilder()
                .orderDesc(EssayModelDao.Properties.CreateTime).limit(1).unique();

    }


    /***
     * 加载数据
     * @param pageNum
     * @param authorId
     * @return
     */
    public static List<EssayModel> loadEssay(int pageNum, Long authorId) {
        String condition = " 1=1 ";

        if (authorId != null) {
            condition += " and " + EssayModelDao.Properties.CreateBy.columnName + "=" + authorId + " ";

        }

        List<EssayModel> list = App.instance.getEssayDao().queryBuilder()
                .where(new WhereCondition.StringCondition(condition))
                .orderDesc(EssayModelDao.Properties.CreateTime)
                .limit(Api.V_PAGE_SIZE).offset((pageNum - 1) * Api.V_PAGE_SIZE).list();

        for (EssayModel essayModel : list) {
            System.out.println(essayModel.getId());
        }

        return list;
    }


    public static void saveEssays(List<EssayModel> list) {
        App.instance.getEssayDao().insertOrReplaceInTx(list);
    }
}
