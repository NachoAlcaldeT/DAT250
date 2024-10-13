## DAT250: Assignment 7 - Software Technology Experiment

## Introduction

In this assignment, I worked with Docker for software containerization, aiming to understand both the use of official Docker images and the creation of my own images. The task is divided into two parts:

1. Use an official Docker image for PostgreSQL and configure it to work with a Java application.
2. Create my own Docker image for a Spring Boot application, packaging it into a container.

### Issues Encountered

During the assignment, I encountered the following issues:

- **Connection issues with PostgreSQL**: Initially, I incorrectly configured the environment variables, which caused authentication errors when trying to connect to the database. I resolved this by ensuring that the `POSTGRES_USER`, `POSTGRES_PASSWORD`, and `POSTGRES_DB` variables were properly set.
- **Dependency version conflicts**: When switching from the H2 driver to PostgreSQL in the Java project, I ran into some dependency issues. These were solved by adding the correct PostgreSQL dependency and adjusting the `persistence.xml` file.

In the end, I was able to successfully complete both parts of the assignment, including running unit tests using PostgreSQL and packaging the Spring Boot application into a functional Docker container.

---

## Part 1: Using a Dockerized PostgreSQL Image

### Configuration

First, I ensured Docker was installed and running by verifying with the following command:

```bash
docker system info
```

### Downloading and Running the PostgreSQL Image

I pulled the official PostgreSQL image from Docker Hub using the following command:

```bash
docker pull postgres
```

Next, to start a PostgreSQL container, I used the `docker run` command with the following arguments:

```bash
docker run -p 5432:5432 \
  -e POSTGRES_USER=jpa_client \
  -e POSTGRES_PASSWORD=secret \
  -e POSTGRES_DB=postgres \
  -d --name my-postgres --rm postgres
````

- `-p 5432:5432`: Maps the container's port to the host.
- `-e POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB`: Environment variables to set the user, password, and initial database.
- `-d`: Runs the container in detached mode.
- `--name my-postgres`: Assigns a name to the container.
- `--rm`: Automatically removes the container when stopped.

### Verifying the Container Status

To verify that the container was running, I used the following command:

```bash
docker ps
```


### Checking the Container Logs

To check the logs and ensure PostgreSQL was running properly, I executed:

```bash
docker logs my-postgres
```


### Connecting to PostgreSQL via psql

To connect to PostgreSQL using the psql client, I used the following command:

```bash
psql -h 127.0.0.1 -p 5432 -U jpa_client -d postgres
```

I used the credentials configured earlier (jpa_client, secret) here.

### Java Application Configuration

I switched from H2 to PostgreSQL in the Java project by adding the following dependency to `build.gradle.kts`:

```bash
implementation("org.postgresql:postgresql:42.7.4")
```

I then modified the `persistence.xml` file to use PostgreSQL:

```bash
<property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
<property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
<property name="hibernate.connection.url" value="jdbc:postgresql://127.0.0.1:5432/postgres"/>
<property name="hibernate.connection.username" value="jpa_client"/>
<property name="hibernate.connection.password" value="secret"/>
```

## Test Results

After making all the necessary adjustments to the PostgreSQL configuration and switching from H2 to PostgreSQL, I proceeded to run my unit tests. **Initially, the tests failed** because the tables required by JPA did not exist in the database.

The error I received indicated that the tables were not defined. This issue occurred because I had not enabled automatic schema generation in the initial `persistence.xml` configuration.

![Screenshot of First Test Run](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment7/Screenshots_Assignment7/Screenshot4_Docker.png)

### Problem Resolution

To resolve this issue, I added the following properties to the `persistence.xml` file, which allowed the necessary SQL scripts for creating and dropping the tables to be generated:

```xml
<property name="jakarta.persistence.schema-generation.scripts.action" value="drop-and-create"/>
<property name="jakarta.persistence.schema-generation.scripts.create-target" value="schema.up.sql"/>
<property name="jakarta.persistence.schema-generation.scripts.drop-target" value="schema.down.sql"/>
```

### SQL Script Generation

By adding these lines, two files were generated: `schema.up.sql` and `schema.down.sql`. These files contain the SQL statements needed to create (and drop) the tables that my JPA data model expects.

Once these files were generated, the next step was to apply the SQL statements manually in the database using an SQL client.

### Manually Applying the Scripts

I used my SQL client to connect to the PostgreSQL database and execute the `CREATE TABLE` statements from the `schema.up.sql` file. This created the required tables in the database.

### Successful Second Test Run

After manually creating the tables in the database, I ran the unit tests again. This time, the tests passed successfully, confirming that the configuration was now working as expected with PostgreSQL instead of H2.

![Screenshot of Second Test Run](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment7/Screenshots_Assignment7/Screenshot5_Docker.png)

---

## Part 2: Building My Own Dockerized Application

In this part of the task, I worked on containerizing my Spring Boot application from a previous experiment. The objective was to package the application into a Docker image, which would simplify sharing the app with other developers and eventually allow for cloud deployment.

## Step 1: Setting Up the Dockerfile

I started by creating a `Dockerfile` in the root of my Spring Boot project. I used a multi-stage build to keep the image size smaller and ensure the final container runs efficiently.

Here is a breakdown of the steps in my `Dockerfile`:

1. **Selecting a Base Image**  
   I chose the official `gradle` image as the base for the build stage, and `temurin` (for Java runtime) for the final image.
   
2. **Building the Application**  
   The first stage uses `gradle` to build the application and generate the necessary `.jar` file using the `bootJar` task.
   
3. **Creating a Minimal Runtime**  
   The second stage uses a lightweight `temurin` image to copy the built `.jar` file from the first stage. This helps avoid unnecessary dependencies in the final image.

## Step 2: Building the Docker Image

After setting up the Dockerfile, I ran the following command to build the Docker image:

```bash
docker build -t my-spring-boot-app
```

This command created an image tagged as `my-spring-boot-app`, ready to be used for running the application in a container.

## Step 3: Running the Docker Container

Once the image was built, I started the Spring Boot application within a Docker container by running the following command:

```bash
docker run -p 8080:8080 my-spring-boot-app
```

This command mapped the container’s port 8080 to my local machine’s port 8080, allowing me to access the application via `localhost:8080`.

## Step 4: Testing the Application

To verify that the application was working correctly, I tested it using a web browser. I used **Bruno** as my API testing tool and accessed the application at `http://localhost:8080`. 

![Screenshot of testing the Application](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment7/Screenshots_Assignment7/Screenshot6_Docker.png)

The test confirmed that the Spring Boot application was running successfully, and everything was functioning as expected.

