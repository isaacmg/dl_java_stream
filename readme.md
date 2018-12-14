# Flink Meets ONNX

A practical example of how to use ONNX in a realtime streaming Flink application using Java Embedded Python (JEP) and comparing its performance with AsyncIO caling an external auto-scaling microservice running on Amazon ECS. 

## Background 
Flink has historically not worked well with Python.

## Setup

Weights are the standard ones from AllenNLP. For SRL they can be download from [here](https://s3-us-west-2.amazonaws.com/allennlp/models/srl-model-2018.05.25.tar.gz )