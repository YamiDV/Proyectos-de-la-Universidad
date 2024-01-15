from utils.db import db

class Usuario_Rol(db.Model):
    id_usuario = db.Column(db.Integer, primary_key=True)
    id_rol = db.Column(db.Integer, primary_key=True)
    estado_up = db.Column(db.Boolean)
    fecha_inicio = db.Column(db.Date)
    fecha_cambio = db.Column(db.Date)

    def __init__(self, id_usuario, id_rol, estado_up = None, fecha_inicio = None, fecha_cambio = None) -> None:
        self.id_usuario = id_usuario
        self.id_rol = id_rol
        self.estado_up = estado_up
        self.fecha_inicio = fecha_inicio
        self.fecha_cambio = fecha_cambio


    