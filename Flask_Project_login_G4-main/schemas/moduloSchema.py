from marshmallow import Schema, fields

class ModuloSchema(Schema):
    id_mod = fields.Integer()
    nombre = fields.String()
