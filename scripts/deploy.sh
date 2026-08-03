#!/bin/bash

set -e

echo "===================================="
echo "Deploying new image"
echo "===================================="

kubectl set image deployment/java-webapp \
java-webapp=428847003845.dkr.ecr.ap-south-1.amazonaws.com/java-webapp:${BUILD_NUMBER} \
-n java-webapp

echo "===================================="
echo "Waiting for rollout"
echo "===================================="

kubectl rollout status deployment/java-webapp \
-n java-webapp