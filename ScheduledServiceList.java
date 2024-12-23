import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScheduledServiceList {

    private ScheduledService[] SSList;
    private int listSize;

    public ScheduledServiceList(int size) {

        SSList = new ScheduledService[size];
        listSize = 0;
    }

    public void add(ScheduledService ss) {

        SSList[listSize] = ss;
        listSize++;
        arrayExpand();
    }

    public boolean delete(ScheduledService scheduled) {

        for (ScheduledService schedule : SSList) {

            if (schedule != null && schedule.equals(scheduled)) {

                schedule = null;
                arrayReAdjust();

                return true;
            }
        }

        return false;
    }

    public int getCount() {

        int i = 0;

        for (i = 0; i < SSList.length; i++) {
        }

        return i;
    }

    public ScheduledService[] getList() {

        return SSList;
    }

    public void arrayExpand() {

        if (SSList[SSList.length - 1] != null) {

            ScheduledService[] tempList = new ScheduledService[SSList.length + 10];

            int size = 0;

            for (int i = 0; i < SSList.length; i++) {

                if (SSList[i] != null) {

                    tempList[size] = SSList[i];
                    size++;
                }
            }

            SSList = tempList.clone();
        }
    }

    public void arrayReAdjust() {

        ScheduledService[] tempList = new ScheduledService[SSList.length];
        int size = 0;

        for (int i = 0; i < SSList.length; i++) {

            if (SSList[i] != null) {

                tempList[size] = SSList[i];
                size++;
            }
        }

        SSList = tempList.clone();
        listSize--;
    }
}
