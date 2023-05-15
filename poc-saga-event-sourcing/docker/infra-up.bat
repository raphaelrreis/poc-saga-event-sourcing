chmod 0444 scripts/my-custom.cnf

docker-compose -f docker-compose.yml up -d

Rem para esperar os containers subirem 
ping 192.0.2.0 -n 1 -w 6000 > nul

criar-topicos.bat
