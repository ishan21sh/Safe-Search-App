import requests
from bs4 import BeautifulSoup
import json
import os
from collections import OrderedDict
from flask import Flask, request, Response,jsonify

# name = 'music'
def fetchAppList(name):
    url = 'https://play.google.com/store/search?q='+name+'&c=apps'

    url2 = "https://web-api-cache.aptoide.com/search?query="

    a = requests.get(url)
    soup = BeautifulSoup(a.content, 'html.parser')

    all_names = soup.find_all('span', class_="DdYX5")
    links = soup.find_all('a', class_='Si6A0c Gy4nib')
    names = []
    pslink = []

    for name, path in zip(all_names, links):
        names.append(name.text)
        pslink.append(path['href'])

    data = {}
    # i = "Youtube Music"
    for i,x in zip(names, pslink):
        x='https://play.google.com'+x
        b = requests.get(url2+"".join(i.split(" ")))

        file_data = json.loads(b.content)
        for j in range(6):
            try:
                uname = file_data['datalist']['list'][j]['uname']
                aname = file_data['datalist']['list'][j]['name'] 
                # print(aname)
                if("".join(aname.split(' ')).lower() == "".join(i.split(" ")).lower()):
                    # print("Milaa:: ", aname)
                    break
            except:
                break

        url3 = requests.get('https://'+uname+'.en.aptoide.com/app')
        soup2 = BeautifulSoup(url3.content, 'html.parser')


        permissionsData = json.loads(soup2.find_all('script')[17].text.encode('utf-8'))

        for k in range(3):
            permissions = soup2.find_all('a', class_='information__InfoAnchor-xn2n41-2 ewlnic')[k].text
            try:
                permissions = int(permissions)
                break
            except:
                pass

        allpermissions = permissionsData['props']['pageProps']['app']['file']['used_permissions']
        data[i] = [permissions,x, allpermissions]
    
    return data


app = Flask(__name__)


def root_dir():  # pragma: no cover
    return os.path.abspath(os.path.dirname(__file__))

def get_file(filename):  # pragma: no cover
    try:
        src = os.path.join(root_dir(), filename)
        # Figure out how flask returns static files
        # Tried:
        # - render_template
        # - send_file
        # This should not be so non-obvious
        return open(src).read()
    except IOError as exc:
        return str(exc)

@app.route('/<string:name>', methods=['GET', 'POST'])
def index(name):
    print(name)
    result = fetchAppList(name)
    # print(result)
    return jsonify(result)
app.run(host='0.0.0.0', port=81)