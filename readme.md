# Flink Meets ONNX

A practical example of how to use ONNX in a realtime streaming Flink application using Java Embedded Python (JEP) and comparing its performance with AsyncIO calling an external auto-scaling microservice running on Amazon ECS. 

[![Codefresh build status]( https://g.codefresh.io/api/badges/pipeline/isaacmg/isaacmg%2Fdl_java_stream%2Fdl_java_stream?branch=master&key=eyJhbGciOiJIUzI1NiJ9.NThkMTljZGFkODE3OGEwMTAwZjMwNDc4._HlakszuvlaxNlKYm8kFS1d5RyrYF1vi7SQfCK0XpEc&type=cf-2)]( https://g.codefresh.io/pipelines/dl_java_stream/builds?repoOwner=isaacmg&repoName=dl_java_stream&serviceName=isaacmg%2Fdl_java_stream&filter=trigger:build~Build;branch:master;pipeline:5bf5d62e072d52e89e661b64~dl_java_stream)


## Background 
Flink has historically not worked well with Python. Although plans are in the works for a broader PythonAPI currently there is no way to use ONNX or plain PyTorch models inside Flink. This is a problem as a lot of the state of the art models are increasingly being written in PyTorch. 

## Experiments 
We aim to compare performance of three seperate scenarios. 

1. Using an external Flask/Docker based microservice on ECS with AsyncIO.
2. With Java Embedded Python (JEP) to embed model directly in map.
3. (If possible) Use Lantern a new Scala library that provides a backend. 

## Setup

Weights are the standard ones from AllenNLP. For SRL they can be download from [here](https://s3-us-west-2.amazonaws.com/allennlp/models/srl-model-2018.05.25.tar.gz )
