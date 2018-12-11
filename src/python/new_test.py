"""
Model class used for inference
"""
import allennlp
from allennlp.predictors.predictor import Predictor

class NewTest():
    def __init__(self):
        self.predictor = Predictor.from_path("model2.tar")
    
    def run(self, text):
        return predictor.predict(sentence=text)
    
