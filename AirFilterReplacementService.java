public class AirFilterReplacementService extends Service {

    private static final double ENGINE_AIR_FILTER_PRICE = 800, SERVICE_CHARGE = 300;
    protected double engineAirFilterPrice;

    public AirFilterReplacementService(AccountProfile account, Vehicle vehicle) {

        super("Air Filter Replacement", SERVICE_CHARGE, account, vehicle);
        engineAirFilterPrice = ENGINE_AIR_FILTER_PRICE;
    }

    public AirFilterReplacementService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Air Filter Replacement", amount, account, vehicle);
        engineAirFilterPrice = ENGINE_AIR_FILTER_PRICE;
    }

    public AirFilterReplacementService(AccountProfile account, Vehicle vehicle, double price) {

        super("Air Filter Replacement", SERVICE_CHARGE, account, vehicle);
        engineAirFilterPrice = price;
    }

    public AirFilterReplacementService(double amount, AccountProfile account, Vehicle vehicle, double price) {

        super("Air Filter Replacement", amount, account, vehicle);
        engineAirFilterPrice = price;
    }

    public double getEngineAirFilterPrice() {

        return engineAirFilterPrice;
    }

    public void setEngineAirFilterPrice(double amount) {

        engineAirFilterPrice = amount;
    }

    public double itemCharge() {

        return engineAirFilterPrice;
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
