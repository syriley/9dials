import httplib2
import re
import time 

from BeautifulSoup import BeautifulSoup, SoupStrainer

http = httplib2.Http()
status, rootResponse = http.request('http://www.drumlessons.com/drum-lessons')
file = open('lessons.txt', 'w')
for drumLesson in BeautifulSoup(rootResponse, parseOnlyThese=SoupStrainer('a')):
    if drumLesson.has_key('href'):
        lessonUrl = drumLesson['href']
        if re.match("http.*drum-lessons/.+.+[^/]*$", lessonUrl):
            print lessonUrl
            fileName = re.sub(".*drum-lessons/","",lessonUrl)
            fileName = re.sub("/", "", fileName)
            print fileName
            file.write(fileName)
            status, lessonResponse = http.request(lessonUrl)
            for link in BeautifulSoup(lessonResponse, parseOnlyThese=SoupStrainer('a')):
                if link.has_key('href'):
                    if "pdf" in link['href']:
                        print "adding link" + link['href']
                        file.write(link['href'])
                        file.write('\n')

            for link in BeautifulSoup(lessonResponse, parseOnlyThese=SoupStrainer('iframe')):
                if link.has_key('src'):
                    if "youtube" in link['src']:
                        print link['src']
                        file.write(link['src'])
                        file.write('\n')
    time.sleep(2)
                
file.close()
  
