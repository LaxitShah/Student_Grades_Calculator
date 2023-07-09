import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Console con = System.console();
        UserService userService = new UserService(scan, con);
        int choice = -1;

        while (choice != 3) {
            userService.displayHomeMenuTxt();
            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    userService.displaySignInMenu();
                    break;

                case 2:
                    userService.handleRegistration();
                    break;

                case 3:
                    System.out.println("\nGoodbye");
                    break;

                default:
                    System.out.println("\nINVALID INPUT\n");
                    System.out.println("Press enter to continue");
                    con.readLine();
            }
        }
    }
}

class User {
    public String fullName;
    public String username;
    public String password;
    public Student student;
}

class Subject {
    public String name;
    public Double marks;
}

class Student {
    public Integer studentId;
    public ArrayList<Subject> subjects;
    private Scanner scan;

    public Student(Scanner scan) {
        this.scan = scan;
    }

    public void getStudentIdInput() {
        System.out.println("Enter student id: ");
        this.studentId = this.scan.nextInt();
    }

    public void getSubjectsInput() {
        subjects = new ArrayList<Subject>();
        System.out.println("How many number of subjects you want to enter: ");
        int numOfSubjects = this.scan.nextInt();
        boolean isCorrectInput = false;

        for (int i = 0; i < numOfSubjects; i++) {
            isCorrectInput = false;
            Subject subject = new Subject();
            System.out.println("Enter subject name: ");
            subject.name = this.scan.next();
            while (!isCorrectInput) {
                System.out.println("Enter subject marks: ");
                subject.marks = this.scan.nextDouble();

                if (subject.marks.doubleValue() < 0 || subject.marks.doubleValue() > 100) {
                    System.out.println("\nMarks should be in between 0 to 100\n");
                } else {
                    isCorrectInput = true;
                }
            }
            this.subjects.add(subject);
        }
    }

    public void displaySubjectsDetail() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("\n======> List of subjects <======");
        System.out.println("\nName\t\tGained Marks\t\tTotal Marks");
        for (Subject subject : this.subjects) {
            System.out.printf("%s\t\t%.2f\t\t\t100", subject.name, subject.marks);
            System.out.println();
        }
        System.out.println();
    }

    public void displayPercentage() {
        double percentage = this.calculatePercentage();
        System.out.printf("\nPercentage: %.2f", percentage);
        System.out.print(" %\n\n");
    }

    public double calculatePercentage() {
        double totalMarksObtained = 0;
        for (Subject subject : this.subjects) {
            totalMarksObtained += subject.marks.doubleValue();
        }
        double percentage = (totalMarksObtained * 100) / (this.subjects.size() * 100);
        return percentage;
    }

    public void displayGrades() {
        System.out.println("\n======> Grade Card <======");
        System.out.println("Subject Name\t\tGrade");
        for (Subject subject : this.subjects) {
            System.out.println(subject.name + "\t\t\t" + this.getGrade(subject.marks));
        }
        double percentage = this.calculatePercentage();
        String grade = this.getGrade(percentage);
        System.out.println("\nOverall Grade:- " + grade + "\n");
    }

    public String getGrade(Double marks) {
        if (marks >= 91) {
            return "A1";
        } else if (marks >= 81) {
            return "A2";
        } else if (marks >= 71) {
            return "B1";
        } else if (marks >= 61) {
            return "B2";
        } else if (marks >= 51) {
            return "C1";
        } else if (marks >= 41) {
            return "C2";
        } else if (marks >= 31) {
            return "D";
        } else {
            return "F";
        }
    }

    public void displayStatus() {
        double percentage = this.calculatePercentage();
        String status = (percentage >= 31) ? "Pass" : "Fail";
        System.out.println("\nStatus: " + status + "\n");
    }
}

class UserService {
    private ArrayList<User> users;
    private Scanner scan;
    private Console con;

    public UserService(Scanner scan, Console con) {
        this.users = new ArrayList<User>();
        this.scan = scan;
        this.con = con;
    }

    public void displayHomeHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("+------------------------------------------+");
        System.out.println("|                                          |");
        System.out.println("| ======> Student grade calculator <====== |");
        System.out.println("|                                          |");
        System.out.println("+------------------------------------------+");
        System.out.println();
    }

    public void displayHomeMenuTxt() {
        this.displayHomeHeader();
        System.out.println("1. Sign In");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Enter your choice: ");
    }

    public void displaySignInMenu() {
        int choice = -1;
        while (choice != 1 && choice != 2) {
            System.out.println("\nChoose an option:- ");
            System.out.println("1. Enter sign in details");
            System.out.println("2. Forgot password");
            System.out.println("Enter your choice: ");
            choice = this.scan.nextInt();
            switch (choice) {
                case 1:
                    User user = this.handleSignIn();
                    if (user != null) {
                        this.handleSignedInUser(user);
                    }
                    break;
                case 2:
                    this.handleForgotPassword();
                    break;
                default:
                    System.out.println("\nINVALID INPUT\n");
                    System.out.println("Press enter to continue");
                    this.con.readLine();
            }
        }
    } 

    public void handleRegistration() {
        User newUser = this.getRegistrationInput();
        if(this.users.add(newUser)) {
            System.out.println("\nRegistered successfully\n");
        } else {
            System.out.println("\nFailed to register\n");
        }
        System.out.println("Press enter to continue");
        this.con.readLine();
    }

    public User getRegistrationInput() {
        User user = new User();
        user.fullName = this.getFullNameInput();
        user.username = this.getUsernameInput();
        user.password = this.getPasswordInput("Enter password: ");
        return user;
    } 

    public User handleSignIn() {
        String username = this.getUsernameInput();
        String password = this.getPasswordInput("Enter password: ");
        for (User user : users) {
            if (user.username.equals(username)) {
                if (user.password.equals(password)) {
                    return user;
                } else {
                    System.out.println("\nInvalid password\n");
                    System.out.println("Press enter to continue");
                    this.con.readLine();
                    return null;
                }
            }
        }
        System.out.println("\nUser is not registered\n");
        System.out.println("Press enter to continue");
        this.con.readLine();
        return null;
    }

    public void handleSignedInUser(User user) {
        System.out.println("\nSuccessfully signed in\n");
        System.out.println("Press enter to continue");
        this.con.readLine();
        
        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean isLoggedIn = true;
        while (isLoggedIn) {
            if (user.student == null) {
                Student student = new Student(scan);
                System.out.println("Enter student details:");
                student.getStudentIdInput();
                student.getSubjectsInput();
                user.student = student;
            }
            user.student.displaySubjectsDetail();
            if (!this.displayStudentMenu(user)) {
                isLoggedIn = false;
            }
        }
    }

    public void handleForgotPassword() {
        String fullName = this.getFullNameInput();
        if (this.resetPassword(fullName)) {
            System.out.println("\nSuccessfully changed the password\n");
        } else {
            System.out.println("\nFull name doesn't exists\n");
        }
        System.out.println("Press enter to continue");
        this.con.readLine();
    }

    public boolean resetPassword(String fullName) {
        for (User user : this.users) {
            if (user.fullName.equals(fullName)) {
                user.password = this.getPasswordInput("Enter new password: ");
                return true;
            }
        }
        return false;
    }

    public boolean displayStudentMenu(User user) {
        int studentChoice = -1;

        System.out.println("Choose an option:-");
        System.out.println("1. Show percentage");
        System.out.println("2. Show grades");
        System.out.println("3. Show status");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        studentChoice = scan.nextInt();

        switch (studentChoice) {
            case 1:
                user.student.displayPercentage();
                break;

            case 2:
                user.student.displayGrades();
                break;

            case 3:
                user.student.displayStatus();
                break;

            case 4:
                System.out.println("\nYour are logged out\n");
                System.out.println("Press enter to continue");
                this.con.readLine();
                return false;

            default:
                System.out.println("\nINVALID INPUT\n");
        }
        System.out.println("Press enter to continue");
        this.con.readLine();
        return true;
    }

    public String getUsernameInput() {
        System.out.println("Enter username: ");
        String username = this.scan.next();
        return username;
    }

    public String getFullNameInput() {
        System.out.println("Enter full name: ");
        this.scan.nextLine();
        String fullName = this.scan.nextLine();
        return fullName;
    }

    public String getPasswordInput(String message) {
        if (this.con == null) {
            System.out.println("\nConsole is not available\n");
            return null;
        }
        System.out.print(message);
        char[] pass;
        pass = this.con.readPassword();
        for (int i = 0; i < pass.length; i++) {
            System.out.print("*");
        }
        System.out.println();
        return new String(pass);
    }
}