import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Visitor {
  private boolean valid_data;
  private String name;
  private int t0;
  private int t1;

  public Visitor(String name, int t0, int t1, boolean valid_data) {
    this.name = name;
    this.t0 = t0;
    this.t1 = t1;
    this.valid_data = valid_data;
  }

  public String getName() { return name; }
  public int getT0() { return t0; }
  public int getT1() { return t1; }
  public boolean isValidData() { return valid_data; }

  public void setName(String name) { this.name = name; }
  public void setT0(int t0) { this.t0 = t0; }
  public void setT1(int t1) { this.t1 = t1; }
  public void setValidData(boolean valid_data) { this.valid_data = valid_data; }

  @Override
  public String toString() {
    return "Visitor{" +
    "name='" + name + '\'' +
    ", t0=" + t0 +
    ", t1=" + t1 +
    ", valid_data=" + valid_data +
    '}';
  }
}

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
        System.out.println(name + " needs to be quarantined.");
        quarantine_count++;
      } else {
        System.out.println(name + " does not need to be quarantined.");
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

  public static Visitor validateFileLine(String line) {
    String[] vals = line.split(" ");
    boolean valid_data = true;
    String name = "";
    int f_t0 = 0;
    int f_t1 = 0;
    if (vals.length != 3) {
      valid_data = false;
    } else {
      try {
        name = vals[0];
        f_t0 = Integer.parseInt(vals[1]);
        f_t1 = Integer.parseInt(vals[2]);
      } catch (Exception e) {
        valid_data =  false;
      }
    }
    Visitor visitor = new Visitor(name, f_t0, f_t1, valid_data);
    return visitor;
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
              // Expect data in the following data
              // <name> <start_time> <end_time>
              // Eg Balaji 18 19

              // Try to create a visitor object
              // If data cannot be extracted properly mark it as not valid
              Visitor visitor = validateFileLine(data);

              if (visitor.isValidData()) {
                boolean overlaps = overlappingDayAndNightPeriods(t0, t1, visitor.getT0(), visitor.getT1());
                potential_zombies += overlaps ? 1 : 0;
                if (overlaps) {
                  System.out.println(visitor.getName() + " needs to be quarantined.");
                }
              } else {
                System.out.println(args[i] + " contians invalid input");
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
