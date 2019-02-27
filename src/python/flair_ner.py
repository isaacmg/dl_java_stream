"""
Model class used for inference
"""
from flair.data import Sentence
from flair.models import SequenceTagger
# make a sentence
def get_ner(text):
    sentence = Sentence(text)
    # load the NER tagger
    tagger = SequenceTagger.load('ner')
    tagger.predict(sentence)
    return sentence.get_spans('ner')
