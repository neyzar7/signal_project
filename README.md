# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## System Design & UML Models

This repository includes UML class diagrams modeling the core subsystems of the Cardiovascular Health Monitoring System (CHMS). These design blueprints focus on modularity, separation of concerns, and system architecture for a simulated hospital environment.

The modeled subsystems include:
* **Alert Generation System:** Evaluates real-time patient data against personalized thresholds to trigger alerts.
* **Data Storage System:** Handles the storage and retrieval of time-stamped patient vitals.
* **Patient Identification System:** Matches incoming simulator data streams to registered hospital patient records.
* **Data Access Layer:** Abstracts incoming external data sources (TCP, WebSocket, File) into standardized system inputs.

You can view the diagrams and their accompanying design documentation in the [uml_models](./uml_models) directory.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Project Members

- Student ID:6399041
- Student ID:6372467
