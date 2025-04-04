public class Product {
    protected int id;
    protected String name;
    protected int quantity;
    protected double price;


    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;


    } //end of Product constructor

    // display
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Quantity: " + quantity + ", Price: " + price;
    }
} //end of product class


