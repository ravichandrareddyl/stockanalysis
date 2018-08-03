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

def getPreviousDay():
    currentDay = datetime.now().date()
    previousDay = currentDay - timedelta(days=1)
    return previousDay.strftime('%dth %b %Y')


if __name__ == '__main__':
    nse = Nse()
    count = 0
    yesterday = getPreviousDay()
    print('lastRunDate is', yesterday)
    nse.download_bhavcopy(yesterday)


