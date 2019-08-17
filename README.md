# herexAndroid

Dit is de repository voor het herexamen van Native Apps 1.

Functionaliteit van de app: 

De app kan gebruikt worden in portret- en landscapemode (optimaal: Nexus 5X) en voor tablets in portret- en landschapmode (optimaal: Nexus 10)

Login/registratie van gebruikers gebeurt via Firebase en is internetconnectiviteit voor nodig.

lokaal (Room database)
  - Recepten toevoegen
  - Recepten verwijderen
  - Recepten in detail bekijken
  - Recepten (in detail) bewerken 
  
Als er WiFi of datanetwerk is werkt de app met een, lokaal draaiende, MongoDB (NodeJS & Express) ( zie folder 'backend').
  - Recepten toevoegen (indien ingelogd)
  - Recepten verwijderen (indien ingelogd)
  - Recepten in detail bekijken
  - Recepten (in detail) bewerken (indien ingelogd)
  
Starten van de backend: 
  - Open de folder 'backend' in visual studio code (of andere IDE)
  - Open twee terminalvensters
  - Doe eerst 'npm install' (installeert alle dependencies van package.json)
  - Daarna in het ene : 'mongod' (Mongo Deamon, host voor het db process. Folders /data/db nodig onder de C: schijf)
  - In de tweede terminal : 'nodemon ./bin/www' (autoreload bij save, hier komen alle logs van POST/GET/PUT/DELETE binnen)
  
  Bij het toevoegen van een recept / het inloggen / het registreren is er ook validatie op de velden van de formulieren
  
  Documentatie van de app kan gevonden worden onder de 'doc' folder. De documentatie werd gegenereerd dmv Dokka.
