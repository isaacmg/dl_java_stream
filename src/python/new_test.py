"""
Model class used for inference
"""
import allennlp
from allennlp.predictors.predictor import Predictor

class NewTest():
    def __init__(self):
        self.predictor = Predictor.from_path("https://s3-us-west-2.amazonaws.com/allennlp/models/srl-model-2018.05.25.tar.gz")
    
    def run(self, text):
        return predictor.predict(sentence=text)
    
