from marshmallow import Schema, fields
from schemas.usuarioSchema import UsuarioSchema
from schemas.moduloSchema import ModuloSchema

class UsuarioModuloSchema(Schema):
    id_usuario = fields.Integer()
    id_mod = fields.Integer()
    estado_um = fields.Boolean()
    fecha_inicio = fields.Date()
    fecha_cambio = fields.Date()

    usuario = fields.Nested(UsuarioSchema)
    modulo = fields.Nested(ModuloSchema)