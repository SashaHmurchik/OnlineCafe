package by.epam.cafe.entity;

import by.epam.cafe.entity.charackter.Category;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (!getId().equals(dish.getId())) return false;
        if (!getName().equals(dish.getName())) return false;
        if (!getKitchen().equals(dish.getKitchen())) return false;
        if (!getCategory().equals(dish.getCategory())) return false;
        if (!getPrice().equals(dish.getPrice())) return false;
        if (!getDescription().equals(dish.getDescription())) return false;
        if (!getAmount().equals(dish.getAmount())) return false;
        return getPicture().equals(dish.getPicture());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getKitchen().hashCode();
        result = 31 * result + getCategory().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + getPicture().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return /*"Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kitchen=" + kitchen +
                ", category=" + category +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", picture='" + picture + '\'' +
                '}'*/ "name";

    }
}
