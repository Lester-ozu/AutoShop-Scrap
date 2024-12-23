public class TireRotationService extends Service {

    private static final double SERVICE_CHARGE = 300;

    public TireRotationService(AccountProfile account, Vehicle vehicle) {

        super("Tire Rotation", SERVICE_CHARGE, account, vehicle);
    }

    public TireRotationService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Tire Rotation", amount, account, vehicle);
    }

    public double itemCharge() {

        return 0;
    }

    public double totalCost() {

        return totalPayment = serviceCharge;
    }
}
