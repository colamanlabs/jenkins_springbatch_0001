#!/bin/bash

export APP_FILE_NAME=jenkins_springbatch_0001-0.0.1-SNAPSHOT.jar
export SRC_PATH=/var/lib/jenkins/workspace/jenkins_springbatch_0001/target/$APP_FILE_NAME
export DEST_PATH=/home/colaman/WORKS/APPS/jenkins_springbatch_0001

export DATE_TIMESTAMP=`date +%Y%m%d_%H%M%S`
export APP_FILE_NAME_BACKUP=$APP_FILE_NAME.BACKUP.$DATE_TIMESTAMP

echo "[SRC_PATH]" $SRC_PATH
echo "[DEST_PATH]" $DEST_PATH
echo "[DATE_TIMESTAMP]" $DATE_TIMESTAMP
echo "[APP_FILE_NAME_BACKUP]" $APP_FILE_NAME_BACKUP
echo
echo

echo "BEGIN TASK"
# 1. APP 디렉토리로 이동
cd $DEST_PATH

# 2. 현재 파일을 백업한다. 
echo "1. execute backup"
cp -p -f $APP_FILE_NAME $APP_FILE_NAME_BACKUP
echo

# 3. 배포한다.
echo "2. deploy file"
cp -p $SRC_PATH $DEST_PATH
echo

echo "END TASK"
echo
