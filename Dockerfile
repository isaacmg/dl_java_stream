FROM flink:latest
FROM continuumio/anaconda:latest
RUN conda create -q -n test-environment python=3.7.1
ENV PATH /opt/conda/envs/test-environmentbin:$PATH
RUN echo $PATH
RUN source activate test-environment
RUN pip install --quiet jep 
RUN pip show jep | grep Location
RUN sudo cp $HOME/miniconda/envs/test-environment/lib/python3.7/site-packages/jep/libjep.so /lib
RUN conda install pytorch-nightly-cpu -c pytorch
RUN pip install --quiet allennlp
RUN apk add --update git && \
apk add git
RUN git clone https://github.com/isaacmg/dl_java_stream.git
WORKDIR "dl_java_stream"
RUN mvn -U -q clean install