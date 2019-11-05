# Classroom Manager
This is a basic Java Swing application for handling students in a classroom via the usage of SQL databases (users and students) and the JDBC API.

## Technologies Used
* Primary Language: Java
* GUI Specification: AWT & Swing
* IDE: Eclipse

## SQL Database Configuration
```sql
CREATE TABLE IF NOT EXISTS `users` (
  `UID` int(2) NOT NULL AUTO_INCREMENT,
  `UNAME` varchar(20) NOT NULL,
  PRIMARY KEY (`UID`)
);

CREATE TABLE IF NOT EXISTS `students` (
  `SID` int(2) NOT NULL AUTO_INCREMENT,
  `STUD_ID` int(8) NOT NULL,
  `FNAME` varchar(20) NOT NULL,
  `SNAME` varchar(20) NOT NULL,
  PRIMARY KEY (`SID`)
);
```

## Importing
* Clone the project, and import it to your preferred IDE.
* By default, the `mysql-connector-java.jar` file should be configured into the build path of the project, but otherwise; this but be added as a referenced library.
* Setup a local MySQL localhost server using the tables configuration from above.
* Ensure that your SQL database credentials are configured correctly onto the global SQL variables in `src/DatabaseController.java`.
* Now the Java program should run without any issues.