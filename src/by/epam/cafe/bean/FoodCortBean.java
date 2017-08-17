package by.epam.cafe.bean;

import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.charackter.Category;

import java.util.List;

public class FoodCortBean {
    private List<Category> categoryList;
    private List<Kitchen> kitchenList;

    public FoodCortBean() {
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Kitchen> getKitchenList() {
        return kitchenList;
    }

    public void setKitchenList(List<Kitchen> kitchenList) {
        this.kitchenList = kitchenList;
    }
}
