package com.example.finalprm392.Data;

import android.app.Application;
import android.content.Context;

import com.example.finalprm392.Constants.MyConstants;
import com.example.finalprm392.Database.RoomDB;
import com.example.finalprm392.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {
    RoomDB database;
    String category;
    Context context;
    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERTION = 3;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData(){
        category = "Basic Needs";
        List<Items> basicItems = new ArrayList<>();
        basicItems.add(new Items("Visa", category, false));
        basicItems.add(new Items("Passport", category, false));
        basicItems.add(new Items("Ticket", category, false));
        basicItems.add(new Items("Wallet", category, false));
        basicItems.add(new Items("Driving License", category, false));
        basicItems.add(new Items("Currency", category, false));
        basicItems.add(new Items("House Key", category, false));
        basicItems.add(new Items("Book", category, false));
        basicItems.add(new Items("Travel Pillow", category, false));
        basicItems.add(new Items("Eye Path", category, false));
        basicItems.add(new Items("Umbrella", category, false));
        basicItems.add(new Items("Note Book", category, false));
        return basicItems;
    }
    public List<Items> getPersonalCareData() {
        String []data = {"Tooth-brush", "Tooth-paste", "Floss", "Mouthwash", "Shaving Cream", "Razor Blade",
                "Soap", "Fiber", "Shampoo", "Hair Conditioner", "Brush", "Comb", "Hair Dryer", "Curling Iron",
                "Hair Moulder", "Hair Clip", "Moisturizer", "Lip Cream", "Contact Lens", "Perfume", "Deodorant",
                "Makeup Materials", "Makeup Remover", "Wet Wipes", "Pad", "Ear Stick", "Cotton", "Nail Polish",
                "Nail Polish Remover", "Tweezers", "Nail Scissors", "Nail Files", "Suntan Cream"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }

    public List<Items> getClothingData() {
        String []data = {"Stockings", "Underwear", "Pajamas", "T-Shirts", "Casual Dress", "Evening Dress",
                "Shirt", "Cardigan", "Vest", "Jacket", "Skirt", "Trousers", "Jeans", "Shorts", "Suit",
                "Coat", "Rain Coat", "Glove", "Hat", "Scarf", "Bikini", "Belt", "Slipper", "Sneakers", "Casual Shoes",
                "Heeled Shoes", "Sports Wear"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data);
    }
    public List<Items> getBabyNeedsData() {
        String []data = {"Snapsuit", "Outfit", "Jumpsuit", "Baby Socks", "Baby Hat", "Baby Pyjamas",
                "Baby Bath Towel", "Muslin", "Blanket", "Dribble Bibs", "Baby Laundry Detergent",
                "Baby Bottles", "Baby Food Thermos", "Baby Bottle Brushes", "Brest-feeding Cover",
                "Breast Pump", "Water Bottle", "Storage Container", "Baby Food Spoon",
                "Highchairs", "Diaper", "Wet Wipes", "Baby Cotton",
                "Baby Care Cover", "Baby Shampoo", "Baby soap", "Baby Nail Scissors",
                "Body Moisturizer", "Potty", "diaper Rash Cream", "Serum Physiological",
                "Nasal Aspiraton", "Fly Repellent Lotion", "Pyrometer", "Antipyretic Syrup", "Probiotic Power",
                "Baby Backpack", "Stroller", "Baby Carrier", "Toys", "Teether", "Playpen", "Playpen", "Baby Radio",
                "Non-slip Sea Shoes", "Baby Sunglasses"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getHealthData() {
        String []data = {"Aspirin", "Drugs Used", "Vitamins Used", "Lens Solutions", "Condom",
                "Hot Water Bag", "Tincture Of Lodine", "Adhesive Plaster",
                "First Aid Kit", "Replacement Lens", "Pain Reliever", "Fever Reducer", "Diarrhea Stopper"
                ,"Pain Relieve Spray"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data);
    }

    public List<Items> getTechnologyData() {
        String []data = {"Mobile Phone", "Phone Cover", "E-Book Reader", "Camera", "Camera Charger",
                "Portable Speaker", "IPAD (Tab)", "Headphone"
                ,"Laptop", "Laptop Charger", "Mouse", "Extension Cable", "Data Transfer Cable",
                "Battery", "Power Bank", "DVD Player", "Flash-Light", "MP3 Player", "MP3 Player Charger"
                ,"Voltage Adapter", "SD Card"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
    }

    public List<Items> getFoodData() {
        String []data = {"Snack", "Sandwich", "Juice", "Tea Bags", "Coffee", "Water", "Thermos", "Chips", "Baby Food"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data);
    }
    public List<Items> getBeachSuppliesData() {
        String []data = {"Sea Glasses", "Sea Bed", "Suntan Cream", "Beach Umbrella", "Swim Fins",
                "Beach Bag", "Beach Towel", "Beach Slippers",
                "Sunbed", "Snorkel", "Waterproof Clock"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getCarSuppliesData() {
        String []data = {"Pump", "Car Jack", "Spare Car Key", "Accident Record Set", "Auto Refrigerator",
                "Car Cover", "Car Charger", "Window Sun Shades"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getNeedsData() {
        String []data = {"Backpack", "Daily Bags", "Laundry Bag", "Sewing Kit",
                "Travel Lock", "Luggage Tag", "Magazine",
                "Sports Equipment", "Important Numbers"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE, data);
    }
     public List<Items> prepareItemsList(String category, String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();
        for (int i = 0; i<=list.size();i++){
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
     }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();;
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        //listOfAllItems.add(getNeedsData());

        return listOfAllItems;

    }

    public void persistAllData() {
        List<List<Items>> listOfAllItems = getAllData();
        for (List<Items> list : listOfAllItems) {
            for (Items items : list) {
                database.mainDAO().saveItem(items);
            }
        }
        System.out.println("Data added.");
    }
}
