package cravebot.results.elysi.results;

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

    public String getRestoName() {
        return restoName;
    }

    public void setRestoName(String restoName) {
        this.restoName = restoName;
    }

    public String getRestoLogo() {
        return restoLogo;
    }

    public void setRestoLogo(String restoLogo) {
        this.restoLogo = restoLogo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getPrice4() {
        return price4;
    }

    public void setPrice4(String price4) {
        this.price4 = price4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getPrice5() {
        return price5;
    }

    public void setPrice5(String price5) {
        this.price5 = price5;
    }

    public String getOption6() {
        return option6;
    }

    public void setOption6(String option6) {
        this.option6 = option6;
    }

    public String getPrice6() {
        return price6;
    }

    public void setPrice6(String price6) {
        this.price6 = price6;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double d) {
        this.price = d;
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