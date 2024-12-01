@echo off
@setlocal EnableDelayedExpansion
set OLD_PATH=%*
@setx PATH  "%OLD_PATH%";
endlocal
