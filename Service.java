import java.util.Random;

public abstract class Service {

    protected String serviceID;
    protected String serviceName;
    protected double serviceCharge;
    protected AccountProfile vehicleOwner;
    protected Vehicle vehicleInfo;
    protected double totalPayment;

    public Service(String name, double charge, AccountProfile account, Vehicle vehicle) {

        serviceID = IDGenerator();
        serviceName = name;
        serviceCharge = charge;
        vehicleOwner = account;
        vehicleInfo = vehicle;
    }

    public String getServiceID() {

        return serviceID;
    }

    public String getServiceName() {

        return serviceName;
    }

    public double getServiceCharge() {

        return serviceCharge;
    }

    public AccountProfile getVehicleOwner() {

        return vehicleOwner;
    }

    public Vehicle getVehicleInfo() {

        return vehicleInfo;
    }

    public double getTotalPayment() {

        return totalPayment;
    }

    public void setServiceID(String identify) {

        serviceID = identify;
    }

    public void setServiceName(String name) {

        serviceName = name;
    }

    public void setServiceCharge(double amount) {

        serviceCharge = amount;
    }

    public void setVehicleOwner(AccountProfile account) {

        vehicleOwner = account;
    }

    public void setVehicleInfo(Vehicle vehicle) {

        vehicleInfo = vehicle;
    }

    public void setTotalPayment(double amount) {

        totalPayment = amount;
    }

    public abstract double itemCharge();

    public abstract double totalCost();

    public String IDGenerator() {

        Random random = new Random();

        String identify = "";

        for (int i = 0; i < 4; i++) {

            identify += (char) random.nextInt(26);
            identify += random.nextInt(9);
        }

        return identify;
    }
}