public class WheelAlignmentService extends Service {

    private static final double SERVICE_CHARGE = 2300;

    public WheelAlignmentService(AccountProfile account, Vehicle vehicle) {

        super("Wheel Alignment", SERVICE_CHARGE, account, vehicle);
    }

    public WheelAlignmentService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Wheel Alignment", amount, account, vehicle);
    }

    public double itemCharge() {

        return 0;
    }

    public double totalCost() {

        return totalPayment = serviceCharge;
    }
}
