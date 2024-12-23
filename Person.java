public class Person {

    protected String name, address, DOB, gender, contactNumber;

    public Person() {

        name = address = DOB = gender = contactNumber = "";
    }

    public Person(String name, String address, String DOB, String gender, String number) {

        this.name = name;
        this.address = address;
        this.DOB = DOB;
        this.gender = gender;
        contactNumber = number;
    }

    public Person(Person otherPerson) {

        this.name = otherPerson.name;
        this.address = otherPerson.address;
        this.DOB = otherPerson.DOB;
        this.gender = otherPerson.gender;
        this.contactNumber = otherPerson.contactNumber;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setDOB(String DOB) {

        this.DOB = DOB;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public void setContactNumber(String number) {

        contactNumber = number;
    }

    public String getName() {

        return name;
    }

    public String getAddress() {

        return address;
    }

    public String getDOB() {

        return DOB;
    }

    public String getGender() {

        return gender;
    }

    public String getContactNumber() {

        return contactNumber;
    }

    @Override
    public String toString() {

        return "Name: " + name +
                "\nAddress: " + address +
                "\nDOB: " + DOB +
                "\nGender: " + gender +
                "\nContact No. :" + contactNumber;
    }
}
