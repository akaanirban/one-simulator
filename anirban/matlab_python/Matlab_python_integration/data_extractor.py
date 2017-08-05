# -*- coding: utf-8 -*-
"""
Created on Thu Aug  3 00:45:01 2017

@author: Anirban Das
"""
from Vehicle import Vehicle
import matlab.engine
import numpy as np
import math
from collections import Counter

#etracts the contents and tags from the message perhost report
def extractContentTag(filename, resultFilename):
    file = open(filename, "r")
    etc = open(resultFilename, "w")
    vehicleFlag = False
    cars = []
    for line in file.readlines()[1:]:
        line = line.strip()
        if(len(line)==0):
            if(vehicleFlag == True): #append only the vehicles
                if("vehicle" in newCar.getID().lower()):
                    cars.append(newCar)
            vehicleFlag = False
        else:
            if (line[0]=='#'): #start a new vehicle object
                vehicleFlag = True
                vehicleID = line.split(':')[1].strip()
                newCar = Vehicle(vehicleID)
               
            elif (line[0] =='['):
                if(vehicleFlag == True):
                    stuff = line.split(':')
                    if(len(stuff) ==2):
                        newCar.appendContent(float(stuff[1]))
                        tag = stuff[0].replace('[', '').replace(']', '').replace(',', '').replace(' ', '')
                        newCar.appendTag([int(i) for i in tag])
            else:
                continue
    file.close
    etc.close
    return cars
    
#get the actual signal from the created messages report
def getXsignal(filename):
    file = open(filename, "r")
    counter = 0
    Xsignal = []
    for line in file.readlines():
        if (counter == 65):
            break
        counter = counter +1
        if(len(line) != 0):
            if (line[0] == '#'):
                continue
            else:
                stringTotal = line.split(':')
                xvalue = stringTotal[1].split('_')
                Xsignal.append(float(xvalue[0]))
    return Xsignal
    

def getErrorRatio(X, Xhat):
    numerator = 0
    dinominator = 0
    if(len(X) != len(Xhat)):
        return
    else:
        for i in range(len(X)):
            numerator = numerator + (X[i]-Xhat[i])**2
            dinominator = dinominator + (X[i])**2
    return math.sqrt(numerator/dinominator)
    

def getRecoveryThreshold(X_elem, Xhat_elem, theta):
    if (X_elem ==0 and Xhat_elem ==0):
        return True
    elif (X_elem ==0 and Xhat_elem != 0):
        return False
    else:
        return (abs(X_elem- Xhat_elem)/abs(X_elem)) <= theta
    

def getRecoveryRatio(X, Xhat, theta):
    lam = 0
    if(len(X) != len(Xhat)):
        return
    else:
        for i in range(len(X)):
            if(getRecoveryThreshold(X[i], Xhat[i], theta)):
                lam = lam +1
    return lam/len(X)


def getUniqueTags(contenttags):
    count = Counter(tuple(e) for e in contenttags)
    contenttag = []
    for key in sorted(count.keys()):
        contenttag.append(list(key))
    return contenttag
    
if __name__ == "__main__":
    Vehicles = extractContentTag("paper_settings_MessagesPerHostReport.txt", "test.txt")
    Xsignal = getXsignal("paper_settings_CreatedMessagesReport.txt")
    theta = 0.01
    allMatlabEngines = []
    
    
    #for i in range(len(Vehicles)):
    for i in range(3):
        allMatlabEngines.append(matlab.engine.start_matlab())
        
    #for i in range(len(Vehicles)):
    for i in range(3):
        content = Vehicles[i].getContent()
        tag = Vehicles[i].getTag()
        #Phi = matlab.double(tag)
        #Y = matlab.double(content)
        #b = allMatlabEngines[i].getActualSignal(Phi, Y)
        b = allMatlabEngines[i].getActualSignal(matlab.double(tag), matlab.double(content))
        curr = np.array([val for subl in b for val in subl])
        actual = np.array(Xsignal)
        print("vehicle: {} - norm Difference= {}, Error ratio= {}"
              .format(i, np.linalg.norm(curr-actual), getErrorRatio(actual, curr)))
        print("\nRecovery ratio = {}\n".format(getRecoveryRatio(actual, curr, theta)))
    
    
    
    
    
    
    
    
    
    
    
    