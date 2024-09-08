# DAT250 Experimental Assignment 2 - Report

## Created Controllers

- **VoteOptionController**: Manages vote options with endpoints for creating, updating, deleting, and retrieving all options.
- **VoteController**: Manages individual votes, including creation, updating, and deletion of votes.
- **UserController**: Controls CRUD operations for users, such as creation, updating, and deletion of users.
- **PollController**: Manages polls, including creation, updating, deletion, and retrieval of polls by ID.
  
## Technical Issues Encountered

1. **Dependency Management**:
   There were issues with Spring Boot dependencies in the `pom.xml` file. After adjusting versions and adding missing packages, the issue was resolved.

2. **Connection Errors When Making HTTP Requests**:
   When attempting to run the application and make HTTP requests, we encountered connection errors. The server seemed to be running, but the connection could not be established. Upon investigation, we discovered that the configured port was causing conflicts. We adjusted the port in the configuration file (`application.properties`), and the problem was resolved, allowing for a successful connection.

3. **Dependency Injection Issues**:
   The `PollManager` class was not being injected properly due to the absence of the `@Component` annotation. Adding this annotation resolved the issue.

4. **Incorrect Mappings in Controllers**:
   There were some errors with `@PathVariable` in the controllers, which prevented access to the endpoints correctly. This was resolved by reviewing and correcting the annotations in the controller methods.

## Unresolved Issues

At this time, there are no pending issues. The application is functioning correctly, and all functionalities have been successfully tested.

## Conclusion

In conclusion, despite encountering some technical issues during the setup and configuration of the project, we were able to resolve all the challenges. Dependency errors, mapping issues, and port conflicts were addressed, and the system now manages polls, users, votes, and vote options seamlessly. This exercise has been an opportunity to strengthen skills in Spring Boot and RESTful API management.
