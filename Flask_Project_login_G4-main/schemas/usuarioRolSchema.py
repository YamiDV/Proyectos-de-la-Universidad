from marshmallow import Schema, fields
from schemas.usuarioSchema import UsuarioSchema
from schemas.rolSchema import RolSchema

class UsuarioRolSchema(Schema):
    id_usuario = fields.Integer()
    id_rol = fields.Integer()
    estado_up = fields.Boolean()
    fecha_inicio = fields.Date()
    fecha_cambio = fields.Date()

    usuario = fields.Nested(UsuarioSchema)
    rol = fields.Nested(RolSchema)

    
