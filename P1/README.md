# Práctica 1

## Agenda distribuida con RPC

### Instrucciones

##### Requisitos

* Sistema Linux con Java y rpcbind instalados.

##### Linux

1. Si se realiza cualquier cambio en el fichero `asociacion.x` y se necesita volver a generar el resto de ficheros, escribir en la línea de comandos `rpcgen -NCa asociacion.x`.
2. Compilar con `make -f Makefile.asociacion`.
3. Lanzar el servidor con `./asociacion_server`.
3. Lanzar el cliente en una terminal distinta a la del servidor con `./asociacion_client localhost`.

### Autor

* Francisco Javier Bolívar Lupiáñez