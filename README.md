# README

This repository serves as my assessment submission to Cibecs, as requested on 3 May 2019.

## Requirements

The following is required in order to run the assessment project:

* Internet connection for the download of Maven dependencies
* Maven
* Java 8 at a minimum

## Getting Started

There are three ways that you can run this project. The first is from the command line with the scripts provided. The second from your IDE, which is also helpfull if you're running on Windows. The third is through Docker.

### Run From the Command Line

These instructions as well as the scripts provided in the project are for Linux or MacOS based systems. Running WSL on Windows will also work.

Follow along with these easy steps:

1. Clone the project to your local PC or laptop:
    ```
    git clone https://github.com/nobbyknox/assessment-cibecs.git
    ```
2. Compile and install the `commons` component into your local `.m2` (maven) repository. This component provides shared resources that both the server and client components rely on.
    ```
    cd commons
    mvn install
    ```
3. Once the `commons` component has been installed, compile and run the server component.
    ```
    cd server
    ./run.sh
    ```
4. With the `server` component running, do the same for the `client` component.
    ```
    cd client
    ./run.sh
    ```

## Running Through Your IDE

This is probably the easiest way to run the project if you are using Windows.

To run the server, configure the following environment variables in your IDE and run the `Server` class.

| Variable      | Value  |
| :------------ | :----- |
| targetDir     | store  |
| tcpServerPort | 9050   |

Configure the client component with the following environment variables and run the `Client` class.

| Variable        | Value       |
| :-------------- | :---------- |
| accountCode     | customer001 |
| sourceDir       | projectX    |
| tcpServerHost   | 127.0.0.1   |
| tcpServerPort   | 9050        |

Please remember that you still have to install the `commons` component in your local maven repository. Refer to the section above for details.

## Running as Docker Containers

First, we need to create a network:

```bash
docker network create cibecs
```

With this out of the way, we can proceed to build and run the `server` and `client` components.

### Server

Build the image from the project root:

```bash
docker build -f dockerfile-server -t cibecs-server:latest .
```

Run the server:

```bash
docker run --name cibecs-server --network=cibecs -e targetDir="store" -e tcpServerPort="9050" cibecs-server
```

### Client

Build the image from the project root:

```bash
docker build -f dockerfile-client -t cibecs-client:latest .
```

Run the client:

```bash
docker run --name cibecs-client --network=cibecs -e accountCode="customer001" -e sourceDir="projectX" -e tcpServerHost="cibecs-server" -e tcpServerPort="9050" cibecs-client
```

Once the client uploaded its files to the server, enter the server container and verify that the files were copied:

```bash
docker exec -ti cibecs-server bash
cd /usr/src/app/server/store
ls -lR
```

To clean up, stop both running containers and remove them:

```bash
# remove the containers
docker rm cibecs-server cibecs-client

# remove the network
docker network rm cibecs
```

## How it Works

The project builds a tree graph of a specific directory (configured in client component). This graph is then submitted to the server via a TCP socket connection. Upon receipt of the graph, the server requests each file individually from the client and saves the files in the `store` directory, using the customer's account code as parent directory, in exactly the same directory structure as on the client.

In short, files from the `${PROJECT_ROOT}/client/projectX/` directory is copied to `${PROJECT_ROOT}/server/store/${ACCOUNT_CODE}/` on start-up of the client component.

You will notice that the client files are stored on the server under the client's unique account code. This makes the system multi-tenanted.

## Configuration

Run of the mill projects are configured with properties files, like they have been for years. This project was build with an eye on being deployable through Docker and for this reason the configuration was moved to environment variables, as this is the industry standard way of configuring containers.

The shell script `run.sh` for both the `client` and `server` components may be edited and configured to suite your needs. The project has sensible defaults, so you do not have to make any configuration changes.

## Limitations

The author is aware of the following limitations:

* In its current state, the server is unable to accept repeat connections after the initial file upload process completes.
* It is unknown how well the system will cope with large files. The largest file tested was 76 MB and it transferred successfully.
* It is unknown how the system will run on Java version 9 or higher. It was only tested with Java 8.

## Parting Thoughts

I had great fun with this project and it required that I brush up on some aspects of Java that I have not use recently. Nevertheless, I think that I met the brief and trust that this assessment will receive a favourable review.
