public class AccountProfile {

    protected String username, password;

    protected Person person;

    public AccountProfile(String username, String password, Person person) {

        this.username = username;
        this.password = password;
        this.person = person;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }

    public Person getPerson() {

        return person;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setPassword(Person person) {

        this.person = person;
    }

    public void update(String name, String address, String DOB, String gender, String number) {

        person.setName(name);
        person.setAddress(address);
        person.setDOB(DOB);
        person.setGender(gender);
        person.setContactNumber(number);
    }
}
