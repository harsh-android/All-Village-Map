package com.appstudio.map.liveallvillagemap;

public class StateModelClass {

    String statename;
    String statecode;
    String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public StateModelClass(String statename, String statecode, String pic) {
        this.statename = statename;
        this.statecode = statecode;
        this.pic = pic;
    }

    public StateModelClass() {

    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }
}
