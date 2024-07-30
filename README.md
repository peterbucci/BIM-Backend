# BHCC Instant Messenger (BIM) Backend

## Overview

This is the backend for the messenger App. It uses Spring Boot and MySQL to manage user data, friendships, and messages. You can find the frontend [here](https://github.com/peterbucci/Bim-Frontend)

## Prerequisites

- Java Development Kit (JDK)
- Docker
- Gradle

### Installing Gradle

#### On Unix-like Systems (Linux, macOS)

1. **Using SDKMAN** (recommended):

   ```sh
   curl -s "https://get.sdkman.io" | bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   sdk install gradle
   ```

2. **Using Homebrew** (macOS):

   ```sh
   brew install gradle
   ```

#### On Windows

1. **Using SDKMAN for Windows** (requires Git Bash):

   ```sh
   curl -s "https://get.sdkman.io" | bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   sdk install gradle
   ```

#### Manual Installation

- Download the binary from the [Gradle Releases](https://gradle.org/releases/).
- Unzip the downloaded file.
- Add the `bin` directory to your `PATH`.

## Configuration

The application uses a `.env` file for configuration. You need to create this file in the project root directory and define the following environment variables:

### Example `.env` File

Create a `.env` file in the project root directory and add the following content:

```dotenv
MYSQL_DATABASE={MYSQL_DATABASE}
MYSQL_ROOT_PASSWORD={MYSQL_ROOT_PASSWORD}
MYSQL_USER={MYSQL_USER}
MYSQL_PASSWORD={MYSQL_PASSWORD}
SPRING_DATASOURCE_URL=jdbc:mysql://{host}:{port}/{MYSQL_DATABASE}
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD={MYSQL_ROOT_PASSWORD}
```

This configuration file provides the necessary database connection details for the application.

## Setting Up the Database

The database is automatically created when you build the project. It is set up in a Docker container.

## Building and Running the Application

### Using Gradle

1. **Navigate to the Project Directory:**
   Open your terminal (or Command Prompt on Windows) and navigate to the root directory of your project.

2. **Build the Project:**
   Ensure your project is built. You can do this with the `build` task:

   ```sh
   ./gradlew build
   ```

   On Windows, use:

   ```cmd
   gradlew.bat build
   ```

3. **Run the Application:**
   Start the application using the `bootRun` task:
   ```sh
   ./gradlew bootRun
   ```
   On Windows, use:
   ```cmd
   gradlew.bat bootRun
   ```

### Running the Docker Containers

- Make sure Docker is installed and running on your system.
- This will start the necessary containers, including the MySQL database container.

## Additional Information

- Make sure your Docker daemon is running before executing any tasks.
- The application will automatically create the required database and tables on startup.
- You can access the application at `http://localhost:8080`.

For any issues or contributions, please refer to the repository on [GitHub](https://github.com/peterbucci/BIM-Backend).
