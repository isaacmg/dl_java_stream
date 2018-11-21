FROM flink:latest
FROM continuumio/anaconda:latest
RUN conda create -q -n jep_env python=3.7.1
ENV PATH /opt/conda/envs/jep_env/bin:$PATH
ENV CONDA_DEFAULT_ENV jep_env
ENV CONDA_PREFIX /opt/conda/envs/jep_env
RUN echo $PATH
RUN source activate jep_env
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