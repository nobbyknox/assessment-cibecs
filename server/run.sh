#!/bin/bash

export targetDir="store"
export tcpServerPort="9050"

mvn exec:java -Dexec.mainClass="com.nobbyknox.cibecs.server.Server"
