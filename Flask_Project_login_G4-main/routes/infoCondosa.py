from flask import Blueprint, render_template as rt

bp = Blueprint('infoCondosa', __name__, url_prefix="/infoCondosa") #al llamar el blue print en base sería (NomreBP.FuncionAsociadaARuta)

@bp.route('/')
def infoCondosa():
    return rt("infoCondosa.html")