#!/usr/bin/env python3

import sys, time, pika

connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq-service'))
channel = connection.channel()

method_frame, header_frame, body = channel.basic_get('job1')
if method_frame:
    print(method_frame, header_frame, body)
    channel.basic_ack(method_frame.delivery_tag)
else:
    print('No message returned')