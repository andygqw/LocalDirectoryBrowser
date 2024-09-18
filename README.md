# Spring Boot Media Browsing Service

This project provides a media streaming service using **Spring Boot**, with the capability to stream video files over HTTP. It supports both full and partial content (byte-range requests), making it suitable for mobile and desktop browsers. The service can be accessed across the local network and is compatible with mobile devices.

## Frontend

Click [here](https://github.com/andygqw/LocalMediaStreamer) to see corresponding React frontend project.

## Features

- **Stream videos via HTTP** with support for HTTP Range requests.
- **Supports multiple media types**, including video, PDF, and more streaming.
- **Responsive video player** in React for a mobile-friendly UI.
- **Remote access** to the Spring Boot application on the local network.

---

## Prerequisites

Before you begin, ensure you have the following software installed on your machine:

- [Java 17 or later](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/) (for managing dependencies)

---

## Project Structure

### Backend (Spring Boot)

- **Video and PDF streaming services**: Located in the `service` package.
- **Controller**: The `MediaStreamingController` handles HTTP requests and selects the appropriate streaming strategy.
- **Strategy Pattern**: Applied for streaming different types of media (video, PDF).

### Frontend (React)

- **Responsive video player**: A React component that leverages the native `<video>` HTML5 element for mobile-friendly video playback.

---

## Setup Instructions

Build and run the Spring Boot application:

```bash
mvn clean install
mvn spring-boot:run
```

---

## Security Considerations

When exposing your application over a network, consider the following:

1. **Use HTTPS**: Ensure that the service is running over a secure connection, especially when transmitting sensitive data.
2. **Firewall and Network Access Control**: Only allow specific IP addresses to access the service by configuring firewall rules.
3. **Authentication**: Implement authentication mechanisms to restrict access to video streaming endpoints.

---

## License

This project is licensed under the MIT License

---

## Contributing

Feel free to open issues or submit pull requests for improvements and bug fixes.
