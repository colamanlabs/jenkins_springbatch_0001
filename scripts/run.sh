#!/bin/bash

export APP_PATH=/home/colaman/WORKS/APPS/jenkins_springbatch_0001
export APP_FILE_NAME=jenkins_springbatch_0001-0.0.1-SNAPSHOT.jar

echo "[APP_PATH]" $APP_PATH
echo "[APP_FILE_NAME]" $APP_FILE_NAME

export PARAM_REQUEST_DATE=$1
echo "[PARAM_REQUEST_DATE]" $PARAM_REQUEST_DATE


cd $APP_PATH
java -jar ./$APP_FILE_NAME requestDate=$PARAM_REQUEST_DATE
