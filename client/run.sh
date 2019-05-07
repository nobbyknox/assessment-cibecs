#!/bin/bash
mvn exec:java -Dexec.mainClass="com.nobbyknox.cibecs.client.Client" -Dtcp.server.host=127.0.0.1 -Dtcp.server.port=9050
