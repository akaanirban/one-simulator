# -*- coding: utf-8 -*-
"""
Created on Thu Aug  3 02:47:31 2017

@author: ME
"""

import matlab.engine
import Vehicle


hh = [[1,2,3], [4,5,6]]
eng = matlab.engine.start_matlab()
a = matlab.double(hh)
b = eng.sqrt(a)
print(b)