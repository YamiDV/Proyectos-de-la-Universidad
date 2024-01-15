from flask import Blueprint, render_template as rt

bp = Blueprint('index', __name__) #al llamar el blue print en base sería (NomreBP.FuncionAsociadaARuta)

@bp.route('/')
def index():
    return rt("index.html")
