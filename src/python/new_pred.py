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
        archive = load_archive("src/python/models/model2.tar.gz")
        logging.log(30, "archive loaded")
        self.predictor = Predictor.from_archive(archive)
    
    def run(self, text):
        return self.predictor.predict(sentence=text)
#print(os.path.exists("__pycache__"))
#load_archive("models/model2.tar.gz")
s = NewTest() 
def class_test():
    return "test data"