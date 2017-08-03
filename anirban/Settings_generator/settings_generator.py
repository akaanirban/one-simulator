# -*- coding: utf-8 -*-
"""
Created on Wed Aug  2 20:41:59 2017

@author: Anirban Das
"""
import random


def writeScenario(filename):
    file = open(filename, "a")
    file.write("Scenario.name = paper_settings\n")
    file.write("Scenario.simulateConnections = true\n")
    file.write("Scenario.updateInterval = 0.1\n")
    file.write("Scenario.endTime = 480\n")
    file.write("Scenario.nrofHostGroups = 66\n")
    file.write("\n")
    file.close
    
def writeInterface(filename):
    file = open(filename, "a")    
    file.write("btInterface.type = SimpleBroadcastInterface\n")
    file.write("btInterface.transmitSpeed = 250k\n")
    file.write("btInterface.transmitRange = 100\n")
    file.write("highspeedInterface.type = SimpleBroadcastInterface\n")
    file.write("highspeedInterface.transmitSpeed = 10M\n")
    file.write("highspeedInterface.transmitRange = 1000\n")
    file.write("\n")
    file.close
    
def writeMovingGroup(filename, groupID, groupNo):
    file = open(filename, "a")    
    file.write("Group{}.groupID={}\n".format(groupNo, groupID))
    file.write("Group{}.bufferSize = 50M\n".format(groupNo))
    file.write("Group{}.router = PayloadMessageRouter\n".format(groupNo))
    file.write("Group{}.movementModel = ShortestPathMapBasedMovement\n".format(groupNo))
    file.write("Group{}.routeType = 1\n".format(groupNo))
    file.write("Group{}.waitTime = 10, 30\n".format(groupNo))
    file.write("Group{}.speed = 90, 90\n".format(groupNo))
    file.write("Group{}.nrofHosts = 800\n".format(groupNo))
    file.write("Group{}.nrofInterfaces = 1\n".format(groupNo))
    file.write("Group{}.interface1 = btInterface\n".format(groupNo))
    file.write("\n")
    file.close   
    
def writeStationaryGroup(filename, groupID, groupNo):
    file = open(filename, "a")    
    file.write("Group{}.groupID={}\n".format(groupNo, groupID))
    file.write("Group{}.bufferSize = 50M\n".format(groupNo))
    file.write("Group{}.router = PayloadMessageRouter\n".format(groupNo))
    file.write("Group{}.movementModel = MapBasedStationaryMovement\n".format(groupNo))
    file.write("Group{}.speed = 0, 0\n".format(groupNo))
    file.write("Group{}.nrofHosts = 1\n".format(groupNo))
    file.write("Group{}.nrofInterfaces = 1\n".format(groupNo))
    file.write("Group{}.interface1 = btInterface\n".format(groupNo))
    file.write("\n")
    file.close   
    
def writeOutSideGroup(filename, groupID, groupNo):
    file = open(filename, "a")    
    file.write("Group{}.groupID={}\n".format(groupNo, groupID))
    file.write("Group{}.bufferSize = 50M\n".format(groupNo))
    file.write("Group{}.router = PayloadMessageRouter\n".format(groupNo))
    file.write("Group{}.movementModel = StationaryMovement\n".format(groupNo))
    file.write("Group{}.speed = 0, 0\n".format(groupNo))
    file.write("Group{}.nodeLocation = 4500,3400\n".format(groupNo))
    file.write("Group{}.nrofHosts = 1\n".format(groupNo))
    file.write("Group{}.nrofInterfaces = 1\n".format(groupNo))
    file.write("Group{}.interface1 = btInterface\n".format(groupNo))
    file.write("\n")
    file.close    

def writeCommonEvents(filename, numberofEvents ):
    file = open(filename, "a")
    file.write("Events.nrof = {}\n".format(numberofEvents))
    file.write("\n")
    file.close
    
def writeEvents(filename, activeFlag, EventNo, host, toHost):    
    file = open(filename, "a")
    file.write("Events{}.class = MessageEventGenerator\n".format(EventNo))
    file.write("Events{}.interval = 1,1\n".format(EventNo))
    file.write("Events{}.size = 500k,1M\n".format(EventNo))
    file.write("Events{}.hosts = {}, {}\n".format(EventNo, host, host))
    file.write("Events{}.tohosts = {}, {}\n".format(EventNo, toHost, toHost))
    file.write("Events{}.prefix = M\n".format(EventNo))
    file.write("Events{}.activehotspot = {}\n".format(EventNo, activeFlag))
    file.write("\n")
    file.close

def writeMovementModel(filename):
    file = open(filename, "a")
    file.write("MovementModel.rngSeed = 1\n")
    file.write("MovementModel.worldSize = 4500, 3400\n")
    file.write("MovementModel.warmup = 1000\n")
    file.write("MapBasedMovement.nrofMapFiles = 4\n")
    file.write("MapBasedMovement.mapFile1 = data/roads.wkt\n")
    file.write("MapBasedMovement.mapFile2 = data/main_roads.wkt\n")
    file.write("MapBasedMovement.mapFile3 = data/pedestrian_paths.wkt\n")
    file.write("MapBasedMovement.mapFile4 = data/shops.wkt\n")
    file.write("\n")
    file.close
    
def writeReports(filename):
    file = open(filename, "a")
    file.write("Report.nrofReports = 2\n")
    file.write("Report.warmup = 0\n")
    file.write("Report.reportDir = relatorios/\n")
    #file.write("Report.report1 = MessageStatsReport\n")
    #file.write("Report.report2 = DeliveredMessagesReport\n")
    #file.write("Report.report3 = ContactsPerHourReport\n")
    file.write("Report.report1 = CreatedMessagesReport\n")
    #file.write("Report.report4 = CreatedMessagesReport\n")
    #file.write("Report.report5 = BufferOccupancyReport\n")
    #file.write("Report.report6 = MessageLocationReport\n")
    file.write("MessageLocationReport.granularity = 1\n")
    file.write("MessageLocationReport.messages = 1\n")
    #file.write("Report.report7 = MessagesPerHostReport\n")
    file.write("Report.report2 = MessagesPerHostReport\n")
    #file.write("Report.report8 = MessageDeliveryReport\n")
    #file.write("Report.report9 = MessageDelayReport\n")
    #file.write("Report.report10 = MessageReport\n")
    #file.write("Report.report11 = EventLogReport\n")
    file.write("Report.granularity = 479\n")
    #some random settings might not be needed
    file.write("ProphetRouter.secondsInTimeUnit = 30\n")
    file.write("SprayAndWaitRouter.nrofCopies = 6\n")
    file.write("SprayAndWaitRouter.binaryMode = true\n")
    file.write("\n")
    file.close
    
def writeOptimization(filename):
    file = open(filename, "a")
    file.write("Optimization.cellSizeMult = 5\n")
    file.write("Optimization.randomizeUpdateOrder = true\n")
    file.write("\n")
    file.close
    
def writeGUI(filename):
    file = open(filename, "a")
    file.write("GUI.UnderlayImage.fileName = data/helsinki_underlay.png\n")
    file.write("GUI.UnderlayImage.offset = 64, 20\n")
    file.write("GUI.UnderlayImage.scale = 4.75\n")
    file.write("GUI.UnderlayImage.rotate = -0.015\n")
    file.write("GUI.EventLogPanel.nrofEvents = 100\n")
    file.write("\n")
    file.close
    
    
if __name__ == "__main__":
    totalFixedGroups = 64
    totalEventGenerators = 64
    totalMovingGroup = 1
    filename = "anirban_generated_settings.txt"
    
    f = open(filename, "w")
    f.close
    
    
    writeScenario(filename)
    writeInterface(filename)
    writeMovingGroup(filename, "Vehicle_", 1)
    
    totalnodes = range(2,66)    
    for i in totalnodes:
        groupID = "HotSpot_" + str((i-1)) + "_"
        writeStationaryGroup(filename, groupID, i )
    
    writeOutSideGroup(filename, "HotSpot_TO", 66)
    writeCommonEvents(filename, totalEventGenerators)
    
    active = random.sample(range(1, totalFixedGroups+1), 10)
    #notActive = list(set(totalnodes) - set(active))
    for i in range(1, 65):
        if i in active:
            writeEvents(filename, "true", i, 800 + i-1, 864 )
        else:
            writeEvents(filename, "false", i, 800 + i-1, 864 )
    
    writeMovementModel(filename)
    writeOptimization(filename)
    writeReports(filename)
    writeGUI(filename)
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    