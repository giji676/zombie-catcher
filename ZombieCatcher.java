import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ZombieCatcher {
  // Ask the user to input num of visitors and their times,
  // Check whichone needs to be quarantined
  public static int getVisitors(int t0, int t1) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of visitors:");
    int visitor_num = scanner.nextInt();
    scanner.nextLine();
    int quarantine_count = 0;

    for (int i = 0; i < visitor_num; i++) {
      System.out.println("Enter the visitor's name:");
      String name = scanner.nextLine();

      System.out.println("Enter the arrival time:");
      int arival_t = scanner.nextInt();
      scanner.nextLine();

      System.out.println("Enter the departure time:");
      int departure_t = scanner.nextInt();
      scanner.nextLine();

      boolean overlaps = overlappingPeriods(t0, t1, arival_t, departure_t);
      if (overlaps) {
        System.out.print(name);
        System.out.println(" needs to be quarantined.");
        quarantine_count++;
      } else {
        System.out.print(name);
        System.out.println(" does not need to be quarantined.");
      }
    }
    return quarantine_count;
  }

  // Check if two time periods overlap
  // Only for one day
  public static boolean overlappingPeriods(int t0, int t1, int t2, int t3) {
    if (t0 <= t2 && t2 < t1) {
      return true;
    } else if (t2 <= t0 && t0 < t3) {
      return true;
    } else {
      return false;
    }
  }

  // Check if two time periods overlap
  // Allows for time period to run into the next day
  public static boolean overlappingDayAndNightPeriods(int t0, int t1, int t2,
                                                      int t3) {
    if (t0 >= 7 && t1 <= 6) {
      t1 += 24;
    } else if (t0 < t2 && t3 < t2) {
      t0 += 24;
      t1 += 24;
    }
    if (t1 < t0) {
      t1 += 24;
    }
    if (t3 < t2) {
      t3 += 24;
    }
    return overlappingPeriods(t0, t1, t2, t3);
  }

  public static void main(String[] args) {
    // If files are specified get the visitors data from the files
    // Otherwise get it from user's input
    boolean files_given = false;
    if (args.length > 0) {
      files_given = true;
    }
    // Check if the files exist
    for (int i = 0; i < args.length; i++) {
      File file = new File(args[i]);

      if (!file.exists()) {
        System.out.println("WARNING: " + args[i] + " not found.");
        args[i] = null;
      }
    }

    Scanner scanner = new Scanner(System.in);

    // Getting the zombies start and end time
    System.out.println("Enter the start time:");
    int t0 = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Enter the end time:");
    int t1 = scanner.nextInt();
    scanner.nextLine();

    int potential_zombies = 0;
    if (files_given) {
      for (int i = 0; i < args.length; i++) {
        if (args[i] != null) {
          try {
            File myObj = new File(args[i]);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              // Proper data should provide the data in the following data
              // <name> <start_time> <end_time>
              // Eg Balaji 18 19
              String[] vals = data.split(" ");
              boolean overlaps = overlappingDayAndNightPeriods(t0, t1, Integer.parseInt(vals[1]), Integer.parseInt(vals[2]));
              potential_zombies += overlaps ? 1 : 0;
              if (overlaps) {
                System.out.print(vals[0]);
                System.out.println(" needs to be quarantined.");
              }
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("WARNING: " + args[i] + " not found.");
          }
        }
      }
    } else {
      potential_zombies = getVisitors(t0, t1);
    }

    System.out.println("Number of potential zombies: " + potential_zombies);
  }
}
