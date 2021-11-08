

chmod +x compile.sh
./compile.sh

docker stop vaccination-db
docker rm vaccination-db

docker stop vaccinate-admin-service
docker rm vaccinate-admin-service

docker network rm vaccination-network
docker network create vaccination-network

cd postgres/

docker build -t postgres:14 .
docker run -d --name vaccination-db -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_INITDB_ARGS=--auth-host=scram-sha-256 -e POSTGRES_DB=vaccination_stock --network=vaccination-network -d postgres:14

cd ..

cd admin

docker build -t vaccinate-admin-service:1 .
docker run -d -p 8080:8080  --name vaccinate-admin-service --network=vaccination-network vaccinate-admin-service:1
docker logs -f vaccinate-admin-service
