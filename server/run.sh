#!/bin/bash
mvn exec:java -Dexec.mainClass="com.nobbyknox.cibecs.server.Server"

# Rename to "app.sh"
# Add the following goals:
# - publish: "mvn install" to publish jar to local .m2 repo
