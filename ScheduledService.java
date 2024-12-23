public class ScheduledService {

    private Service service;
    private String date;
    private String time;
    private boolean isPaid = false;

    public ScheduledService(Service service, String date, String times) {

        this.service = service;
        this.date = date;
        this.time = time;
    }

    public Service getService() {

        return service;
    }

    public String getDate() {

        return date;
    }

    public String getTime() {

        return time;
    }

    public boolean getIsPaid() {

        return isPaid;
    }

    public void setService(Service service) {

        this.service = service;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public void getIsPaid(boolean truth) {

        isPaid = truth;
    }
}
