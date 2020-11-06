from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

import datetime

auth_provider = PlainTextAuthProvider(
    username="studentbdonn",
    password="SAO201907"
)

cluster = Cluster(contact_points=["ser-info-03.ec-nantes.fr"], auth_provider=auth_provider)
session = cluster.connect("bofuri")

def format_gps(gps_x, gps_y):
    """format lat/long"""
    return "Coordonées GPS ({} {})".format(gps_y, gps_x)

# 1] Lister les données de tous les camions

def list_camions(session):
    rows = session.execute("SELECT * FROM camion LIMIT 100")
    print(" -------  Liste tous les camions ------- ")
    for row in rows[0:10] :
        print (row.camion_id, row.heure, format_gps(row.gps_x, row.gps_y))

# 2] Lister les données d’un camion donné
def list_one_camion(session, camion_id):
    query = "SELECT * FROM camion WHERE camion_id='{}'".format(camion_id)
    rows = session.execute(query)
    for row in rows[:10]:
        print (row.camion_id, row.heure, format_gps(row.gps_x, row.gps_y))

# 3] Vérifier que vous avez bien accès aux données des GPS sur une plage horaire donnée
def get_gps(session, camion, start, end):
    query = "SELECT * from camion" 
    query = query + " WHERE camion_id ='{}' AND heure >= '{}' AND heure <= '{}' ALLOW FILTERING".format(camion, start, end)
    rows = session.execute(query)

    print(" -------  Liste sur une plage de temps ------- ")
    for row in rows[0:20] :
        print (row.camion_id, row.heure, format_gps(row.gps_x, row.gps_y))

# 4] get_trajet(session, "BA-865-PF")


def get_trajet(session, camion, year, month, day):  
    """ réupère le temps de trajet pour un camion sur une journée"""  
    day = datetime.datetime(year, month, day)  
    dayTomorrow = day + datetime.timedelta(days=1)
    query = "SELECT MIN(heure) AS min, MAX(heure) AS max FROM camion WHERE camion_id = '{}' AND heure >= '{}' AND heure <= '{}'".format(camion, day, dayTomorrow)
    result = session.execute(query)

    if result[0].min == None:
        return datetime.timedelta(0)
    else:
        return result[0].max - result[0].min



# list_camions(session)

# list_one_camion(session, "BA-865-PF")


### get first and last date in DB ##@
res2 = session.execute("select MIN(heure) AS min_date, MAX(heure) AS max_date FROM camion")
start_date = res2[0].min_date
end_date = res2[0].max_date + datetime.timedelta(days=1) # ensure this day is included

def get_camion_average(start_date, end_date, camion_id):
    delta = datetime.timedelta(days=1)
    average_time = datetime.timedelta(0)
    number_result = 1

    while start_date <= end_date:
        res = get_trajet(session, camion_id, start_date.year, start_date.month, start_date.day)
        if res > datetime.timedelta(0):
            average_time += res
            number_result += 1
        start_date += delta # increment day

    print("----- Camion {} ----- ".format(camion_id))
    print("Nombre de trajets : {}".format(number_result))
    print("Temps moyen : {}".format(average_time/number_result))



###  get all camions ###

def get_all_camions_average():
    rows = session.execute("select distinct camion_id FROM camion")
    for camion in rows:
        get_camion_average(start_date, end_date, camion.camion_id)

get_all_camions_average()


# get_camion_average(start_date, end_date, "CN-225-AB")
# for row in res:
#     print (row)

# get_gps(session,  "CN-225-AB", "2020-10-06", "2020-10-11")


# get_trajet(session, "CN-225-AB", 2020, 7, 7)
# get_trajet(session, "CN-225-AB", 2020, 2, 7)