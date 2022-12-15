# Goosfraba Interview Project

### Consideration

There are plenty of inconsistencies throughout the project. The idea was to 
showcase possible solutions for various problems and provide the grounds 
for discussing the further.

Also, I had to leave out some functionalities that should be part of most webservices, like
- Caching
- Documentation
- Monitoring / Observability


### How to start the project

Go into the ``/docker`` directory and run ``docker-compose up``. Look below for some request examples.

### How to run the test

Start only the database container with ``docker-compose up database`` and run the only test.


### Example requests

Create city
```
curl --request POST \
--url http://localhost:8080/city \
--header 'Content-Type: application/json' \
--data '{
"name": "{NAME}",
"code": "{CODE}"
}'
```

Create parking facility
```
curl --request POST \
  --url http://localhost:8080/facility \
  --header 'Content-Type: application/json' \
  --data '{
	"name":"{NAME}",
	"cityId": "{VALID CITY ID}",
	"capacity": "{CAPACITY}",
	"type": "{'CAR' or 'BIKE'}"
}'
```

Create vehicle
```
curl --request POST \
  --url http://localhost:8080/vehicle \
  --header 'Content-Type: application/json' \
  --data '{
	"type":"{'CAR' or 'BIKE'}",
	"cityId":"{VALID CITY ID}"
}'
```

Park Vehicle

```
curl --request PUT \
  --url http://localhost:8080/vehicle/park \
  --header 'Content-Type: application/json' \
  --data '{
	"vehicleId": "{VALID VEHICLE ID}",
	"facilityId": "{VALID FACILITY ID}"
}'
```
