public class SuspensionAndSteeringService extends Service {

    private static final double SHOCK_ABSORBER_PRICE = 3000, CONTROL_ARMS_PRICE = 2000, TIE_RODS_PRICE = 1500,
            SERVICE_CHARGE = 1000;
    protected double shockAbsorberPrice, controlArmsPrice, tieRodsPrice;
    protected int shockAbsorberQuantity, controlArmsQuantity, tieRodsQuantity;

    public SuspensionAndSteeringService(AccountProfile account, Vehicle vehicle, int absorberQuant, int armsQuant,
            int rodsQuant) {

        super("Suspension and Steering Service", SERVICE_CHARGE, account, vehicle);
        shockAbsorberPrice = SHOCK_ABSORBER_PRICE;
        controlArmsPrice = CONTROL_ARMS_PRICE;
        tieRodsPrice = TIE_RODS_PRICE;
        shockAbsorberQuantity = absorberQuant;
        controlArmsQuantity = armsQuant;
        tieRodsQuantity = rodsQuant;
    }

    public SuspensionAndSteeringService(AccountProfile account, Vehicle vehicle, int absorberQuant, int armsQuant,
            int rodsQuant, double absorberPrice, double armsPrice, double rodsPrice) {

        super("Suspension and Steering Service", SERVICE_CHARGE, account, vehicle);
        shockAbsorberPrice = absorberPrice;
        controlArmsPrice = armsPrice;
        tieRodsPrice = rodsPrice;
        shockAbsorberQuantity = absorberQuant;
        controlArmsQuantity = armsQuant;
        tieRodsQuantity = rodsQuant;
    }

    public SuspensionAndSteeringService(double amount, AccountProfile account, Vehicle vehicle, int absorberQuant,
            int armsQuant,
            int rodsQuant) {

        super("Suspension and Steering Service", amount, account, vehicle);
        shockAbsorberPrice = SHOCK_ABSORBER_PRICE;
        controlArmsPrice = CONTROL_ARMS_PRICE;
        tieRodsPrice = TIE_RODS_PRICE;
        shockAbsorberQuantity = absorberQuant;
        controlArmsQuantity = armsQuant;
        tieRodsQuantity = rodsQuant;
    }

    public SuspensionAndSteeringService(double amount, AccountProfile account, Vehicle vehicle, int absorberQuant,
            int armsQuant,
            int rodsQuant, double absorberPrice, double armsPrice, double rodsPrice) {

        super("Suspension and Steering Service", amount, account, vehicle);
        shockAbsorberPrice = absorberPrice;
        controlArmsPrice = armsPrice;
        tieRodsPrice = rodsPrice;
        shockAbsorberQuantity = absorberQuant;
        controlArmsQuantity = armsQuant;
        tieRodsQuantity = rodsQuant;
    }

    public double getShockAbsorberPrice() {

        return shockAbsorberPrice;
    }

    public double getControlArmsPrice() {

        return controlArmsPrice;
    }

    public double getTieRodsPrice() {

        return tieRodsPrice;
    }

    public int getShockAbsorberQuantity() {

        return shockAbsorberQuantity;
    }

    public int getControlArmsQuantity() {

        return controlArmsQuantity;
    }

    public int getTieRodsQuantity() {

        return tieRodsQuantity;
    }

    public void setShockAbsorberPrice(double amount) {

        shockAbsorberPrice = amount;
    }

    public void setControlArmsPrice(double amount) {

        controlArmsPrice = amount;
    }

    public void setTieRodsPrice(double amount) {

        tieRodsPrice = amount;
    }

    public void setShockAbsorberQuantity(int quantity) {

        shockAbsorberQuantity = quantity;
    }

    public void setControlArmsQuantity(int quantity) {

        controlArmsQuantity = quantity;
    }

    public void setTieRodsQuantity(int quantity) {

        tieRodsQuantity = quantity;
    }

    public double itemCharge() {

        return (shockAbsorberPrice * shockAbsorberQuantity) + (controlArmsPrice * controlArmsQuantity)
                + (tieRodsPrice * tieRodsQuantity);
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
