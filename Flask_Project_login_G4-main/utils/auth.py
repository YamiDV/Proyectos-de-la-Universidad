from functools import wraps
from flask import session, redirect, url_for

def login_required(view):
    @wraps(view)
    def wrapped_view(*args, **kwargs):
        if 'logged_in' in session:
            return view(*args, **kwargs)
        else:
            #return redirect(url_for('portal.portal'))
            return redirect(url_for('login.login'))
    return wrapped_view
