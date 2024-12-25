# Basic Spring Security Application with PostgreSQL

This is a basic Spring Boot application demonstrating Spring Security integration with a PostgreSQL database. The application provides authentication functionality using Spring Security's `UserDetailsService` and `DaoAuthenticationProvider`. The database is used to store user credentials.

## Features

- Stateless Authentication using HTTP Basic.
- Custom implementation of `UserDetailsService`.
- Database integration with PostgreSQL.
- Secure endpoints with authentication.

---

## Getting Started

### Prerequisites

1. **Java**: JDK 17 or higher installed.
2. **Spring Boot**: Knowledge of Spring Boot basics.
3. **PostgreSQL**: A running PostgreSQL instance with a database configured.
4. **Maven**: Build tool installed.

---

### Installation

1. Clone this repository:
    ```bash
    git clone <repository-url>
    cd <repository-name>
    ```

2. Set up your PostgreSQL database:
    - Create a database (e.g., `spring_security_db`).
    - Create a `users` table with the following structure:
      ```sql
      CREATE TABLE users (
          id SERIAL PRIMARY KEY,
          username VARCHAR(50) UNIQUE NOT NULL,
          password VARCHAR(255) NOT NULL
      );
      ```
    - Insert a test user:
      ```sql
      INSERT INTO users (username, password) VALUES ('id','testuser', 'password');
      ```

3. Update the `application.properties` file with your PostgreSQL configuration:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/spring_security_db
    spring.datasource.username=<your-database-username>
    spring.datasource.password=<your-database-password>
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

4. Build the application:
    ```bash
    mvn clean install
    ```

5. Run the application:
    ```bash
    mvn spring-boot:run
    ```

---

## Endpoints

### Secured Endpoint
- **`GET /home`**: Accessible only to authenticated users. Returns a list of students.
  - Example Response:
    ```json
    [
      { "id": 1, "name": "Navin", "marks": 60 },
      { "id": 2, "name": "Rahul", "marks": 64 }
    ]
    ```

---

## Project Structure

```
src/main/java
├── telusko/SpringSecurtiyLearning
│   ├── config
│   │   └── SecurityConfig.java
│   ├── controller
│   │   └── StudentController.java
│   ├── model
│   │   ├── UserPrincipal.java
│   │   └── Users.java
│   ├── repo
│   │   └── UserRepo.java
│   ├── service
│   │   └── MyUserDetailsService.java
├── resources
│   ├── application.properties
```

---

## Key Components

### 1. **`Users` Entity**
Defines the `users` table structure:
```java
@Entity
public class Users {
    @Id
    private int id;
    private String username;
    private String password;
    // Getters, Setters, toString()
}
```

### 2. **`UserPrincipal`**
Implements `UserDetails` for custom user data handling:
```java
public class UserPrincipal implements UserDetails {
    private Users users;
    // Custom implementation of UserDetails methods
}
```

### 3. **`MyUserDetailsService`**
Loads user details from the database:
```java
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
```

### 4. **`SecurityConfig`**
Configures Spring Security:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(request -> request.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
```

### 5. **`StudentController`**
Exposes secured REST API endpoints:
```java
@RestController
public class StudentController {
    private List<Student> students = new ArrayList<>(
        List.of(new Student(1, "Navin", 60), new Student(2, "Rahul", 64))
    );

    @GetMapping("/home")
    public List<Student> getStudent() {
        return students;
    }
}
```

---

## Tools and Technologies

- **Spring Boot**: Framework for application development.
- **Spring Security**: Authentication and security features.
- **PostgreSQL**: Database for storing user information.
- **Maven**: Dependency management and build tool.

---

## Future Enhancements

- Encrypt passwords using `BCryptPasswordEncoder`.
- Add role-based access control (RBAC).
- Implement JWT-based authentication.
- Create a registration endpoint for new users.

---

## Contributing

1. Fork the repository.
2. Create a feature branch.
3. Commit your changes.
4. Push to the branch.
5. Open a pull request.

---

## License

This project is licensed under the MIT License.

---

## Author

Rahul R. Nair

