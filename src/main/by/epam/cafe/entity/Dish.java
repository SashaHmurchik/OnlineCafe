package by.epam.cafe.entity;

import java.math.BigDecimal;

public class Dish extends Entity {
    private Integer id;
    private String name;
    private Kitchen kitchen;
    private Category category;
    private BigDecimal price;
    private String description;
    private String amount;
    private String picture;
    private Boolean archiveStatus;

    public Dish() {
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

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (getId() != null ? !getId().equals(dish.getId()) : dish.getId() != null) return false;
        if (getName() != null ? !getName().equals(dish.getName()) : dish.getName() != null) return false;
        if (getKitchen() != null ? !getKitchen().equals(dish.getKitchen()) : dish.getKitchen() != null) return false;
        if (getCategory() != null ? !getCategory().equals(dish.getCategory()) : dish.getCategory() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(dish.getPrice()) : dish.getPrice() != null) return false;
        if (getDescription() != null ? !getDescription().equals(dish.getDescription()) : dish.getDescription() != null)
            return false;
        if (getAmount() != null ? !getAmount().equals(dish.getAmount()) : dish.getAmount() != null) return false;
        if (getPicture() != null ? !getPicture().equals(dish.getPicture()) : dish.getPicture() != null) return false;
        return getArchiveStatus() != null ? getArchiveStatus().equals(dish.getArchiveStatus()) : dish.getArchiveStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getKitchen() != null ? getKitchen().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        result = 31 * result + (getArchiveStatus() != null ? getArchiveStatus().hashCode() : 0);
        return result;
    }
}
