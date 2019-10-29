
// using Comparable interface so to incorporate collections and can perform like sort easily.
public class ShiftUnits implements Comparable<ShiftUnits> {

    int start_time;
    int end_time;

    public ShiftUnits() {

    }

    public ShiftUnits(int start_time , int end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @Override
    public int compareTo(ShiftUnits s) {
        if (s.start_time > start_time)
            return -1;
        else if (s.start_time < start_time)
            return 1;
        return 0;
    }

    public int get_Start_time() {
        return start_time;
    }

    public int get_end_time() {
        return end_time;
    }

    public void set_start_time(int start_time) {
        this.start_time = start_time;
    }

    public void set_end_time(int end_time) {
        this.end_time = end_time;
    }

}