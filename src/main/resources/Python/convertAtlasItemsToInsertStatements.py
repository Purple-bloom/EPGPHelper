import re
import sys

with open("inputItemsES.txt") as file:
    itemList = []
    bossList = []
    itemsToQuality = {}
    currentRaid = ""
    currentBoss = ""
    for line in file:
        line = line.strip()
        if(line == ""):
            currentRaid = ""
            print("Empty line.")
        elif(line[0] != "{" and currentRaid==""):
            currentRaid = line
        elif(line[0] != "{"):
            currentBoss = line.split("=")[0].strip().replace("Trash Mobs", "Trash Mobs " + currentRaid).replace("'", "''")
        else:
            print("Raid: " + currentRaid + " Boss: " + currentBoss)
            m = re.findall("\"=q([0-9])=(.*)\",\\s*\"=", line);
            if(len(m) == 0):
                print("no match found, skipping empty entry")
            elif len(m) > 1:
                print("Found more than one match! Skipping")
                print(line)
            else:
                quality = m[0][0]
                item = m[0][1].replace("'", "''")
                itemList.append(item)
                bossList.append(currentBoss)
                itemsToQuality[item] = quality

    if(len(itemList) != len(bossList)):
        print("Something went horribly, horribly wrong. Mismatch between lengths of bosslist, itemlist or qualitylist")
        sys.exit()

    listlen = len(itemList)
    for key in itemsToQuality:
        with open("outItemsES.txt", "a") as outFile:
            print("adding " + key)
            outFile.write("INSERT INTO items VALUES (nextval('items_seq'), '" + key + "', " + itemsToQuality[key] +");\n")

    for i in range (0, listlen):
        with open("outItemsES.txt", "a") as outFile:
            outFile.write("INSERT INTO items_bosses VALUES (nextval('items_bosses_seq'), (select id from items where name = '" + itemList[i] + "'), (select id from bosses where name ='" + bossList[i] + "'));\n")
