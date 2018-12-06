package com.bvkit.douglas.bvkit.Model;

/**
 * Created by DOUGLAS on 09/09/2017.
 */

public class Strip {

    private String id;
    private String date;
    private String leu;
    private String nit;
    private String uro;
    private String pro;
    private String ph;
    private String blo;
    private String sg;
    private String ket;
    private String bil;
    private String glu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLeu() {
        return leu;
    }

    public void setLeu(String leu) {
        this.leu = leu;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getUro() {
        return uro;
    }

    public void setUro(String uro) {
        this.uro = uro;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getBlo() {
        return blo;
    }

    public void setBlo(String blo) {
        this.blo = blo;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getBil() {
        return bil;
    }

    public void setBil(String bil) {
        this.bil = bil;
    }

    public String getGlu() {
        return glu;
    }

    public void setGlu(String glu) {
        this.glu = glu;
    }
    public Strip(){}

    public Strip(String id, String date, String leu, String nit, String uro, String pro, String ph, String blo, String sg, String ket, String bil, String glu) {
        this.id = id;
        this.date = date;
        this.leu = leu;
        this.nit = nit;
        this.uro = uro;
        this.pro = pro;
        this.ph = ph;
        this.blo = blo;
        this.sg = sg;
        this.ket = ket;
        this.bil = bil;
        this.glu = glu;
    }

}
