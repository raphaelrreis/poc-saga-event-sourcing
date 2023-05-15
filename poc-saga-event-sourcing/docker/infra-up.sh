docker-compose up -d

sleep 15

sh criar-topicos.sh
cd ..
cd mensagem-customizada
sh criar-conector.sh
