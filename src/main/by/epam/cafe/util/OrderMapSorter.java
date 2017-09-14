package by.epam.cafe.util;

import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderMapSorter {
    private static Logger logger = LogManager.getLogger(OrderMapSorter.class);

    /**
     * Comparator for sorting by paid status
     */
    public static final Comparator<Order> comparatorByPaid = Comparator.comparing(Order::getPaid);

    /**
     *  Comparator for sorting by delivery date
     */
    public static final Comparator<Order> comparatorByDeliveryDate = (order, t1) -> t1.getDeliveryDate().compareTo(order.getDeliveryDate());

    /**
     * Method for getting chain comparator
     * @param comparators vararg with comparators
     * @return chain comparator
     */
    @SafeVarargs
    public static Comparator<Order> getChainComparator(Comparator<Order>... comparators) {
        Comparator<Order> result = null;
        if (comparators.length != 0) {
            result = comparators[0];
        }
        for (int i = 1; i < comparators.length; i++) {
            if (comparators[i] != null) {
                result = result.thenComparing(comparators[i]);
            }
        }
        return result;
    }

    /**
     * Sorting order.
     * @param orderMap - map with order for sorting.
     * @param comparator - comparator for sorting.
     * @return sorted map.
     */
    public Map<Order, HashMap<Dish, Integer>> sortOrders(Map<Order, HashMap<Dish, Integer>> orderMap, Comparator<Order> comparator) {
        if (orderMap.isEmpty() || orderMap == null || comparator == null) {
            logger.fatal("input parameter is null or empty");
            throw new RuntimeException("input parameter is null or empty");
        }
        Map<Order, HashMap<Dish, Integer>> result = new LinkedHashMap<>();

        orderMap.keySet().stream().sorted(comparator).forEach(o -> result.put(o, orderMap.get(o)));
        return result;
    }
}


