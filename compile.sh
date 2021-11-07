cd ui/ && ng build --configuration=production
cp -R ui/dist/ui/static admin/src/main/resource/
cd admin && mvn clean package