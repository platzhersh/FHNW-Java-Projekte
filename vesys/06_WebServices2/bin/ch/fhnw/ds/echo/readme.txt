cd D:\Documents\Kurse\DistributedSystems\Teaching\06_WebServices2\06_WebServices2

wsgen -cp bin -keep -s src -d bin ch.fhnw.ds.echo.server.EchoServiceImpl

wsimport -keep -p ch.fhnw.ds.echo.client.jaxws -d bin -s src http://localhost:9898/echo?wsdl


