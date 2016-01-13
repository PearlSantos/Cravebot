package cravebot.results.elysi.customobjects;
/**
 * Created by Francisco on 12/22/2015.
 */
public class Resto {
    private int restoid;
    private String restoname;
    private String restoaddress;
    private String restocontact;
    private String restostorehours;
    private String restologo;
    private String surveylink;

    public Resto(){}

    public Resto(int restoId, String restoAddress, String restoContact, String restoHours,
                 String restoLogo){
        this.restoid = restoId;
        this.restoaddress = restoAddress;
        this.restocontact = restoContact;
        this.restostorehours = restoHours;
        this.restologo = restoLogo;
    }

    public int getRestoId() {
        return restoid;
    }

    public void setRestoId(int restoId) {
        this.restoid = restoId;
    }

    public String getRestoName() {
        return restoname;
    }

    public void setRestoName(String restoName) {
        this.restoname = restoName;
    }

    public String getRestoAddress() {
        return restoaddress;
    }

    public void setRestoAddress(String restoAddress) {
        this.restoaddress = restoAddress;
    }

    public String getRestoContact() {
        return restocontact;
    }

    public void setRestoContact(String restoContact) {
        this.restocontact = restoContact;
    }

    public String getRestoHours() {
        return restostorehours;
    }

    public void setRestoHours(String restoHours) {
        this.restostorehours = restoHours;
    }

    public String getRestoLogo() {
        return restologo;
    }

    public void setRestoLogo(String restoLogo) {
        this.restologo = restoLogo;
    }

    public String getSurveyLink() {
        return surveylink;
    }

    public void setSurveyLink(String surveyLink) {
        this.surveylink = surveyLink;
    }
}
