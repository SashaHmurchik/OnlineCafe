package by.epam.cafe.entity;

public class Category extends Entity {
    private Integer id;
    private String name;
    private Boolean archiveStatus;

    public Category() {
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

    public Boolean getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(Boolean archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (getId() != null ? !getId().equals(category.getId()) : category.getId() != null) return false;
        if (getName() != null ? !getName().equals(category.getName()) : category.getName() != null) return false;
        return getArchiveStatus() != null ? getArchiveStatus().equals(category.getArchiveStatus()) : category.getArchiveStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getArchiveStatus() != null ? getArchiveStatus().hashCode() : 0);
        return result;
    }
}
