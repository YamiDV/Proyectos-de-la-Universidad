from marshmallow import Schema, fields

class UsuarioSchema(Schema):
    id_usuario = fields.Integer()
    nombre_usuario = fields.String()
    password = fields.String()