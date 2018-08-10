# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import json

from django.http import JsonResponse

from django.http import HttpResponse
from django.core import serializers
from .models import Blog
from .memcache_use import Cache


def index(request):
    blog_set = Blog.objects.all()
    return HttpResponse(serializers.serialize('json', blog_set), content_type='application/json')


def new_blog(request):
    key = 'top2_new_blog'
    value = Cache.get_data(key)
    if value:
        print len(value)
    if not value:

        blog_set = Blog.objects.order_by('-create_date')[:2]
        value = serializers.serialize('json', blog_set)
        Cache.save_data(key, value, 60)
    return HttpResponse(value, content_type='application/json')


def create(request):
    for i in xrange(1, 10):
        blog = Blog(title='title' + str(i),
                    content='content' + str(i),
                    author='author' + str(i),
                    )
        blog.save()

    return JsonResponse("success", content_type='application/json', safe=False)
