# README

This project serves as my assessment submission to Cibecs, as requested on 3 May 2019.

## Status

Busy...

## Notes

Some notes that you may want to include in the final main README file.

* Configuration is external to the system.
    - No properties
    - Follow SOLID principles (TODO: Get link)
    - Docker ready
    - Include documentation for the configuration of the system (both client and server)
* Java version 8 was embraced w.r.t. lambdas, "Optional", etc
* Multi-tenanted

## Requirements

The following is required in order to run the assessment project:

* Internet connection for the download of Maven dependencies
* Maven
* Java 8 at a minimum

## Getting Started

The instructions in this document as well as the scripts provided in the project are for Linux or MacOS based systems. Running WSL on Windows will also work. However, some creativity on your part will be required if you want to run this on Windows. Brief instruction on this is provided below.

This project is runnable with the least amount of effort on your part. 😀 Follow along with these easy steps:

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

## How it Works

The project builds a tree graph of a specific directory (configured in client component). This graph is then submitted to the server via a TCP socket connection. Upon receipt of the graph, the server then requests each file individually from the client and saves the files in the `store` directory, using the customer's account code as parent directory, in exactly the same directory structure as on the client.

In short, files from the `${PROJECT_ROOT}/client/projectX/` directory is copied to `${PROJECT_ROOT}/server/store/${ACCOUNT_CODE}/` on start-up of the client component.

// TODO: I don't like the following sentence
You will notice that the client files are stored on the server under the client's unique account code. This makes the system multi-tenanted.

## Configuration

Run of the mill projects are configured with properties files, like they have been for years. This project was build with an eye on being deployable through Docker and for this reason the configuration was moved to environment variables, as this is the industry standard way of configuring containers. The thinking behind this is that configuration is provided external to the container. An objective rarely achievable using properties files.

The shell script `run.sh` for both the `client` and `server` components may be edited and configured to suite your needs. The project has sensible defaults, so you do not have to make any configuration changes.

## Limitations

The author is aware of the following limitations:

* In its current state, the server is unable to accept repeat connections after the initial file upload process completes.
* It is unknown how well the system will cope with large files. The largest file tested was 76M.
* It is unknown how the system will run on Java version 9 or higher. It was only tested on Java 8.
