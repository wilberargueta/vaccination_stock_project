
cd interface/ && npm install && ng build --configuration=production
cd ..
cp -R interface/dist/ui/static admin/src/main/resource/
cd admin && mvn clean package
cd ..