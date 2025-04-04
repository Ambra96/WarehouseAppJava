public class DryProduct extends Product {
    private String packagingType;

    public DryProduct(int id, String name, int quantity, double price, String packagingType) {
        super(id, name, quantity, price);
        this.packagingType = packagingType;


    } //end of DryProdcut constructor

    public String getTransportType() {


        if (packagingType.equalsIgnoreCase("pallet")) {
            return "Truck";
        } else if (packagingType.equalsIgnoreCase("box")) {
            return "Van";
        } else {
            return "No type";
        }
    }


    @Override
    public String toString() {
        return super.toString() + " Packaging Type: " + packagingType + ", Transport Type: " + getTransportType();
    }


} //end of DryProduct class
