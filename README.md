### **Pizza Project with Spring Boot at TelRan (Starta University)**
![Project view](https://github.com/sergey-zabolotnyi/project-spring-pizza/blob/main/src/main/resources/static/pics/pizza_screenshot.png)

**Universal Technology Stack:**
*  Programming Language: Java (version 17)
*  Primary Framework: Spring Boot (versions from 2.7.*.RELEASE)
*  Database: MySQL
*  Database Migration: Liquibase
*  Testing: Mockito, JUnit 5
*  API Documentation: Swagger, Postman
*  Code Documentation: JavaDoc, AsciiDoc
*  Build Tool: Maven
*  Code Coverage Measurement: JaCoCo
*  Object Mapping: MapStruct
*  Persistence Framework: Hibernate and JPA
*  Logging: SLF4J
*  Version Control: GitHub
*  Documentation Tool: Word Office
*  Containerization: Docker (optional for deployment)

![Project view](https://github.com/sergey-zabolotnyi/project-spring-pizza/blob/main/src/main/resources/static/pics/BD.png)

**Project Objectives "Pizza":**
1. Integrated Management System Development: Creating a system that allows administrators to manage the pizzeria menu, as well as information about cafes where these pizzas are available.
2. Process Optimization: The main idea is to automate the processes of creating, reading, updating, and deleting (CRUD) pizza and cafe records. This can significantly speed up work processes.
3. Data Security: Ensuring the security of customer data and product information using modern authentication and authorization methods.
4. Extensibility: Creating a system in such a way that new features can be easily added or integrated with other systems in the future.
5. Scalability: Building an architecture to ensure the application can handle a large number of users simultaneously without failures.
6. Analytics and Reporting: Implementing tools for collecting statistics and analytics to help administrators understand the popularity of different pizzas, order frequency, and other key metrics.
7. Feedback: Allowing customers to leave reviews about pizza or cafes to improve the quality of services and offerings.
8. Integration with External Systems: (Optional) The ability to integrate with other systems or platforms, such as delivery systems or payment systems.
9. Interactive Interface: (Optional) Developing a simple and intuitive interface for customers to easily choose pizza and cafes.
10. Eco-Friendly Features: (Optional) Implementing features that allow customers to choose environmentally friendly pizza or cafes.
Project view

**Architecture**
1. Presentation Layer: This layer is responsible for the user interface, be it a web interface, mobile application, or API for third-party systems. It handles user input and displays data to the user.
2. Business Logic Layer: This layer contains the core business logic of the application. It processes data received from the presentation layer, performs necessary calculations or processing, and passes data to the next layer or returns it back to the presentation layer.
3. Data Access Layer: This layer is responsible for interacting with the database or other external data storage systems. It includes all CRUD (create, read, update, delete) operations and provides abstraction from the specific data storage system.
Additional Components:
4. Service Layer: May be added between the presentation layer and the business logic layer, especially if there is a need to implement business logic that should be accessible through multiple different interfaces.
5. Integration Layer: If the application integrates with external systems or services, this layer can provide the necessary abstraction and data transformation.
