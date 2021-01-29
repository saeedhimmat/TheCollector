package de.hsworms.vl.ema.AutoManager;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public  class Cars implements Parcelable {
    private String mImageResource;
    private String name;
    private String model;
    private String bauJahr;
    private String ps;



    private int yearServer;
    private int monthServer;
    private int dayServer;
    String lastServer;

    public String getLastServer() {
        return lastServer;
    }

    public void setLastServer(String lastServer) {
        this.lastServer = lastServer;
    }

    public int getYearServer() {
        return yearServer;
    }

    public void setYearServer(int yearServer) {
        this.yearServer = yearServer;
    }

    public int getMonthServer() {
        return monthServer;
    }

    public void setMonthServer(int monthServer) {
        this.monthServer = monthServer;
    }

    public int getDayServer() {
        return dayServer;
    }

    public void setDayServer(int dayServer) {
        this.dayServer = dayServer;
    }


    private String typ;
    private String KM;
    private String preis;
    private  String servicein;

    public String getModel() {
        return model;
    }

    public String getServicein() {
        return servicein;
    }

    public void setServicein(String servicein) {
        this.servicein = servicein;
    }

    public String getBauJahr() {
        return bauJahr;
    }


    public String getPs() {
        return ps;
    }

    public String getTyp() {
        return typ;
    }

    public String getKM() {
        return KM;
    }

    public String getPreis() {
        return preis;
    }

    public Cars(String imageResource, String name, String model, String bauJahr,String ser, String ps, String typp, String km, String tur,int year,int month,int day,String lastserver) {
        this.mImageResource = imageResource;
        this.name = name;
        this.model = model;
        this.bauJahr = bauJahr;
        this.ps = ps;
        this.typ = typp;
        this.KM = km;
        this.preis = tur;
        this.yearServer=year;
        this.monthServer=month;
        this.dayServer=day;
        this.lastServer=lastserver;
    }
    public Cars() {
        mImageResource = String.valueOf(R.drawable.ic_android);
        this.name = "Null";
        this.model = "Null";
        this.bauJahr = "0";
        this.ps = "0";
        this.typ = "0";
        this.KM = "0";
        this.preis = "0";
        this.yearServer=0;
        this.monthServer=0;
        this.dayServer=0;
        this.lastServer="0";

    }


    protected Cars(Parcel in) {
        mImageResource=in.readString();
        name = in.readString();
        model = in.readString();
        bauJahr = in.readString();
        ps = in.readString();
        typ = in.readString();
        KM = in.readString();
        preis = in.readString();
        servicein=in.readString();
        yearServer=in.readInt();
        monthServer=in.readInt();
        dayServer=in.readInt();
        lastServer=in.readString();
    }

    public static final Creator<Cars> CREATOR = new Creator<Cars>() {
        @Override
        public Cars createFromParcel(Parcel in) {
            return new Cars(in);
        }

        @Override
        public Cars[] newArray(int size) {
            return new Cars[size];
        }
    };

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBauJahr(String bauJahr) {
        this.bauJahr = bauJahr;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setKM(String KM) {
        this.KM = KM;
    }

    public void setPreis(String preis) {
        this.preis = preis;
    }

    public String getImageResource() {
        return mImageResource;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageResource);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeString(bauJahr);
        dest.writeString(ps);
        dest.writeString(typ);
        dest.writeString(KM);
        dest.writeString(preis);
        dest.writeString(servicein);
        dest.writeInt(yearServer);
        dest.writeInt(monthServer);
        dest.writeInt(dayServer);
        dest.writeString(lastServer);

    }
    public String ServiceIn()  {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(yearServer+1, monthServer, dayServer);
        end.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Date startDate = start.getTime();
        Date endDate = end.getTime();
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long diffTime = startTime - endTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        DateFormat dateFormat = DateFormat.getDateInstance();
        if(diffDays>0){
            String result= "Next Service in " + String.valueOf(diffDays) + " day";
            return result;
        }
        return "SERVICE IST FÄLLIG !!";
    }
}
