package by.epam.cafe.entity;

import by.epam.cafe.entity.charackter.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class User extends Entity {
    private static final int MAX_BASKET_CAPACITY = 5;
    private static final int MAX_DAYS_DELOVERY_PERIOD = 14;

    private Integer id;
    private Role role;
    private String name;
    private String surname;
    private String password;
    private String mail;
    private String phone;
    private String passport;
    private int loyaltyPoint;
    private String avatar;
    private HashMap<Dish, Integer> basket;
    private BigDecimal basketPrice = new BigDecimal(0.0);
    private LocalDate[] availableForDeliveryDays;

    private static LocalDate[]  initAvailableForDeliveryDays() {
        LocalDate[] availableForDeliveryDays = new LocalDate[3];
        LocalDate now = LocalDate.now();
        availableForDeliveryDays[0] = now;
        availableForDeliveryDays[1] = now.plusDays(1);
        availableForDeliveryDays[2] = now.plusDays(MAX_DAYS_DELOVERY_PERIOD);
        return availableForDeliveryDays;
    }
    public User() {
        basket = new HashMap<>(MAX_BASKET_CAPACITY);
        availableForDeliveryDays = initAvailableForDeliveryDays();
    }

    public void setBasket(HashMap<Dish, Integer> basket) {
        this.basket = basket;
    }

    public void setBasketPrice(BigDecimal basketPrice) {
        this.basketPrice = basketPrice;
    }

    public void setAvailableForDeliveryDays(LocalDate[] availableForDeliveryDays) {
        this.availableForDeliveryDays = availableForDeliveryDays;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getLoyaltyPoint() {
        return loyaltyPoint;
    }

    public void setLoyaltyPoint(int loyaltyPoint) {
        this.loyaltyPoint = loyaltyPoint;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public HashMap<Dish, Integer> getBasket() {
        return basket;
    }

    public BigDecimal getBasketPrice() {
        return basketPrice;
    }

    public LocalDate[] getAvailableForDeliveryDays() {
        return availableForDeliveryDays;
    }

    public boolean addToBasket(Dish dish) {
        boolean resp = false;

        int capacity = basket.keySet().stream().mapToInt(o -> basket.get(o)).sum();

        if (capacity < MAX_BASKET_CAPACITY) {
            if (basket.containsKey(dish)) {
                Integer amount = basket.get(dish);
                basket.put(dish, amount + 1);
            } else {
                basket.put(dish, new Integer(1));
            }
            basketPrice = basketPrice.add(dish.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            resp = true;
        }
        return resp;
    }

    public boolean removeFromBasket(Dish dish) {
        boolean resp = false;

        if (basket.containsKey(dish)) {
            if (basket.get(dish) > 1) {
                Integer amount = basket.get(dish);
                basket.put(dish, amount - 1);
            } else {
                basket.remove(dish);
            }
            basketPrice = basketPrice.subtract(dish.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            resp = true;
        }

        return resp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getLoyaltyPoint() != user.getLoyaltyPoint()) return false;
        if (!getId().equals(user.getId())) return false;
        if (getRole() != user.getRole()) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getSurname().equals(user.getSurname())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getMail().equals(user.getMail())) return false;
        if (!getPhone().equals(user.getPhone())) return false;
        if (!getPassport().equals(user.getPassport())) return false;
        return getAvatar().equals(user.getAvatar());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getRole().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getMail().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getPassport().hashCode();
        result = 31 * result + getLoyaltyPoint();
        result = 31 * result + getAvatar().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", passport='" + passport + '\'' +
                ", loyaltyPoint=" + loyaltyPoint +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
