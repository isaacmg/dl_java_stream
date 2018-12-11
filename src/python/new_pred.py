"""
Model class used for inference
"""
import allennlp
import os
from allennlp.predictors.predictor import Predictor
from allennlp.models.archival import Archive, load_archive
import logging 
class NewTest():
    def __init__(self):
        logging.log(30, "run shit")
        self.predictor = Predictor.from_path("src/python/models/model2.tar.gz")
    
    def run(self, text):
        logging.log(30, "about to run pred")
        return self.predictor.predict(sentence=text)
#print(os.path.exists("__pycache__"))
#load_archive("models/model2.tar.gz")
#s = NewTest()