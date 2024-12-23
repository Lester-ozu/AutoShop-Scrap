public class SparkPlugReplacementService extends Service {

    private static final double SPARK_PLUG_PRICE = 300, SERVICE_CHARGE = 500;
    protected double sparkPlugPrice;

    public SparkPlugReplacementService(AccountProfile account, Vehicle vehicle) {

        super("Spark Plug Replacement", SERVICE_CHARGE, account, vehicle);
        sparkPlugPrice = SPARK_PLUG_PRICE;
    }

    public SparkPlugReplacementService(double amount, AccountProfile account, Vehicle vehicle) {

        super("Spark Plug Replacement", amount, account, vehicle);
        sparkPlugPrice = SPARK_PLUG_PRICE;
    }

    public SparkPlugReplacementService(AccountProfile account, Vehicle vehicle, double price) {

        super("Spark Plug Replacement", SERVICE_CHARGE, account, vehicle);
        sparkPlugPrice = price;
    }

    public SparkPlugReplacementService(double amount, AccountProfile account, Vehicle vehicle, double price) {

        super("Spark Plug Replacement", amount, account, vehicle);
        sparkPlugPrice = price;
    }

    public double getSparkPlugPrice() {

        return sparkPlugPrice;
    }

    public void setSparkPlugPrice(double amount) {

        sparkPlugPrice = amount;
    }

    public double itemCharge() {

        return sparkPlugPrice;
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
