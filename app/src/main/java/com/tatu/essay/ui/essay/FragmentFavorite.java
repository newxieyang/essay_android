package com.tatu.essay.ui.essay;

import android.os.Bundle;

import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.ui.main.BaseFragment;

import static com.tatu.essay.logic.EnumAction.FavoritesLoad;

/****
 *
 * 喜欢
 */
public class FragmentFavorite extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = FavoritesLoad.getAction();
        folder = EnumEssayFolder.FAVORITES;
    }


    /**
     * 从服务器拉数据
     */
    public void refresh() {
        EssayApi.favorites();
    }


}
