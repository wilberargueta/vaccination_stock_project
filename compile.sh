cd interface/ && ng build --configuration=production
cp -R interface/dist/ui/static admin/src/main/resource/
cd admin && mvn clean package