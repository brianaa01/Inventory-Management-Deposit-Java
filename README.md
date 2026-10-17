# Inventory Management System (Java Desktop App)

This project is a desktop application developed in **Java (Swing)** for managing warehouse inventory. It was created to practically apply Object-Oriented Programming (OOP) principles, the MVC architecture, and relational database integration.

## Architecture & OOP Concepts
The application adheres to clean software design principles:
- **Model-View-Controller (MVC):** Clear separation between the graphical user interface (`DepozitView`), business logic (`DepozitController`), and data persistence.
- **Inheritance & Polymorphism:** Products are modeled using a class hierarchy (the base class `Produs` extended by `Mobila`, `Electronic`, `Consumabil`).
- **Interfaces (Contracts):** Utilization of interfaces such as `Cautabil` and `Stocabil` to decouple components.
- **Exception Handling:** Custom exceptions (e.g., `StocInsuficientException`) to prevent errors during inventory transactions.

## Core Features
- **Product Management:** Adding, searching, and removing products from the warehouse across various categories.
- **Data Persistence:** Connection to a **PostgreSQL** database via JDBC (`DataBaseManager`).
- **SQL Automation:** The database structure utilizes **Triggers** and functions (`actualizeaza_stoc()`) to automatically calculate quantities (IN/OUT) for every transaction directly at the backend level.

## Technologies Used
- **Language:** Java (Swing GUI)
- **Concepts:** OOP, MVC, Custom Exceptions
- **Database:** PostgreSQL (JDBC, Triggers, Functions)

## Project Structure
- `src/` - Contains the modularly organized Java source code.
- `baza_de_date.sql` - The complete SQL script for recreating tables, relationships (Foreign Keys), and automatic triggers.
