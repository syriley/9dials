import re

inF = open('data.yml', "r")
outF = open('pdfUrls.txt', "w")
count = 0;
inDrumLessons = 0;
for line in inF:
    if "pdfLocation" in line:
        outF.write(line)
