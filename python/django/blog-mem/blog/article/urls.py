# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^list/', views.index, name='index'),
    url(r'^create/', views.create, name='create'),
    url(r'^newer/', views.new_blog, name='newer')
]