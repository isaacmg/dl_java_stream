FROM flink:latest as flink

RUN apt-get update --fix-missing && apt-get install -y wget bzip2 ca-certificates \
    libglib2.0-0 libxext6 libsm6 libxrender1 \
    git mercurial subversion

RUN wget --quiet https://repo.anaconda.com/miniconda/Miniconda2-4.5.11-Linux-x86_64.sh -O ~/miniconda.sh && \
    /bin/bash ~/miniconda.sh -b -p /opt/conda && \
    rm ~/miniconda.sh && \
    ln -s /opt/conda/etc/profile.d/conda.sh /etc/profile.d/conda.sh && \
    echo ". /opt/conda/etc/profile.d/conda.sh" >> ~/.bashrc && \
    echo "conda activate base" >> ~/.bashrc

RUN apt-get install -y curl grep sed dpkg && \
    TINI_VERSION=`curl https://github.com/krallin/tini/releases/latest | grep -o "/v.*\"" | sed 's:^..\(.*\).$:\1:'` && \
    curl -L "https://github.com/krallin/tini/releases/download/v${TINI_VERSION}/tini_${TINI_VERSION}.deb" > tini.deb && \
    dpkg -i tini.deb && \
    rm tini.deb && \
    apt-get clean

RUN conda create -q -n jep_env python=3.7.1
RUN /bin/bash -c "source activate jep_env"
RUN pip install --upgrade pip
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