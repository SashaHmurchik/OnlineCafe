package by.epam.cafe.entity;

public class Kitchen extends Entity {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String site;
    private String email;
    private String picture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kitchen)) return false;

        Kitchen kitchen = (Kitchen) o;

        if (!getId().equals(kitchen.getId())) return false;
        if (!getName().equals(kitchen.getName())) return false;
        if (!getPhone().equals(kitchen.getPhone())) return false;
        if (!getAddress().equals(kitchen.getAddress())) return false;
        if (!getSite().equals(kitchen.getSite())) return false;
        if (!getEmail().equals(kitchen.getEmail())) return false;
        return getPicture().equals(kitchen.getPicture());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getSite().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPicture().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Kitchen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", site='" + site + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
