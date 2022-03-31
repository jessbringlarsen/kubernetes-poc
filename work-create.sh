#!/usr/bin/env bash

export BROKER_URL=amqp://guest:guest@rabbitmq-service:5672

for f in apple banana cherry date fig grape lemon melon
do
  /usr/bin/amqp-publish --url=$BROKER_URL -r job1 -p -b $f
done