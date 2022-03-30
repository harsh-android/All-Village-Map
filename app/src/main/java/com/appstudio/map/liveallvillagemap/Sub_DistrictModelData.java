package com.appstudio.map.liveallvillagemap;

class Sub_DistrictModelData {

    String cityname;
    String citycode;
    String village;

    public Sub_DistrictModelData() {
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Sub_DistrictModelData(String cityname, String citycode, String village) {

        this.cityname = cityname;
        this.citycode = citycode;
        this.village = village;
    }
}
