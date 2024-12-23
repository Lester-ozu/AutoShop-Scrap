public class WheelBalancingService extends Service {

    private static final double SERVICE_CHARGE = 500;

    public WheelBalancingService(AccountProfile account, Vehicle vehicle) {

        super("Wheel Balancing", SERVICE_CHARGE, account, vehicle);
    }

    public WheelBalancingService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Wheel Balancing", amount, account, vehicle);
    }

    public double itemCharge() {

        return 0;
    }

    public double totalCost() {

        return totalPayment = serviceCharge;
    }
}
