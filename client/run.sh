#!/bin/bash

export accountCode="customer001"
export sourceDir="projectX"
export tcpServerHost="127.0.0.1"
export tcpServerPort="9050"

mvn exec:java -Dexec.mainClass="com.nobbyknox.cibecs.client.Client"
