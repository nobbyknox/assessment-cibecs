#!/bin/bash

while read -r -a myArray
do
    # echo "${myArray[0]}"
    # echo "${myArray[1]}"

    curl -X POST --header "Content-Type: application/json" -d "{ \"id\": ${myArray[0]} }" http://127.0.0.1:3005/v1/refresh-preview
    sleep 2;
done < links.txt
