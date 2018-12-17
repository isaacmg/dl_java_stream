# Flink Meets ONNX

A practical example of how to use ONNX in a realtime streaming Flink application using Java Embedded Python (JEP) and comparing its performance with AsyncIO calling an external auto-scaling microservice running on Amazon ECS. 

## Background 
Flink has historically not worked well with Python. Although plans are in the works for a broader PythonAPI currently there is no way to use ONNX or plain PyTorch models inside Flink. This is a problem as a lot of the state of the art models are increasingly being written in PyTorch. 

## Experiments 
We aim to compare performance of three seperate scenarios. 

1. Using an external Flask/Docker based microservice on ECS with AsyncIO.
2. With Java Embedded Python (JEP) to embed model directly in map.
3. (If possible) Use Lantern a new Scala library that provides a backend. 

## Setup

Weights are the standard ones from AllenNLP. For SRL they can be download from [here](https://s3-us-west-2.amazonaws.com/allennlp/models/srl-model-2018.05.25.tar.gz )
