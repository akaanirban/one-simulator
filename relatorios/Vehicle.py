# -*- coding: utf-8 -*-
"""
Created on Thu Aug  3 12:17:16 2017

@author: Anirban Das
"""

#model of a vehicle for use in the tag and content 
class Vehicle(object):
    def __init__(self, ID):
        self.content = list()
        self.tag = list()
        self.ID = 0 if ID is None else ID
        
    def replicate(self, car):
        self.content = car.getContent()
        self.ID = car.getID()
        self.tag = car.getTag()
    def appendContent(self, content):
        self.content.append(float(content))
    def getContent(self):
        return self.content
    def appendTag(self, oneList):
        self.tag.append(oneList)
    def getTag(self):
        return self.tag
    def getID(self):
        return self.ID
