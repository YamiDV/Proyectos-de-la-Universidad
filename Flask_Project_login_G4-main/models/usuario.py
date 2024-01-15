from utils.db import db
    
class Usuario(db.Model):
    id_usuario = db.Column(db.Integer, primary_key=True)
    user_name = db.Column(db.String(300))
    password = db.Column("password",db.String(50)) #usamos el alias 'pass' para que los reconozca la BD

    def __init__(self, id_usuario, user_name=None, password=None) -> None:
        self.id_usuario = id_usuario
        self.user_name = user_name
        self.password = password
        

    