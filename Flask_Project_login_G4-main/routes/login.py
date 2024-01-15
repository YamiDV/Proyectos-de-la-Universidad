from flask import session, Blueprint, render_template as rt, request, url_for, redirect
from flask import Blueprint, render_template as rt, request, url_for, redirect

from models.usuario import Usuario

bp = Blueprint('login', __name__,url_prefix="/login") #al llamar el blue print en base sería (NomreBP.FuncionAsociadaARuta)

@bp.route('/', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        email = request.form['email']
        password = request.form['password']

        usuario = Usuario.query.filter_by(user_name=f"{email}@condosa.com").first()
        if usuario:
            d_password = usuario.password
            if password == d_password:
                print("Éxito")
                session['logged_in'] = True
                return redirect(url_for('mainmenu.mainmenu'))
            else:
                print("Fallo")
                
        else:
            print("Fallo")

    return rt("login.html")