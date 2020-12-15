from flask import Flask, render_template
from api.routes import api

app = Flask(__name__)


@app.route('/index.html')
def index():
    return app.send_static_file('u9Dbg3hkUo.html')


app.register_blueprint(api, url_prefix='/api')
