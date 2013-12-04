package edu.ucla.cs.cs144;

public class ItemPurchaseData {

    int itemId;

    String itemName;

    float buyPrice;

    public ItemPurchaseData() {

    }

    public ItemPurchaseData(int itemId, String itemName, float buyPrice) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.buyPrice = buyPrice;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}