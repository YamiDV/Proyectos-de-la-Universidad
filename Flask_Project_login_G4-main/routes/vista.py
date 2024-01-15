from flask import Blueprint, jsonify

from models.usuario import Usuario
from models.modulo import Modulo
from models.rol import Rol
from models.usuarioRol import Usuario_Rol
from models.usuarioModulo import Usuario_Modulo

from utils.db import db
from utils.json import model_to_dict

bp = Blueprint('vista',__name__, url_prefix="/vista")

@bp.route('/')
def vista():
    usuario = Usuario.query.all()
    usuario_rol = Usuario_Rol.query.all()
    modulo = Modulo.query.all()

    data_usuario_rol = []
    data_usuario = []
    data_modulo = []

    for obj in usuario_rol:
        x = model_to_dict(obj)
        data_usuario_rol.append(x)

    for obj in usuario:
        x = model_to_dict(obj)
        data_usuario.append(x)

    for obj in modulo:
        x = model_to_dict(obj)
        data_modulo.append(x)    

    result = {
        'Usuario':data_usuario,
        'Rol_Usuario':data_usuario_rol,
        'Modulo':data_modulo
    }

    return jsonify(result)

@bp.route('/usuario')
def vista_usuario():
    query = Usuario.query.all()
    data = []
    for obj in query:
        data.append(model_to_dict(obj))
    result = {
        'Usuario': data
    }
    return jsonify(result)

@bp.route('/modulo')
def vista_modulo():
    query = Modulo.query.all()
    data = []
    for obj in query:
        data.append(model_to_dict(obj))
    result = {
        'Modulo': data
    }
    return jsonify(result)

@bp.route('/rol')
def vista_rol():
    query = Rol.query.all()
    data = []
    for obj in query:
        data.append(model_to_dict(obj))
    result = {
        'Rol': data
    }
    return jsonify(result)

@bp.route('/usuario_modulo')
def vista_usuario_modulo():
    query = Usuario_Modulo.query.all()
    data = []
    for obj in query:
        data.append(model_to_dict(obj))
    result = {
        'Usuario_Modulo': data
    }
    return jsonify(result)

@bp.route('/usuario_rol')
def vista_usuario_rol():
    query = Usuario_Rol.query.all()
    data = []
    for obj in query:
        data.append(model_to_dict(obj))
    result = {
        'Usuario_Rol': data
    }
    return jsonify(result)