#!/bin/bash

set -e

echo "===================================="
echo "Deployment"
echo "===================================="

kubectl get deployment -n java-webapp

echo "===================================="
echo "Pods"
echo "===================================="

kubectl get pods -n java-webapp

echo "===================================="
echo "Service"
echo "===================================="

kubectl get svc -n java-webapp

echo "===================================="
echo "Ingress"
echo "===================================="

kubectl get ingress -n java-webapp