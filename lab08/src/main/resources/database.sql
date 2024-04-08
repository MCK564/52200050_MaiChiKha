CREATE DATABASE LAB08;
       USE LAB08;
           CREATE TABLE Employees (
        EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
         Name VARCHAR(255) NOT NULL,
        Email VARCHAR(255),
        Address VARCHAR(255),
         Phone VARCHAR(20)
  );
INSERT INTO Employees (Name, Email, Address, Phone) VALUES
                                                        ('John Doe', 'john.doe@example.com', '123 Main St, City, Country', '1234567890'),
                                                        ('Jane Smith', 'jane.smith@example.com', '456 Elm St, City, Country', '4567890123'),
                                                        ('Michael Johnson', 'michael.johnson@example.com', '789 Oak St, City, Country', '7890123456'),
                                                        ('Emily Brown', 'emily.brown@example.com', '987 Pine St, City, Country', '9876543210'),
                                                        ('David Wilson', 'david.wilson@example.com', '654 Maple St, City, Country', '6543210987'),
                                                        ('Jennifer Lee', 'jennifer.lee@example.com', '321 Cedar St, City, Country', '3210987654'),
                                                        ('Christopher Martinez', 'christopher.martinez@example.com', '555 Birch St, City, Country', '5551234567'),
                                                        ('Amanda Taylor', 'amanda.taylor@example.com', '777 Spruce St, City, Country', '7774567890'),
                                                        ('James Anderson', 'james.anderson@example.com', '888 Walnut St, City, Country', '8887890123'),
                                                        ('Jessica Thomas', 'jessica.thomas@example.com', '999 Sycamore St, City, Country', '9990123456'),
                                                        ('Matthew Garcia', 'matthew.garcia@example.com', '111 Pineapple St, City, Country', '1119876543'),
                                                        ('Sarah Hernandez', 'sarah.hernandez@example.com', '222 Banana St, City, Country', '2228765432'),
                                                        ('Daniel King', 'daniel.king@example.com', '333 Orange St, City, Country', '3337654321'),
                                                        ('Laura Martinez', 'laura.martinez@example.com', '444 Grape St, City, Country', '4446543210'),
                                                        ('Andrew Adams', 'andrew.adams@example.com', '555 Lemon St, City, Country', '5555432109'),
                                                        ('Elizabeth Wright', 'elizabeth.wright@example.com', '666 Lime St, City, Country', '6664321098'),
                                                        ('Kevin Lopez', 'kevin.lopez@example.com', '777 Pear St, City, Country', '7773210987'),
                                                        ('Megan Hill', 'megan.hill@example.com', '888 Peach St, City, Country', '8882109876'),
                                                        ('Justin Scott', 'justin.scott@example.com', '999 Plum St, City, Country', '9990987654'),
                                                        ('Rachel Green', 'rachel.green@example.com', '123 Cherry St, City, Country', '1235432109');



