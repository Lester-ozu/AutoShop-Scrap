public class FuelSystemService extends Service {

    private static final double FUEL_INJECTOR_CLEANER_PRICE = 750, FUEL_FILTER_PRICE = 750, SERVICE_CHARGE = 1250;
    protected int fuelInjectorCleanerQuantity, fuelFilterQuantity;
    protected double fuelInjectorCleanerPrice, fuelFilterPrice;

    public FuelSystemService(AccountProfile account, Vehicle vehicle, int cleanerQuant, int filterQuant) {

        super("Fuel System Service", SERVICE_CHARGE, account, vehicle);
        fuelInjectorCleanerQuantity = cleanerQuant;
        fuelFilterQuantity = filterQuant;
        fuelInjectorCleanerPrice = FUEL_INJECTOR_CLEANER_PRICE;
        fuelFilterPrice = FUEL_FILTER_PRICE;
    }

    public FuelSystemService(double amount, AccountProfile account, Vehicle vehicle, int cleanerQuant,
            int filterQuant) {

        super("Fuel System Service", amount, account, vehicle);
        fuelInjectorCleanerQuantity = cleanerQuant;
        fuelFilterQuantity = filterQuant;
        fuelInjectorCleanerPrice = FUEL_INJECTOR_CLEANER_PRICE;
        fuelFilterPrice = FUEL_FILTER_PRICE;
    }

    public FuelSystemService(AccountProfile account, Vehicle vehicle, int cleanerQuant, int filterQuant,
            double cleanerPrice,
            double filterPrice) {

        super("Fuel System Service", SERVICE_CHARGE, account, vehicle);
        fuelInjectorCleanerQuantity = cleanerQuant;
        fuelFilterQuantity = filterQuant;
        fuelInjectorCleanerPrice = cleanerPrice;
        fuelFilterPrice = filterPrice;
    }

    public FuelSystemService(double amount, AccountProfile account, Vehicle vehicle, int cleanerQuant, int filterQuant,
            double cleanerPrice, double filterPrice) {

        super("Fuel System Service", amount, account, vehicle);
        fuelInjectorCleanerQuantity = cleanerQuant;
        fuelFilterQuantity = filterQuant;
        fuelInjectorCleanerPrice = cleanerPrice;
        fuelFilterPrice = filterPrice;
    }

    public int getFuelInjectorCleanerQuantity() {

        return fuelInjectorCleanerQuantity;
    }

    public int getFuelFilterQuantity() {

        return fuelFilterQuantity;
    }

    public double getFuelInjectorCleanerPrice() {

        return fuelInjectorCleanerPrice;
    }

    public double getFuelFilterPrice() {

        return fuelFilterPrice;
    }

    public double itemCharge() {

        return (fuelInjectorCleanerPrice * fuelInjectorCleanerQuantity) + (fuelFilterPrice * fuelFilterQuantity);
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
