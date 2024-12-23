public class TimingBeltReplacementService extends Service {

    private static final double TIMING_BELT_KIT_PRICE = 300, SERVICE_CHARGE = 500;
    protected double TimingBeltKitPrice;

    public TimingBeltReplacementService(AccountProfile account, Vehicle vehicle) {

        super("Timing Belt Replacement", SERVICE_CHARGE, account, vehicle);
        TimingBeltKitPrice = TIMING_BELT_KIT_PRICE;
    }

    public TimingBeltReplacementService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Timing Belt Replacement", amount, account, vehicle);
        TimingBeltKitPrice = TIMING_BELT_KIT_PRICE;
    }

    public TimingBeltReplacementService(AccountProfile account, Vehicle vehicle, double price) {

        super("Timing Belt Replacement", SERVICE_CHARGE, account, vehicle);
        TimingBeltKitPrice = price;
    }

    public TimingBeltReplacementService(double amount, AccountProfile account, Vehicle vehicle, double price) {

        super("Timing Belt Replacement", amount, account, vehicle);
        TimingBeltKitPrice = price;
    }

    public double getTimingBeltKitPrice() {

        return TimingBeltKitPrice;
    }

    public void setTimingBeltKitPrice(double amount) {

        TimingBeltKitPrice = amount;
    }

    public double itemCharge() {

        return TimingBeltKitPrice;
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
