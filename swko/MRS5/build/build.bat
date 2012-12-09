@echo off

REM bat file for a convenient build of the project

set PROJECT_HOME=.
set ANT_HOME=C:\java\apache-ant-1.7.1

set PATH=%ANT_HOME%/bin;%PATH%

ant %*