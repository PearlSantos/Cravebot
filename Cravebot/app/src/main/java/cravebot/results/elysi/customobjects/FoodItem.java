package cravebot.results.elysi.customobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elysi on 12/23/2015.
 * Edited by christofferkho 12/29/2015
 */
public class FoodItem implements Parcelable {
    private String restoName, restoLogo, notes, itemName, description, option1, price1, option2, price2,
            option3, price3, option4, price4, option5, price5, option6, price6, photo;
    private double price;

    public FoodItem(){}
    public FoodItem(String restoName, String restoLogo, String notes, String itemName, double price, String description,
                    String option1, String price1, String option2, String price2, String option3, String price3,
                    String option4, String price4, String option5, String price5, String option6, String price6,
                    String photo) {
        this.restoName = restoName;
        this.restoLogo = restoLogo;
        this.notes = notes;
        this.itemName = itemName;
        this.description = description;
        this.option1 = option1;
        this.price1 = price1;
        this.option2 = option2;
        this.price2 = price2;
        this.option3 = option3;
        this.price3 = price3;
        this.option4 = option4;
        this.price4 = price4;
        this.option5 = option5;
        this.price5 = price5;
        this.option6 = option6;
        this.price6 = price6;
        this.photo = photo;
        this.price = price;
    }


    private FoodItem(Parcel in) {
        restoName = in.readString();
        restoLogo = in.readString();
        notes = in.readString();
        itemName = in.readString();
        description = in.readString();
        option1 = in.readString();
        price1 = in.readString();
        option2 = in.readString();
        price2 = in.readString();
        option3 = in.readString();
        price3 = in.readString();
        option4 = in.readString();
        price4 = in.readString();
        option5 = in.readString();
        price5 = in.readString();
        option6 = in.readString();
        price6 = in.readString();
        photo = in.readString();
        price = in.readDouble();
    }

    public String getDescription() {
        return description;
    }

    public String getRestoName() {
        return restoName;
    }

    public String getRestoLogo() {
        return restoLogo;
    }

    public String getNotes() {
        return notes;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }

    public String getOptions(int i){
        if(i==1) return option1;
        else if(i==2) return option2;
        else if(i==3) return option3;
        else if(i==4) return option4;
        else if(i==5) return option5;
        else if(i==6) return option6;
        else return "";
    }

    public String getPrices(int i){
        if(i==1) return price1;
        else if(i==2) return price2;
        else if(i==3) return price3;
        else if(i==4) return price4;
        else if(i==5) return price5;
        else if(i==6) return price6;
        else return "";
    }

    public void setRestoName(String restoName) {
        this.restoName = restoName;
    }

    public void setRestoLogo(String restoLogo) {
        this.restoLogo = restoLogo;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setOptions(int i, String options){
        if(i==1) option1=options;
        else if(i==2) option2=options;
        else if(i==3) option3=options;
        else if(i==4) option4=options;
        else if(i==5) option5=options;
        else if(i==6) option6=options;
    }

    public void setPrices(int i, String prices){
        if(i==1) price1=prices;
        else if(i==2) price2=prices;
        else if(i==3) price3=prices;
        else if(i==4) price4=prices;
        else if(i==5) price5=prices;
        else if(i==6) price6=prices;
    }


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(restoName);
        out.writeString(restoLogo);
        out.writeString(notes);
        out.writeString(itemName);
        out.writeString(description);
        out.writeString(option1);
        out.writeString(price1);
        out.writeString(option2);
        out.writeString(price2);
        out.writeString(option3);
        out.writeString(price3);
        out.writeString(option4);
        out.writeString(price4);
        out.writeString(option5);
        out.writeString(price5);
        out.writeString(option6);
        out.writeString(price6);
        out.writeString(photo);
        out.writeDouble(price);
    }

    public static final Parcelable.Creator<FoodItem> CREATOR = new Parcelable.Creator<FoodItem>() {
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };
}