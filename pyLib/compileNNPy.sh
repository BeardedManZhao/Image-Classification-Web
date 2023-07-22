java -version
python -V
python -m compileall $1/cifar10_train.py
python -m compileall $1/cnn.py
python -m compileall $1/mnist_train.py
python -m compileall $1/train.py
python -m compileall $1/use.py
python -m compileall $1\useBatch.py
python -m compileall $1\utils.py
