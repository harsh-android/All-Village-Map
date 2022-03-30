package com.appstudio.map.liveallvillagemap;

class CityModelData {

    String disname;
    String discode;

    public String getDisname() {
        return disname;
    }

    public void setDisname(String disname) {
        this.disname = disname;
    }

    public String getDiscode() {
        return discode;
    }

    public void setDiscode(String discode) {
        this.discode = discode;
    }

    public CityModelData() {
    }

    public CityModelData(String disname, String discode) {
        this.disname = disname;
        this.discode = discode;
    }


}
