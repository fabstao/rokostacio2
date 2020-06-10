#!/bin/bash

payload='{"id":null,"nombre":"Caifanes","representante":"Sabo Romo","email":"nohay@gmail.com","direccion":"Calle num","codigo_postal":53100,"telefono":"+525554047930","rider":{"backline":"Amplis, bataca","microfonos":"AKG y Shure","otros":"Catering"}}'

echo "__________________________________________________________"
echo
echo "Probando REST POST"
echo
echo "Data:"
echo
echo $payload

url="http://localhost:8080/artista"

echo
echo "POST:"
echo
curl -X POST -H "Content-Type: application/json" -d "$payload" $url
echo
echo
echo "Nueva lista con GET"

curl $url
echo
