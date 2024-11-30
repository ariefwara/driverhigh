# DriverHigh  

**DriverHigh** is a high-performance web framework for building APIs that leverage parallel task execution to optimize performance. Powered by the **Drift Framework**, it ensures efficient processing by enabling developers to handle independent tasks concurrently while maintaining clean and intuitive code.

---

## Features  

- **Parallel Task Execution**: Execute unrelated tasks simultaneously for maximum efficiency.  
- **Seamless Integration with Drift**: Leverages the powerful Drift framework for task orchestration.  
- **Request Handling**: Easily manage HTTP parameters and headers using the `Request` object.  
- **Intuitive API**: A clean and developer-friendly syntax for building scalable systems.  

---

## Installation  

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>id.levalapp.driverhigh</groupId>
    <artifactId>driverhigh-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```

Update your Maven dependencies:

```bash
mvn clean install
```

---

## Usage  

### Example: Parallel API Workflow

```java
package id.levalapp.driverhigh;

import id.levalapp.drift.Drift;

public class Example {
    public static void main(String[] args) {
        DriverHigh engine = new DriverHigh(8080);

        engine.get("parallel/example", (drift) -> {
            return new Response(200, drift
                .shift(
                    () -> {
                        String userId = drift.getRequest().getParam("userId");
                        System.out.println("Task 1: Fetching user details for userId: " + userId);
                        simulateDelay(1000);
                        return "User Details for " + userId;
                    },
                    () -> {
                        String userId = drift.getRequest().getParam("userId");
                        System.out.println("Task 2: Fetching account details for userId: " + userId);
                        simulateDelay(1000);
                        return "Account Balance: 5000";
                    },
                    () -> {
                        String userId = drift.getRequest().getParam("userId");
                        System.out.println("Task 3: Fetching recommendations for userId: " + userId);
                        simulateDelay(1000);
                        return "Recommendations: Buy more tech books";
                    }
                )
                .swift((userDetails, accountDetails, recommendations) -> {
                    System.out.println("Combining results");
                    return "Response: " + userDetails + ", " + accountDetails + ", " + recommendations;
                })
            );
        });

        engine.ignite();
    }

    private static void simulateDelay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

---

## Workflow  

1. **Engine Initialization**:
   - Create a new `DriverHigh` instance with the desired port number.

2. **Endpoint Definition**:
   - Use HTTP method handlers (e.g., `get`) to define routes and attach workflows.

3. **Drift Workflow**:
   - Use `shift` to define parallel tasks for unrelated processes.
   - Use `swift` to combine results and produce the final response.

4. **Server Start**:
   - Call `ignite` to start the server.

---

## Output  

For the above example, when hitting **GET /parallel/example?userId=123**, the following is logged:

```
Task 1: Fetching user details for userId: 123
Task 2: Fetching account details for userId: 123
Task 3: Fetching recommendations for userId: 123
Combining results
```

And the client receives:

```http
HTTP/1.1 200 OK
Content-Type: application/json

Response: User Details for 123, Account Balance: 5000, Recommendations: Buy more tech books
```

---

## Why DriverHigh?  

DriverHigh provides a simple yet powerful approach to building high-performance APIs by focusing on parallelism. Its integration with the Drift framework ensures developers can easily handle complex workflows while optimizing performance and maintaining code clarity.

---

## License  

This project is licensed under the MIT License.