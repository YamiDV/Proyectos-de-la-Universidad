from app import crear_app
from utils.db import db

app = crear_app()

# Vincula la BD con nuestra app
db.init_app(app)

if __name__ == "__main__":
    app.run()

#Ejecutar con -python index.py
#probar login  con user_name = ejemplo_usuario@condosa.com y password = pass123