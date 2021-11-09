
cd interface/ && npm install && ng build --configuration=production
cd ..
cp -R interface/dist/ui/static admin/src/main/resources/
cd admin && mvn clean package -DskipTests
cd ..
