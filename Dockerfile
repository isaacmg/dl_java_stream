FROM flink:latest
RUN apt-get update
RUN apt-get install -y libgl1-mesa-glx apt-utils openssh-server net-tools
# Conda update and creation of environment
RUN conda update conda && \
    conda env create -f environment.yml && \
    # Activation of environment and correction of bash
    echo "source activate xlshp_env" > ~/.bash
RUN hash -r
RUN conda config --set always_yes yes --set changeps1 no
RUN conda update -q conda
RUN conda info -a
RUN conda create -q -n test-environment python=3.7.1
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