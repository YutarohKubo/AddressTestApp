package chom.arikui.waffle.addresstestapp;

public class AddressData {

    private String name;
    private String phone_num;
    private String fax_num;
    private String mail_address;
    private String note;

    public AddressData () {}

    public AddressData (String name, String phone_num, String fax_num, String mail_address, String note) {
        this.name = name;
        this.phone_num = phone_num;
        this.fax_num = fax_num;
        this.mail_address = mail_address;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getFax_num() {
        return fax_num;
    }

    public String getMail_address() {
        return mail_address;
    }

    public String getNote() {
        return note;
    }
}
