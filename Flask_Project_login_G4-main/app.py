from flask import Flask
from utils.config import Config

from routes import login, index, portal, vista, mainmenu, infoCondosa,login2

def crear_app():
    app = Flask(__name__)

    app.static_folder = 'static'

    app.config.from_object(Config)

    app.register_blueprint(login.bp)
    app.register_blueprint(login2.bp)
    app.register_blueprint(index.bp)
    app.register_blueprint(portal.bp)
    app.register_blueprint(vista.bp)
    app.register_blueprint(mainmenu.bp)
    app.register_blueprint(infoCondosa.bp)

    return app