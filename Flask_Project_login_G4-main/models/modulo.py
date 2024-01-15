from utils.db import db
    
class Modulo(db.Model):
    id_mod = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(30))    

    def __init__(self, id_mod, nombre=None) -> None:
        self.id_mod = id_mod
        self.nombre = nombre
