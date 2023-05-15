rs.initiate({ _id: "rs0", members: [ { _id: 0, host: "localhost:27017" } ] })
db = db.getSiblingDB('pedido');
db.createUser(
	{
		user: 'user', 
		pwd: 'password', 
		roles: [{role: 'readWrite', db: 'pedido'}]
	}
);
db.createCollection('eventos_pedido');
db.createCollection('outbox');

db = db.getSiblingDB('consumidor');
db.createUser(
	{
		user: 'user', 
		pwd: 'password', 
		roles: [{role: 'readWrite', db: 'consumidor'}]
	}
);