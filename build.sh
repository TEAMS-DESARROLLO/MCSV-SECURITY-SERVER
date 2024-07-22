echo $PWD
# cp $PWD/target/*.jar /home/fuentes/target
cd /home/fuentes/security-server
echo $PWD
docker build -t security-server:1.0.1 .