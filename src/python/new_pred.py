"""
Model class used for inference
"""
import allennlp
import os
from allennlp.predictors.predictor import Predictor
from allennlp.models.archival import Archive, load_archive

class NewTest():
    def __init__(self):
        self.predictor = Predictor.from_path("src/python/models/model2.tar.gz")
    
    def run(self, text):
        return self.predictor.predict(sentence=text)
#print(os.path.exists("__pycache__"))
#oad_archive("models/model2.tar.gz")
 