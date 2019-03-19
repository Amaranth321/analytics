#!/usr/bin/env bash

FTP_HOST="sgftp.kaisquare.com"
FTP_PORT=21
FTP_USERNAME="sgftp"
##FTP_VCA_DIR=others/renzongke/vaaas4.7/vca  # create your own path for development
FTP_VCA_DIR=others/am/gradle_repo/vca
#echo -n "Enter sgftp password > "
#read -s FTP_PASSWORD

./gradlew build \
-Dftp_host=$FTP_HOST \
-Dftp_port=$FTP_PORT \
-Dftp_username=$FTP_USERNAME \
-Dftp_password=$FTP_PASSWORD \
-Dftp_vca_dir=$FTP_VCA_DIR