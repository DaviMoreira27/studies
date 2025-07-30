#!/bin/bash

id flaskuser &>/dev/null || useradd -m flaskuser

apt-get update
apt-get install -y build-essential python3-pip rsync

sudo -u flaskuser pip3 install --user flask

cat <<EOF > /home/flaskuser/app.py
import sys
sys.path.append('/home/flaskuser/.local/lib/python3.9/site-packages')  # ajuste conforme versão do Python

from flask import Flask
app = Flask(__name__)

@app.route('/')
def hello_cloud():
    return 'Hello Cloud!'

app.run(host='0.0.0.0')
EOF

# Define permissões
chown flaskuser:flaskuser /home/flaskuser/app.py

# Executa o app Flask como flaskuser
sudo -u flaskuser bash -c 'export PATH=\$HOME/.local/bin:\$PATH && nohup python3 /home/flaskuser/app.py > /home/flaskuser/flask.log 2>&1 &'
