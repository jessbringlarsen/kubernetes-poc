#!/usr/bin/env python3

import sys, time, pika

def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq-service'))
    channel = connection.channel()


    def callback(ch, method, properties, body):
        print(" [x] Received %r" % body)
        time.sleep(10)

    channel.basic_consume(queue='job1', auto_ack=True, on_message_callback=callback)
    channel.start_consuming()

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)