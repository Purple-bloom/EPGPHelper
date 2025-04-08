import re

with open("inputBosses.txt") as file:
    bossesToRaids = {}
    currentRaid = ""
    for line in file:
        line = line.strip()
        if(line == ""):
            print("Empty line.")
        elif (line[0] != "["):
            currentRaid = line
        else:
            m = re.findall("AL\\[\\\"([A-z\\s']*)\\\"\\]", line);
            print(m)
            if len(m) == 1:
                m[0] = m[0].replace("Trash Mobs", "Trash Mobs " + currentRaid)
                bossesToRaids[m[0].replace("'", "''")] = currentRaid.replace("'", "''")
            else:
                m[1] = m[1].replace("Trash Mobs", "Trash Mobs " + currentRaid)
                bossesToRaids[m[1].replace("'", "''")] = currentRaid.replace("'", "''")

    print(bossesToRaids)
    for key in bossesToRaids:
        with open("outBosses.txt", "a") as outFile:
            outFile.write("INSERT INTO bosses VALUES (nextval('bosses_seq'), '" + key + "', (SELECT id FROM raids where name = '" + bossesToRaids[key] + "'));\n")
