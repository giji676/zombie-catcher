import java.util.Scanner; 


public class ZombieCatcher {
  public static int getVisitors(int t0, int t1) {
    Scanner scanner_obj = new Scanner(System.in);
    System.out.println("Enter the number of visitors:");
    int visitor_num = scanner_obj.nextInt();
    scanner_obj.nextLine();
    int quarantine_count = 0;

    for (int i = 0; i < visitor_num; i++) {
      System.out.println("Enter the visitor's name:");
      String name = scanner_obj.nextLine();

      System.out.println("Enter the arrival time:");
      int arival_t = scanner_obj.nextInt();
      scanner_obj.nextLine();

      System.out.println("Enter the departure time:");
      int departure_t = scanner_obj.nextInt();
      scanner_obj.nextLine();

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

  public static boolean overlappingPeriods(int t0, int t1, int t2, int t3) {
    if (t0 <= t2 && t2 < t1) {
      return true;
    } else if (t2 <= t0 && t0 < t3) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean overlappingDayAndNightPeriods(int t0, int t1, int t2, int t3) {
    
    System.out.println(t0 + " " + t1 + " " + t2 + " " + t3);
    return overlappingPeriods(t0, t1, t2, t3);
  }

  public static void main(String[] args) {
    System.out.println(overlappingDayAndNightPeriods(22, 2, 22, 2)); // true
    System.out.println(overlappingDayAndNightPeriods(22, 2, 23, 2)); // true
    System.out.println(overlappingDayAndNightPeriods(22, 2, 23, 1)); // true
    System.out.println(overlappingDayAndNightPeriods(22, 2, 23, 3)); // true
    System.out.println(overlappingDayAndNightPeriods(22, 2, 21, 23)); // true
    System.out.println(overlappingDayAndNightPeriods(22, 2, 21, 22)); // false
    
    System.out.println(overlappingDayAndNightPeriods(1, 2, 23, 2)); // true
    System.exit(0);
    Scanner scanner_obj = new Scanner(System.in);

    System.out.println("Enter the start time:");
    int t0 = scanner_obj.nextInt();
    scanner_obj.nextLine();

    System.out.println("Enter the end time:");
    int t1 = scanner_obj.nextInt();
    scanner_obj.nextLine();
    int potential_zombies = getVisitors(t0, t1);
    
    System.out.println("Number of potential zombies: " + potential_zombies);
  }
}
