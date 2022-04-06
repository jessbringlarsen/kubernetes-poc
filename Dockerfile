# Specify BROKER_URL and QUEUE when running
FROM ubuntu:18.04

WORKDIR /work

RUN apt-get update && \
    apt-get install -y curl ca-certificates amqp-tools python3 python3-pip\
       --no-install-recommends \
    && rm -rf /var/lib/apt/lists/* \
    && python3 -m pip install pika --upgrade
COPY worker.py worker.py
RUN chmod +x /work/worker.py
CMD ["python3", "/work/worker.py"]