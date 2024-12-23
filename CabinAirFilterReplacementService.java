public class CabinAirFilterReplacementService extends Service {

    private static final double CABIN_AIR_FILTER_PRICE = 900, SERVICE_CHARGE = 300;
    protected double cabinAirFilterPrice;

    public CabinAirFilterReplacementService(AccountProfile account, Vehicle vehicle) {

        super("Cabin Filter Replacement", SERVICE_CHARGE, account, vehicle);
        cabinAirFilterPrice = CABIN_AIR_FILTER_PRICE;
    }

    public CabinAirFilterReplacementService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Cabin Filter Replacement", amount, account, vehicle);
        cabinAirFilterPrice = CABIN_AIR_FILTER_PRICE;
    }

    public CabinAirFilterReplacementService(AccountProfile account, Vehicle vehicle, double price) {

        super("Cabin Filter Replacement", SERVICE_CHARGE, account, vehicle);
        cabinAirFilterPrice = price;
    }

    public CabinAirFilterReplacementService(double amount, AccountProfile account, Vehicle vehicle, double price) {

        super("Cabin Filter Replacement", amount, account, vehicle);
        cabinAirFilterPrice = price;
    }

    public double getCabinAirFilterPrice() {

        return cabinAirFilterPrice;
    }

    public void setCabinAirFilterPrice(double amount) {

        cabinAirFilterPrice = amount;
    }

    public double itemCharge() {

        return cabinAirFilterPrice;
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
