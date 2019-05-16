#!/bin/bash

# ------------------------------------------------------------------------------
# Specify our configuration via environment variables
# ------------------------------------------------------------------------------

export targetDir="store"
export tcpServerPort="9050"

# ------------------------------------------------------------------------------
# Copy dependencies locally and package the component
# ------------------------------------------------------------------------------

mvn dependency:copy-dependencies package

# ------------------------------------------------------------------------------
# Run it
# ------------------------------------------------------------------------------

cat logo.txt

java -cp \
target/dependency/secret001-commons-0.1.0-SNAPSHOT.jar:\
target/dependency/log4j-api-2.11.2.jar:\
target/dependency/log4j-core-2.11.2.jar:\
target/cibecs-server-0.1.0-SNAPSHOT.jar \
com.nobbyknox.secret001.server.Server
