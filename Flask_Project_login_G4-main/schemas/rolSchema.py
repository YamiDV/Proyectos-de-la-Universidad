from marshmallow import Schema, fields

class RolSchema(Schema):
    id_rol = fields.Integer()
    descripcion = fields.String()