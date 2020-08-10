package com.tatu.essay.ui.essay;

/**
 * @author cullen
 * @title: PageInfo
 * @projectName essay_android
 * @description: TODO
 * @date 2020/8/1011:36
 */
public class PageInfo {

    public static final int PAGE_SIZE = 20;

    public int page = 0;

    public void nextPage() {
        page++;
    }

    public void reset() {
        page = 0;
    }

    public boolean isFirstPage() {
        return page == 0;
    }

}
