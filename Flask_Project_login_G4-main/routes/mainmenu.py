from flask import session, Blueprint, render_template as rt, request, url_for, redirect
from flask import Blueprint, render_template as rt
from utils.auth import login_required

bp = Blueprint('mainmenu', __name__, url_prefix="/mainmenu")

@bp.route('/', methods=['GET', 'POST'])
@login_required
def mainmenu():
    if request.method == 'POST':
                session.clear()
                return redirect(url_for('index.index'))
    return rt("mainmenu.html")

