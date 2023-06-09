import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    @BeforeEach
    public void init(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        // Adding more items to menu
        restaurant.addToMenu("Pizza capsicum", 299);
        restaurant.addToMenu("Chhole Bhature", 150);
        restaurant.addToMenu("Rava Dosa", 260);
        restaurant.addToMenu("Hyderabadi Chicken Biryani", 250);
        restaurant.addToMenu("Special Thaali", 390);
        restaurant.addToMenu("Vada Paav", 80);

        System.out.println("Restaurant Initialized.");
    }

    @AfterAll
    public static void clear(){
        System.out.println("Testing Completed!");
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("10:31:00");
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(currentTime);
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("10:29:00");
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(currentTime);
        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }


    @Test
    public void total_order_cost_should_be_equal_to_sum_of_selected_items_from_menu(){

        // Selecting items from menu
        List<String> itemList = new ArrayList<>();
        itemList.add("Sweet corn soup");
        itemList.add("Chhole Bhature");
        itemList.add("Hyderabadi Chicken Biryani");
        itemList.add("Rava Dosa");

        int totalOrderCost = restaurant.calculateOrderPrice(itemList);
        assertEquals((119+150+250+260),totalOrderCost);
    }

    @Test
    public void total_order_cost_should_be_zero_if_no_items_selected_from_menu(){

        // no items selected from menu
        List<String> itemList = new ArrayList<>();

        int totalOrderCost = restaurant.calculateOrderPrice(itemList);
        assertEquals(0,totalOrderCost);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}