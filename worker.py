#!/usr/bin/env python3

import sys, time, pika

connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq-service'))
channel = connection.channel()

for i in range(3):
    time.sleep(5)
    method_frame, header_frame, body = channel.basic_get('job1')
    if method_frame:
        print(method_frame, header_frame, body)
        channel.basic_ack(method_frame.delivery_tag)
        if 'fig' in body.decode("utf-8"):
            print('Failure!')
            sys.exit(1)
    else:
        print('No message returned')

connection.close()