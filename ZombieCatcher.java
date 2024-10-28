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
    System.out.println(t0 + " " + t1 + " " + t2 + " " + t3);
    return overlappingPeriods(t0, t1, t2, t3);
  }

  public static void main(String[] args) {
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
