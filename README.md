Order Management System

This is a Java desktop application I built for my Programming Techniques course. The idea was to create a simple system for managing clients, products and orders.
The application has a graphical interface made with Java Swing and uses PostgreSQL to store the data.
I made this project to practice working with databases in Java and to better understand how to organize an application into different layers.
It also helped me learn more about JDBC, reflection, generic classes and building graphical interfaces with Swing.

Features

Add, edit, delete and view clients and products.
Manage product prices and available stock.
Create orders by selecting a client, a product and a quantity.
Check if there is enough stock before placing an order.
Automatically update the stock after an order.
Save bill information for each order.
Display orders and calculate their total value.

Technologies Used

Java 
Java Swing
PostgreSQL
JDBC
Maven

Project Structure

The application is organized into several layers: presentation, business logic, data access and models.
I used a generic DAO class and Java Reflection to handle database operations without writing the same code for every entity.
The repository also includes a UML diagram and the SQL script needed to create the database tables.
