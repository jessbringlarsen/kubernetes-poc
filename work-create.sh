#!/usr/bin/env bash

export BROKER_URL=amqp://guest:guest@rabbitmq-service:5672

for f in fig apple banana cherry date grape
do
  /usr/bin/amqp-publish --url=$BROKER_URL -r job1 -p -b $f
done