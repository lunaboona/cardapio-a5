package com.company;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int id;
    private List<Item> items = new ArrayList<>();
    private String note;

    public Order(int id) {
        this.id = id;
    }

    public void AddItem(Item item) {
        this.items.add(item);
    }
    public void RemoveItem(int index) {
        this.items.remove(index);
    }

    public List<Item> GetItems() {
        return this.items;
    }

    public void SetNote(String note) {
        this.note = note;
    }
    public String GetNote() {
        return this.note;
    }

    public float GetTotal() {
        return this.items.stream().map(item -> item.price).reduce((float) 0, Float::sum);
    }

    public int GetId() {
        return this.id;
    }

    public boolean IsValid() {
        return this.items.size() != 0;
    }

}
