package by.epam.cafe.entity;


public class OrderDish extends Entity {
    private Order order;
    private Dish dish;
    private Integer number;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDish)) return false;

        OrderDish orderDish = (OrderDish) o;

        if (!getOrder().equals(orderDish.getOrder())) return false;
        if (!getDish().equals(orderDish.getDish())) return false;
        return getNumber().equals(orderDish.getNumber());
    }

    @Override
    public int hashCode() {
        int result = getOrder().hashCode();
        result = 31 * result + getDish().hashCode();
        result = 31 * result + getNumber().hashCode();
        return result;
    }
}
