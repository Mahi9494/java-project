package academic;

import java.util.*;

public class AcademicSystemComplete {

    static Scanner sc = new Scanner(System.in);
    static Map<String, Student> students = new HashMap<>();
    static final int PASS_MARKS = 40; // define pass mark

    public static void main(String[] args) {

        int choice;
        do {
            System.out.println("\n===== Academic Performance System =====");
            System.out.println("1. Add Student & Subjects");
            System.out.println("2. View Student Report");
            System.out.println("3. View Performance Categories");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> addStudentWithSubjects();
                case 2 -> viewStudentReport();
                case 3 -> viewCategories();
                case 4 -> System.out.println("Program Ended");
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 4);
    }

    // Add student and subjects
    static void addStudentWithSubjects() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        Student s = new Student(id, name);

        System.out.print("Enter number of subjects: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter Subject " + i + " name: ");
            String subject = sc.nextLine().toUpperCase();

            System.out.print("Enter marks for " + subject + ": ");
            int marks = sc.nextInt();
            sc.nextLine();

            s.marks.put(subject, marks);
        }

        students.put(id, s);
        System.out.println("Student and Marks Added Successfully!");
    }

    // View report for single student
    static void viewStudentReport() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student Not Found!");
            return;
        }

        System.out.println("\n----- Student Report -----");
        System.out.println("ID: " + s.id);
        System.out.println("Name: " + s.name);
        System.out.println("Marks:");

        int totalMarks = 0, passed = 0, failed = 0;

        for (Map.Entry<String, Integer> entry : s.marks.entrySet()) {
            int mark = entry.getValue();
            System.out.println(entry.getKey() + " : " + mark);
            totalMarks += mark;
            if (mark >= PASS_MARKS) passed++;
            else failed++;
        }

        double percentage = totalMarks / (double) s.marks.size();
        String grade = s.getGrade();
        String performance = getPerformanceCategory(percentage);

        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Subjects Passed: " + passed);
        System.out.println("Subjects Failed: " + failed);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Grade: " + grade);
        System.out.println("Overall Performance: " + performance);
    }

    // Categorize students
    static void viewCategories() {
        Map<String, List<String>> categories = new LinkedHashMap<>();
        categories.put("Excellent", new ArrayList<>());
        categories.put("Good", new ArrayList<>());
        categories.put("Average", new ArrayList<>());
        categories.put("Below Average", new ArrayList<>());
        categories.put("Poor", new ArrayList<>());

        for (Student s : students.values()) {
            double pct = s.getPercentage();
            String cat = getPerformanceCategory(pct);
            categories.get(cat).add(s.name + " (" + s.id + ")");
        }

        System.out.println("\n----- Student Performance Categories -----");
        for (Map.Entry<String, List<String>> entry : categories.entrySet()) {
            System.out.println(entry.getKey() + ":");
            if (entry.getValue().isEmpty()) System.out.println("  - None");
            else entry.getValue().forEach(s -> System.out.println("  " + s));
        }
    }

    // Helper to get performance category
    static String getPerformanceCategory(double percentage) {
        if (percentage >= 90) return "Excellent";
        else if (percentage >= 80) return "Good";
        else if (percentage >= 70) return "Average";
        else if (percentage >= 60) return "Below Average";
        else return "Poor";
    }
}

// Student class
class Student {
    String id;
    String name;
    Map<String, Integer> marks = new HashMap<>();

    Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    double getPercentage() {
        if (marks.isEmpty()) return 0;
        int total = 0;
        for (int m : marks.values()) total += m;
        return total / (double) marks.size();
    }

    String getGrade() {
        double p = getPercentage();
        if (p >= 90) return "A+";
        else if (p >= 80) return "A";
        else if (p >= 70) return "B";
        else if (p >= 60) return "C";
        else return "F";
    }
}
