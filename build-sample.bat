@ECHO off

SETLOCAL

:: default
SET FTP_HOST=sgftp.kaisquare.com
SET FTP_PORT=21
SET FTP_USER=sgftp
SET FTP_PASS=password
SET FTP_VDIR=others/am/gradle_repo/vca
REM create your own path for development

:: user-input
SET /p INPUT_FTP_HOST=ftp_host [%FTP_HOST%]: 
IF NOT [%INPUT_FTP_HOST%] == [] (SET FTP_HOST=%INPUT_FTP_HOST%)

SET /p INPUT_FTP_PORT=ftp_port [%FTP_PORT%]: 
IF NOT [%INPUT_FTP_PORT%] == [] (SET FTP_PORT=%INPUT_FTP_PORT%)

SET /p INPUT_FTP_USER=ftp_user [%FTP_USER%]: 
IF NOT [%INPUT_FTP_USER%] == [] (SET FTP_USER=%INPUT_FTP_USER%)

SET /p INPUT_FTP_PASS=ftp_pass [%FTP_PASS%]: 
IF NOT [%INPUT_FTP_PASS%] == [] (SET FTP_PASS=%INPUT_FTP_PASS%)

SET /p INPUT_FTP_VDIR=ftp_vdir [%FTP_VDIR%]: 
IF NOT [%INPUT_FTP_VDIR%] == [] (SET FTP_VDIR=%INPUT_FTP_VDIR%)

./gradlew.bat clean build ^
-Dftp_host=%FTP_HOST% ^
-Dftp_port=%FTP_PORT% ^
-Dftp_username=%FTP_USER% ^
-Dftp_password=%FTP_PASS% ^
-Dftp_vca_dir="%FTP_VDIR%"

ENDLOCAL