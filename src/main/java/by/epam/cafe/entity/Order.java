package by.epam.cafe.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order extends Entity {
    private Integer id;
    private User user;
    private LocalDate date;
    private LocalDate deliveryDate;
    private Boolean paid;
    private BigDecimal price;
    private Boolean deliveryStatus;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getId() != null ? !getId().equals(order.getId()) : order.getId() != null) return false;
        if (getUser() != null ? !getUser().equals(order.getUser()) : order.getUser() != null) return false;
        if (getDate() != null ? !getDate().equals(order.getDate()) : order.getDate() != null) return false;
        if (getDeliveryDate() != null ? !getDeliveryDate().equals(order.getDeliveryDate()) : order.getDeliveryDate() != null)
            return false;
        if (getPaid() != null ? !getPaid().equals(order.getPaid()) : order.getPaid() != null) return false;
        if (getPrice() != null ? !getPrice().equals(order.getPrice()) : order.getPrice() != null) return false;
        return getDeliveryStatus() != null ? getDeliveryStatus().equals(order.getDeliveryStatus()) : order.getDeliveryStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getDeliveryDate() != null ? getDeliveryDate().hashCode() : 0);
        result = 31 * result + (getPaid() != null ? getPaid().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDeliveryStatus() != null ? getDeliveryStatus().hashCode() : 0);
        return result;
    }
}
