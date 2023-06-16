package oose.data;

import oose.DB.DBC;

import java.util.ArrayList;

public class saleLineItems {
    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(String item) {
        items.add(item);
    }

    static ArrayList<String>  items=new ArrayList<>(0);

    public ArrayList<Integer> getItemsQty() {
        return itemsQty;
    }

    public void setItemsQty(int itemsQty) {
        saleLineItems.itemsQty.add(itemsQty);
    }

    static ArrayList<Integer>  itemsQty=new ArrayList<>(0);

    public void insertData(){
        DBC dbc=new DBC();
        String temp="";
        for (int i = 0; i < items.size(); i++) {
            if(i== items.size()-1){
                temp+=(items.get(i));
            }
            else {
                temp+=(items.get(i)+"&");
            }
        }
        dbc.recordTransation(temp);
    }
}
