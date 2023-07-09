# Student_Grades_Calculator
A console-based application for Student Grades Calculator in java


Part I: User Login/ Register

● Application should start by showing two options: (1) Sign In (2) Register

● In the case of Sign In, ask the user to provide his “username” and “password”. If they
match, let her in, not otherwise.

● In the case of Register, ask the user to provide his “full name”, “username”, and
“password”. In case of password, echo asterisk symbol (*) in place of the typed character.

E.g., a user types “1@4”, you should display “***”, but you will store the actual
password to match when she logs in again.

Hint: You can use java.io.Console.readPassword() method. Carefully read
its description from the Java 8 docs online.

● In case of Sign In, provide an option of “Forgot Password”. Ask the user for her name, if
it matches then, let her set a new password and redirect her to login again with the new
password.

Part II: Input Details

● As the user successfully logs in, user needs to input details: (1) Student id, (2) Number of
subjects, (3) Name of each subject, (4) Marks for each subject (Out of 100)

● After inserting all the above information it should display the list of subjects with their
names, gained marks and total marks.

● At the end of this list there should be two options: (1) Show Percentage, (2) Show
Grades (decide criteria for grades yourself), (3) Show Status (Passed or Failed)

Part III: Show Result

● On selecting the “Show Percentage” option, show the result in percentage.

● Similarly for the “Show Grades” and “Show Status”
