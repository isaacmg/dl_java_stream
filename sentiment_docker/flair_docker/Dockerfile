FROM python:3.7
RUN pip install flair flask
RUN wget -O microservice.py https://gist.githubusercontent.com/isaacmg/4410f34de344a357fea8425333a81103/raw/c8ee840a26e34dfd93b4d830add0147f98fe0ae2/sentiment_microservice.py
RUN python microservice.py

