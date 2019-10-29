import java.io.*;
import java.util.*;



public class Mysterious_Safeguard {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = null;
        BufferedWriter output = null;
        try {
            for (int j = 1; j <= 10; j++) {
                reader = new BufferedReader(new FileReader("Input/" + j + ".in"));
                output = new BufferedWriter(new FileWriter(new File("Output/" + j + ".out")));

                //First Line is not used
                String first_line = reader.readLine();

                //List to put split the lines into start_time and end_time
                List<ShiftUnits> ShiftUnits = new ArrayList<>();
                while ((first_line = reader.readLine()) != null) {
                    String[] units = first_line.trim().split(" ");
                    ShiftUnits Shiftunit = new ShiftUnits(Integer.parseInt(units[0]), Integer.parseInt(units[1]));
                    ShiftUnits.add(Shiftunit);
                }

                //Sort the ShiftUnits list wrt start_time
                Collections.sort(ShiftUnits);

                //Calculate the minimum impact lifeguard and calculate max time when removed the same
                int Max_time = Calculate_max_time(ShiftUnits);
                System.out.println(j + ".out: " + Max_time);
                output.write(Max_time + "\n");
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
            output.close();
        }
    }

    public static int Calculate_max_time(List<ShiftUnits> ShiftUnits) {

        // Calculate the time_gap between start time and end time for the first guard
        int time_gap = ShiftUnits.get(0).end_time - ShiftUnits.get(0).start_time;

        // end time of before lifeguard
        int lg_end = ShiftUnits.get(0).end_time;

        // minimum impact lifeguard that can be fired to get max throughput
        boolean Minimal_impact_guard = false;  //has found the safeguard that has minimal impact

        // Minimum impact of the guard to be fired
        int Minimal_Impact_Number = 0;
        int size_time_slots = ShiftUnits.size();

        //Effect periods corresponds to the effect produced on the overall time when each less impact guard if fired.
        int[] Effect_periods = new int[size_time_slots];
        for (int j = 0; j < size_time_slots; j++) {
            Effect_periods[j] = ShiftUnits.get(j).end_time - ShiftUnits.get(j).start_time;
        }

        // Business code
        for (int i = 1; i < size_time_slots; i++) {
            // if overlapping
            if (ShiftUnits.get(i).start_time < lg_end) {
                // when a time_slot of the guard is completely overlapping with the previous time_slot
                if (ShiftUnits.get(i).end_time < lg_end) {
                    Minimal_impact_guard = true;
                    Effect_periods[i] = 0;
                    Minimal_Impact_Number = 0;
                    continue;
                }

                // time gap corresponds to the diff btw end_times of consecutive guards
                time_gap += (ShiftUnits.get(i).end_time - lg_end);

                // Overlap to the previous slot
                int overlap = lg_end - ShiftUnits.get(i).start_time;

                // if lifeguard is not of minimal effect
                if (!Minimal_impact_guard) {
                    // lifeguards working hours - overlap hours
                    Effect_periods[i] = Effect_periods[i] - overlap;
                    // if 0 is the effect then
                    if (Effect_periods[i] <= 0) {
                        Minimal_impact_guard = true;
                        Minimal_Impact_Number = 0;
                    }

                    Effect_periods[i - 1] = Effect_periods[i - 1] - overlap;
                    if (Effect_periods[i - 1] <= 0) {
                        Minimal_impact_guard = true;
                        Minimal_Impact_Number = 0;
                    }
                }
            }
            // If time_slots are not overlapping
            else {
                time_gap += (ShiftUnits.get(i).end_time - ShiftUnits.get(i).start_time);
            }

            // assigning lg_end pointer to the end_time of current lifeguard so to act as a pointer for next lifeguard
            lg_end = ShiftUnits.get(i).end_time;
        }

        // finding the minimal effecting guard and his Minimal_Impact_Number
        if (!Minimal_impact_guard) {
            Minimal_Impact_Number = Effect_periods[0];
            for (int i = 1; i < size_time_slots; ++i) {
                if (Effect_periods[i] < Minimal_Impact_Number) {
                    Minimal_Impact_Number = Effect_periods[i];
                }
            }
        }
        return time_gap - Minimal_Impact_Number;
    }


}