@echo off
@setlocal EnableDelayedExpansion
set OLD_PATH=%~1
@setx PATH  "%%JAVA_HOME%%\bin;%OLD_PATH%";
endlocal
