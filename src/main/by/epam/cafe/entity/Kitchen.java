package by.epam.cafe.entity;

public class Kitchen extends Entity {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String site;
    private String email;
    private String picture;
    private Boolean archiveStatus;

    public Kitchen() {
    }

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

    public Boolean getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(Boolean archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kitchen)) return false;

        Kitchen kitchen = (Kitchen) o;

        if (getId() != null ? !getId().equals(kitchen.getId()) : kitchen.getId() != null) return false;
        if (getName() != null ? !getName().equals(kitchen.getName()) : kitchen.getName() != null) return false;
        if (getPhone() != null ? !getPhone().equals(kitchen.getPhone()) : kitchen.getPhone() != null) return false;
        if (getAddress() != null ? !getAddress().equals(kitchen.getAddress()) : kitchen.getAddress() != null)
            return false;
        if (getSite() != null ? !getSite().equals(kitchen.getSite()) : kitchen.getSite() != null) return false;
        if (getEmail() != null ? !getEmail().equals(kitchen.getEmail()) : kitchen.getEmail() != null) return false;
        if (getPicture() != null ? !getPicture().equals(kitchen.getPicture()) : kitchen.getPicture() != null)
            return false;
        return getArchiveStatus() != null ? getArchiveStatus().equals(kitchen.getArchiveStatus()) : kitchen.getArchiveStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getSite() != null ? getSite().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        result = 31 * result + (getArchiveStatus() != null ? getArchiveStatus().hashCode() : 0);
        return result;
    }
}
