
# Task Manager

A simple Java-based event and task management application to help you organize and track events efficiently.  
Supports both interactive CLI and background daemon (reminder) modes, and is fully Dockerized for easy deployment on any system, including Raspberry Pi.

## Features

- Add and delete events with a name, event date, and reminder date
- List all scheduled events
- Persistent storage via CSV file
- Interactive CLI tool
- Background daemon mode for reminders
- Docker images for both CLI and daemon

## Getting Started

### Prerequisites

- **Java 17 or higher** (only if building/running manually)
- **Maven** (for manual builds)
- **Docker** (recommended for most users)

---

## Installation

### Manual Build & Run (No Docker)

1. **Clone the repository:**
    '''bash
    git clone https://github.com/ewanritchie85/task-manager.git
    cd task-manager
    '''

2. **Build the project:**
    '''bash
    mvn clean package
    '''

3. **Run the CLI application:**
    '''bash
    java -cp target/task-manager-app-1.0-SNAPSHOT.jar MainCLI
    '''
    **Or run the daemon:**
    '''bash
    java -cp target/task-manager-app-1.0-SNAPSHOT.jar MainDaemon
    '''

---

## Installation & Usage (Docker)

#### **Build Docker Images Locally (if not pulling from Docker Hub)**

'''bash
docker build -f Dockerfile.cli -t vinylritchie85/taskmanager-cli:latest .
docker build -f Dockerfile.daemon -t vinylritchie85/taskmanager-daemon:latest .
'''

#### **Run the CLI Tool**
Run interactively, mounting your data directory for persistence:
'''bash
docker run --rm -it -v /home/pi/task-manager-data:/app/data vinylritchie85/taskmanager-cli:latest
'''

#### **Run the Daemon**
Start the background reminder daemon (in detached mode), also mounting your data:
'''bash
docker run -d --name taskmanager-daemon \
  -v /home/pi/task-manager-data:/app/data \
  vinylritchie85/taskmanager-daemon:latest
'''

**Note:**  
Change /home/pi/task-manager-data to wherever you want your CSV/event data to be stored.  
All event data will persist in this directory on the host.

---

## Usage

- **CLI:**  
  Follow the on-screen prompts to add, view, and delete events interactively.

- **Daemon:**  
  Runs in the background, checking for events and reminders automatically.

---

## Automation & CI/CD

This project is set up to build and push Docker images using GitHub Actions.  
On your Raspberry Pi, you can use a self-hosted GitHub Actions runner to automatically deploy and update the daemon container.

---

## Contributing

Contributions are welcome! Please open issues or submit pull requests.

## License

This project is licensed under the MIT License.