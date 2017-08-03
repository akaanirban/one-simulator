# -*- coding: utf-8 -*-
"""
Created on Thu Aug  3 12:17:16 2017

@author: Anirban Das
"""
class Vehicle(object):
    def __init__(self):
        self.content = 0
        self.tag = list()
        
    def addContent(self, content):
        self.content = content
    def getContent(self):
        return self.content
    def appendTag(self, oneList):
        self.tag.append(self, oneList)
    def getTag(self):
        return self.tag
