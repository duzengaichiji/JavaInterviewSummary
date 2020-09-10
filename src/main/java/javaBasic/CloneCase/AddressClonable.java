package javaBasic.CloneCase;

import java.io.Serializable;

public class AddressClonable implements Cloneable, Serializable {
    private String province;
    private String city;

    public void setAddress(String province, String city) {
        this.province = province;
        this.city = city;
    }

    public String display() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
