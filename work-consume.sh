#!/usr/bin/env bash

export BROKER_URL=amqp://guest:guest@rabbitmq-service:5672

/usr/bin/amqp-consume --url=$BROKER_URL -q job1 -c 1 cat && echo