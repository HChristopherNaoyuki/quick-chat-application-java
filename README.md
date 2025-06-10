# QuickChat Application

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Technical Architecture](#technical-architecture)
4. [Installation](#installation)
5. [Usage](#usage)
6. [Configuration](#configuration)
7. [File Structure](#file-structure)
8. [Dependencies](#dependencies)
9. [Development Roadmap](#development-roadmap)
10. [Contributing](#contributing)
11. [License](#license)

## Overview

QuickChat is a lightweight Java-based desktop chat application that provides secure messaging between registered users. Designed with simplicity in mind, it offers core chat functionality while maintaining robust data validation and persistence.

Key Characteristics:
- 100% Java implementation
- Swing-based graphical interface
- Local message persistence
- User authentication system
- Allman-style code formatting

## Features

### Authentication System
- User registration with validation:
  - Username requirements (must contain underscore, ≤5 chars)
  - Password complexity (8+ chars with uppercase, number, special char)
  - South African cell number validation (+27XXXXXXXXX format)
- Secure login functionality
- Session management

### Messaging
- Message composition with 250-character limit
- Recipient selection from registered users
- Message history persistence (JSON format)
- Real-time message display
- Message status tracking (Sent/Received)

### User Interface
- Clean, intuitive interface
- Login/registration panel
- Chat panel with:
  - Message display area
  - Recipient dropdown
  - Message input field
- Informational dialogs and error messages

## Technical Architecture

### Design Patterns
- Model-View-Controller (MVC) pattern
- Interface-based design for UI components
- Separation of concerns principle

### Core Components
| Component | Responsibility |
|-----------|---------------|
| `ChatApplication` | Main controller coordinating all operations |
| `Login` | Handles user authentication and validation |
| `Message` | Manages message creation, validation and persistence |
| `UserInterface` | GUI contract defining UI behavior |

### Data Flow
1. User authenticates via Login panel
2. System validates credentials
3. Upon success, loads Chat interface
4. User selects recipient and composes message
5. System validates and sends message
6. Message stored in JSON file and displayed

## Installation

### Prerequisites
- Java JDK 11 or later
- Maven 3.6.3 or later

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/HChristopherNaoyuki/quick-chat-application-java.git
   cd quick-chat-application-java
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   java -jar target/quickchat-1.0.jar
   ```

## Usage

### First-Time Setup
1. Launch the application
2. Register a new user:
   - Username: e.g., "a_bcd"
   - Password: e.g., "Pass123!"
   - Phone: e.g., "+27821234567"

### Normal Operation
1. Login with registered credentials
2. Select recipient from dropdown
3. Type message in input field
4. Press Enter or click Send button
5. View conversation history in chat area

### Demo Mode
The application includes a demo user:
- Username: `admin`
- Password: `Pass123!`

## Configuration

### Environment Variables
| Variable | Purpose | Default |
|----------|---------|---------|
| `QC_MSG_PATH` | Message storage path | `messages.json` |
| `QC_MAX_MSG` | Max messages to display | `50` |

### Runtime Options
Run with custom message path:
```bash
java -DQC_MSG_PATH=custom_path.json -jar quickchat.jar
```

## File Structure

```
quickchat/
├── src/
│   ├── main/
│   │   ├── java/solution/
│   │   │   ├── ChatApplication.java  # Main controller
│   │   │   ├── Login.java            # Authentication
│   │   │   ├── Message.java          # Message handling
│   │   │   ├── MessageStatus.java    # Status enum
│   │   │   ├── UserInterface.java    # UI contract
│   │   │   └── Solution.java         # Entry point
│   │   └── resources/                # Future resource files
├── target/                           # Build output
├── messages.json                     # Message storage
├── pom.xml                           # Maven config
└── README.md                         # This file
```

## Dependencies

This application uses pure Java with no external dependencies beyond:
- Java SE Development Kit (JDK 11+)
- Java Swing (included in JDK)

## Development Roadmap

### Planned Features
- [ ] Message encryption
- [ ] Contact management
- [ ] Group chats
- [ ] Message search
- [ ] UI themes

### Technical Improvements
- [ ] Unit test coverage
- [ ] Database integration
- [ ] Network communication
- [ ] Internationalization

## Contributing

We welcome contributions! Please follow these guidelines:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (following Allman style)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

### Coding Standards
- Strict Allman style formatting
- Javadoc for all public methods
- Clear, descriptive variable names
- Comprehensive error handling

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
