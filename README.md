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

## Getting Started

The instructions in this document as well as the scripts provided in the project are for Linux or MacOS based systems. Running WSL on Windows will also work. Some creativity on your part might be required if you try to run this on Windows.

This project is runnable with the smallest amount of effort on your part. 😀 Follow along with these easy steps:

1. Clone the project to your local PC or laptop:
    ```
    git clone https://github.com/nobbyknox/assessment-cibecs.git
    ```
2. Compile and install the `commons` component into your local `.m2` (maven) repository. This component provides shared resources that both the server and client components rely on.
    ```
    cd commons
    mvn install
    ```
3. Once the `commons` component installed successfully, compile and run the server component.
    ```
    cd server
    mvn compile
    ./run.sh
    ```
4. With the `server` component running, do the same for the `client` component.
    ```
    cd client
    mvn compile
    ./run.sh
    ```
## What it Does

The project builds a tree graph of a specified directory (client component). This graph is then submitted to the server via a TCP socket. The server then requests each file individually from the client and saves the files under the customer's account code in exactly the same directory structure as on the client.

Files from the `$PROJECT_ROOT/client/src/` directory is copied to `$PROJECT_ROOT/server/store/$ACCOUNT_CODE/` on start-up of the client component.

## Configuration

Run of the mill projects are configured with properties files, like they have been for a very long time. This project was build with an eye on being deployable through Docker and for this reason the configuration was moved to environment variables, as this is the industry standard way of configuring containers. The rule is that configuration is provided external to the container. An objective rarely achievable using properties files.

The shell script `run.sh` for both the `client` and `server` components may be edited and configured to suite your needs. The project has sensible defaults, so you do not have to make any configuration changes.

## Limitations

* No large files tested
