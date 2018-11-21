FROM flink
RUN apt-get install -y python3-pip
RUN pip3 install jep
RUN mkdir -p /lib
RUN pip3 show jep | grep Location
#RUN cp /usr/local/lib/python3.7/dist-packages/jep/{jep-3.7.1.jar,libjep.so} /lib
ENV LD_PRELOAD /usr/lib/x86_64-linux-gnu/libpython3.7m.so.1.0

RUN pip3 install  numpy 
RUN pip3 install  pymc3 
RUN pip3 install six
RUN pip3 install urllib3
RUN pip3 install pandas
RUN pip3 install requests
RUN pip3 install urllib3 	
RUN pip3 install six
RUN pip3 install lxml
RUN pip3 install JPype1==0.6.2
RUN pip3 install jep
RUN pip3 install baostock
RUN pip3 install bs4
RUN pip3 install tushare
RUN pip3 install numpy
RUN pip3 install allenai
RUN pip3 install caffe2
ENV JAVA_OPTS  "-Djava.library.path=/usr/local/lib/python3.7/dist-packages/jep"

#RUN sbt "runMain com.sushant.example.KerasScala"
#RUN  libpython3.6-dev
CMD ["/usr/bin/java"]
WORKDIR /data
ENTRYPOINT ["/usr/bin/java", "-Djava.library.path=/usr/local/lib/python3.7/dist-packages/jep"]