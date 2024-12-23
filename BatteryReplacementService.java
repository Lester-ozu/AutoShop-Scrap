public class BatteryReplacementService extends Service {

    private static final double CAR_BATTERY_PRICE = 5000, SERVICE_CHARGE = 300;
    protected double carBatteryPrice;

    public BatteryReplacementService(AccountProfile account, Vehicle vehicle) {

        super("Battery Replacement", SERVICE_CHARGE, account, vehicle);

        carBatteryPrice = CAR_BATTERY_PRICE;
    }

    public BatteryReplacementService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Battery Replacement", amount, account, vehicle);

        carBatteryPrice = CAR_BATTERY_PRICE;
    }

    public BatteryReplacementService(AccountProfile account, Vehicle vehicle, double batteryPrice) {

        super("Battery Replacement", SERVICE_CHARGE, account, vehicle);

        carBatteryPrice = batteryPrice;
    }

    public BatteryReplacementService(double amount, AccountProfile account, Vehicle vehicle, double batteryPrice) {

        super("Battery Replacement", amount, account, vehicle);

        carBatteryPrice = batteryPrice;
    }

    public double itemCharge() {

        return carBatteryPrice;
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
