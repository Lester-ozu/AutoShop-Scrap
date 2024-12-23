public class EmissionsSystemService extends Service {

    private static final double EGR_VALVE_PRICE = 2000, OXYGEN_SENSOR_PRICE = 2000, SERVICE_CHARGE = 650;
    protected int EGRValveQuantity, oxygenSensorQuantity;
    protected double EGRValvePrice, oxygenSensorPrice;

    public EmissionsSystemService(AccountProfile account, Vehicle vehicle, int valveQuant, int sensoryQuant) {

        super("Emissions System Service", SERVICE_CHARGE, account, vehicle);
        EGRValveQuantity = valveQuant;
        oxygenSensorQuantity = sensoryQuant;
        EGRValvePrice = EGR_VALVE_PRICE;
        oxygenSensorPrice = OXYGEN_SENSOR_PRICE;
    }

    public EmissionsSystemService(double amount, AccountProfile account, Vehicle vehicle, int valveQuant,
            int sensoryQuant) {

        super("Emissions System Service", amount, account, vehicle);
        EGRValveQuantity = valveQuant;
        oxygenSensorQuantity = sensoryQuant;
        EGRValvePrice = EGR_VALVE_PRICE;
        oxygenSensorPrice = OXYGEN_SENSOR_PRICE;
    }

    public EmissionsSystemService(AccountProfile account, Vehicle vehicle, int valveQuant, int sensoryQuant,
            double valvePrice,
            double sensoryPrice) {

        super("Emissions System Service", SERVICE_CHARGE, account, vehicle);
        EGRValveQuantity = valveQuant;
        oxygenSensorQuantity = sensoryQuant;
        EGRValvePrice = valvePrice;
        oxygenSensorPrice = sensoryPrice;
    }

    public EmissionsSystemService(double amount, AccountProfile account, Vehicle vehicle, int valveQuant,
            int sensoryQuant,
            double valvePrice,
            double sensoryPrice) {

        super("Emissions System Service", amount, account, vehicle);
        EGRValveQuantity = valveQuant;
        oxygenSensorQuantity = sensoryQuant;
        EGRValvePrice = valvePrice;
        oxygenSensorPrice = sensoryPrice;
    }

    public int getEGRValveQuantity() {

        return EGRValveQuantity;
    }

    public int getOxygenSensorQuantity() {

        return oxygenSensorQuantity;
    }

    public double getEGRValvePrice() {

        return EGRValvePrice;
    }

    public double getOxygenSensorPrice() {

        return oxygenSensorPrice;
    }

    public double itemCharge() {

        return (EGRValvePrice * EGRValveQuantity) + (oxygenSensorPrice * oxygenSensorQuantity);
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
