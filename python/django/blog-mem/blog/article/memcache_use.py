#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Created by Jikai Zhang on 17-7-1

from pymemcache.client.hash import HashClient

host = '127.0.0.1'
port_one = 1111
port_two = 2222
client = HashClient([(host, port_one), (host, port_two)])


class Cache(object):
    @staticmethod
    def save_data(key, value, expired_time=0):
        return client.set(key, value, expired_time)

    @staticmethod
    def get_data(key):
        return client.get(key)

    @staticmethod
    def delete_data(key):
        return client.delete(key)
