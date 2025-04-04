public class RefrigeratedProduct extends Product {
    private double storageTemp;

    public RefrigeratedProduct(int id, String name, int quantity, double price, double storageTemp) {
        super(id, name, quantity, price);
        this.storageTemp = storageTemp;

    } //end of RefrigeratedProduct constructor

    public boolean isTemperatureSafe(double storageTemp) {
        return this.storageTemp >= 0 && this.storageTemp <= 7;
    }

    @Override
    public String toString() {
        String currentTemp;
        if (isTemperatureSafe(storageTemp)) {
            currentTemp = "OK";
        } else {
            currentTemp = "The product needs to return.";
        }
        return super.toString() + " " + currentTemp + ", Temperature:  " + storageTemp;

    }

} //end of class RefProduct


