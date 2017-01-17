package com.com.tigarty.api.RecycleViewAssets;

/**
 * Created by Amr Saidam on 12/1/2016.
 */

public class CellContent {

    private int cellId;
    private String mainTitle;
    private String subTitle;

    public CellContent(int cellId, String mainTitle, String subTitle) {
        this.cellId = cellId;
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
