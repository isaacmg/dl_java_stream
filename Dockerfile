FROM continuumio/anaconda:latest AS conda
RUN conda create -q -n jep_env python=3.7.1
RUN /bin/bash -c "source activate jep_env"
RUN pip install --upgrade pip
FROM flink:latest
COPY --from=conda / /
RUN pip install jep 
RUN pip show jep | grep Location
RUN sudo cp $HOME/miniconda/envs/test-environment/lib/python3.7/site-packages/jep/libjep.so /lib
RUN conda install pytorch-nightly-cpu -c pytorch
RUN pip install --quiet allennlp
RUN apk add --update git && \
apk add git
RUN git clone https://github.com/isaacmg/dl_java_stream.git
WORKDIR "dl_java_stream"
RUN mvn -U -q clean install