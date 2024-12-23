public class Vehicle {

    private String make, model, year;
    private double mileage;

    public Vehicle(String make, String model, String year, double mileage) {

        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public String getModel() {

        return model;
    }

    public String getMake() {

        return make;
    }

    public String getYear() {

        return year;
    }

    public double getMilieage() {

        return mileage;
    }

    public void setModel(String name) {

        model = name;
    }

    public void setMake(String name) {

        make = name;
    }

    public void setYear(String name) {

        year = name;
    }

    public void getMilieage(double digit) {

        mileage = digit;
    }

}
