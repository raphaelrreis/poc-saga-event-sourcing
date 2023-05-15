docker images -a | grep "mensagem-customizada" | awk '{print $3}' | xargs docker rmi -f
mvn clean package
docker build -t mensagem-customizada .