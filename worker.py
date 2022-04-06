#!/usr/bin/env python3

import sys, time, pika

connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq-service'))
channel = connection.channel()

channel.basic_consume(queue='job1',
                      auto_ack=True,
                      on_message_callback=callback)

def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)
    time.sleep(10)

channel.start_consuming()