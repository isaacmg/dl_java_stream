FROM tiangolo/uwsgi-nginx-flask:python3.7
RUN pip install allennlp
RUN git clone http://github.com/isaacmg/ml_serving_flask
ENV UWSGI_INI /ml_serving_flask/uwsgi.ini
WORKDIR ml_serving_flask
ENV PYTHONPATH=/ml_serving_flask
