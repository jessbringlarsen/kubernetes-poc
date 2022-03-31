#!/usr/bin/env python3

# Just prints standard out and sleeps for 10 seconds.
import sys
import time

data = sys.stdin.readlines()[0]
print("Processing: " + data)

time.sleep(10)