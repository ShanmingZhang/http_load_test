#!/usr/bin/env python
# -*- coding: utf-8 -*-

import numpy as np
import matplotlib.pyplot as plt


result_dict = {}
batch_number_array = []
successful_amount = 0;
failure_amount = 0;
total_amount = 0;

with open("HttpRequestLoadTest.txt", "r") as f_file:

    result_dict = {}
    for f_line in f_file:
        if f_line.find("BatchNumber=") > -1 :

            items = f_line.split(";")
            batch_number = items[0].split("=")[1]


            batch_number_array.append(int(batch_number))


batch_number_array.sort()
batch_number_array = list(set(batch_number_array))

for num in batch_number_array:
    with open("HttpRequestLoadTest.txt", "r") as s_file:
        
        for s_line in s_file:
            if s_line.find("BatchNumber=") > -1 :
                
                items = s_line.split(";")
                batch_number = items[0].split("=")[1]
                if int(batch_number) == num:
                    total_amount+=1
                    flag = items[2].split("=")[1]
                    if flag == "true":
                        successful_amount+=1
                    else:
                        failure_amount+=1

                    succ_fail_total_array = [successful_amount, failure_amount, total_amount]

                    result_dict[num]=succ_fail_total_array
                   
print result_dict

successful_array = [0]
failture_array = [0]
total_array = [0]

for index in batch_number_array:
	items_set = result_dict[index]
	a = items_set[0]
	successful_array.append(items_set[0])
	failture_array.append(items_set[1])
	total_array.append(items_set[2])
	
batch_number_array.insert(0,0)
print batch_number_array
print successful_array
print failture_array
print total_array

labels = ["Normal HttpRequests", "Error HttpRequests", "Total HttpRequests"]

plt.plot(batch_number_array, successful_array, label='Normal HttpRequests')

plt.plot(batch_number_array, failture_array, label='Error HttpRequests')

plt.plot(batch_number_array, total_array, label='Total HttpRequests')

plt.legend(loc='upper left')
plt.grid(color="gray")

plt.xlabel("Concurrent time(s)")
plt.ylabel("Amount of HttpRequestts")

plt.savefig('HttpLoadTestResultChart.png') 
plt.title('HttpRequest Load Test',loc='Center')

plt.show()
