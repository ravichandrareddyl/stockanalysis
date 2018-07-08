import logging
import json
import re
import six
import time
from nse import Nse
from datetime import datetime, timedelta
from dateutil import parser
from utils import js_adaptor, byte_adaptor

log = logging.getLogger('nse')
logging.basicConfig(level=logging.DEBUG)

def isHoliday(date):
    return date.isoweekday() > 5

def getPreviousDate(currentDay):
    d = parser.parse(currentDay)
    previousDay = d - timedelta(days=1)
    while isHoliday(previousDay):
        previousDay = previousDay - timedelta(days=1)
    return previousDay.strftime('%dth %b %Y')

def changeLastRunDate(lastRunDate):
    fh = open("lastRundate.txt","w")
    fh.write(lastRunDate)
    fh.close()


def getLastRunDate():
    filename = 'lastRundate.txt'
    fh = open(filename,"r")
    return fh.read()


if __name__ == '__main__':
    nse = Nse()
    count = 0
    lastRunDate = getLastRunDate()
    while (count <= 1000):
        time.sleep(60)
        if count == 0:
            nse.download_bhavcopy(lastRunDate)
            print('lastRunDate is', lastRunDate)
        else:
            previousDate = getPreviousDate(lastRunDate)
            nse.download_bhavcopy(previousDate)
            lastRunDate = previousDate
            print('lastRunDate is', lastRunDate)
        count = count + 1
        changeLastRunDate(lastRunDate)


