#pragma once

#include <string>

//Raw data string creator
String sendRAW(float temperature,float humidity,float pressure,float altitude, float wifiStrength);

//HTML Creator
String sendHTML(float temperature,float humidity,float pressure,float altitude, float wifiStrength);

//Create output webpage
void outputHTML();

//Create output RAW data
void outputRAW();

//Notfound page
void outputNotFound();