package com.tatu.essay.service;

import com.tatu.essay.logic.EnumDataState;
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
     * @return
     */
    public static List<EssayModel> loadEssays(int pageNum) {


        return App.instance.getEssayDao().queryBuilder()
                .where(EssayModelDao.Properties.State.eq(EnumDataState.NORMAL.getState()))
                .orderDesc(EssayModelDao.Properties.CreateTime)
                .limit(Api.V_PAGE_SIZE).offset((pageNum - 1) * Api.V_PAGE_SIZE).list();

    }


    /***
     * 加载数据
     * @param pageNum
     * @return
     */
    public static List<EssayModel> loadMine(int pageNum, Long authorId) {

        return App.instance.getEssayDao().queryBuilder()
                .where(
                        EssayModelDao.Properties.State.eq(EnumDataState.NORMAL.getState()),
                        EssayModelDao.Properties.CreateBy.eq(authorId)
                )
                .orderDesc(EssayModelDao.Properties.CreateTime)
                .limit(Api.V_PAGE_SIZE).offset((pageNum - 1) * Api.V_PAGE_SIZE).list();

    }


    /***
     * 加载数据
     * @param ids
     * @return
     */
    public static List<EssayModel> loadFavorites(List<Long> ids) {
        return App.instance.getEssayDao().queryBuilder()
                .where(EssayModelDao.Properties.Id.in(ids))
                .orderDesc(EssayModelDao.Properties.CreateTime)
                .list();

    }


    /***
     * 加载数据
     * @param pageNum
     * @return
     */
    public static List<EssayModel> loadDrafts(int pageNum) {

        return App.instance.getEssayDao().queryBuilder()
                .where(EssayModelDao.Properties.State.eq(EnumDataState.DRAFT.getState()))
                .orderDesc(EssayModelDao.Properties.CreateTime)
                .limit(Api.V_PAGE_SIZE).offset((pageNum - 1) * Api.V_PAGE_SIZE).list();

    }


    public static void saveEssays(List<EssayModel> list) {
        App.instance.getEssayDao().insertOrReplaceInTx(list);
    }
}
