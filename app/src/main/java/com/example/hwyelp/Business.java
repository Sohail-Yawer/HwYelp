package com.example.hwyelp;

public class Business {
    private String bid;
    private String coverImageURL;
    private String bname;
    private String brating;
    private String bdistance;
    private String bd;
    private String burl;

    public Business() {}
    public  Business(String bid,String coverImageURL,String bname,String brating,String bdistance){
        this.bid = bid;
        this.coverImageURL = coverImageURL;
        this.bname = bname;
        this.brating = brating;
        this.bdistance = bdistance;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBrating() {
        return brating;
    }

    public void setBrating(String brating) {
        this.brating = brating;
    }

    public String getBdistance() {
        return bdistance;
    }

    public void setBdistance(String bdistance) {
        this.bdistance = bdistance;
    }

    public void setBd(String bd){this.bd = bd;}

    public String getBd(){return bd;}

    public void setBurl(String burl){this.burl = burl;}

    public String getBurl() {return burl;}

}
