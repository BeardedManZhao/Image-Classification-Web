@echo off
java -version
python -V
python -m compileall .\cifar10_train.py
python -m compileall .\cnn.py
python -m compileall .\mnist_train.py
python -m compileall .\train.py
python -m compileall .\use.py

::begin

java -jar ModelInit.jar

pause
exit