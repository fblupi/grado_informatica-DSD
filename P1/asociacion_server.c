/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "asociacion.h"

typedef enum {false,true} bool;

DiccionarioPtr root = NULL;

Estado *
ponerasociacion_1_svc(ID arg1, Clave arg2, Valor arg3,  struct svc_req *rqstp)
{
	static Estado  result;

	if(root==NULL) { // No hay ningún diccionario
		//printf("No hay ningun diccionario\n");
		// Nueva entrada
		EntradaPtr ent = (EntradaPtr)malloc(sizeof(Entrada));
		ent->clave = arg2;
		ent->valor = arg3;
		ent->sig = NULL;
		// Nuevo diccionario
		DiccionarioPtr dic = (DiccionarioPtr)malloc(sizeof(Diccionario));  
		dic->id = arg1;
		dic->sig = NULL;
		dic->first = ent;
		root = dic;
		result = OK;
	} else { // Hay algún diccionario
		//printf("Hay algun diccionario\n");
		DiccionarioPtr dicPtr = root;
		//printf("dicPtr = root\n");
		bool encontradoID = false;
		if(dicPtr->id==arg1) {
			encontradoID = true;
		}
		//printf("voy a entrar en while\n");
		while(encontradoID==false && dicPtr->sig!=NULL) { // Buscar diccionario
			dicPtr=dicPtr->sig;
			if(dicPtr->id==arg1) {
				encontradoID = true;
			}
		}
		//printf("salgo while\n");
		if(encontradoID==true) { // Diccionario encontrado
			//printf("Diccionario encontrado\n");
			EntradaPtr entPtr = dicPtr->first;
			bool encontradoClave = false;
			if(entPtr->valor==arg2) {
				encontradoClave = true;
			}
			while(encontradoClave==false && entPtr->sig!=NULL) { // Buscar entrada
				entPtr=entPtr->sig;
				if(entPtr->valor==arg2) {
					encontradoClave = true;
				}
			}
			if(encontradoClave==true) { // Clave encontrada
				//printf("Clave encontrada\n");
				entPtr->valor = arg3;
				result = REEMPLAZADO;
			} else { //  Clave no encontrada
				//printf("Clave no encontrada\n");
				// Nueva entrada
				EntradaPtr ent = (EntradaPtr)malloc(sizeof(Entrada));
				ent->clave = arg2;
				ent->valor = arg3;
				ent->sig = NULL;
				entPtr->sig=ent;
				result = OK;
			}
		} else { // Diccionario no encontrado -> se crea uno nuevo
			//printf("Diccionario no encontrado\n");
			// Nueva entrada
			EntradaPtr ent = (EntradaPtr)malloc(sizeof(Entrada));
			ent->clave = arg2;
			ent->valor = arg3;
			ent->sig = NULL;
			// Nuevo diccionario
			DiccionarioPtr dic = (DiccionarioPtr)malloc(sizeof(Diccionario));  
			dic->id = arg1;
			dic->sig = NULL;
			dic->first = ent;
			dicPtr->sig = dic;
			result = OK;
		}
	}

/*******************************************************************************
SET
********************************************************************************

if(root==NULL) {
	new Entrada entradaNew;
	entradaNew->clave = arg2;	
	entradaNew->valor = arg3;
	entradaNew->sig = null;
	root->first = entradaNew;
	root->ID = arg1;
	root->sig = null;	
	result = OK;
} else {
	encontradoID = BUSCAR ID;
	if(encontradoID) {
		encontradoClave = BUSCAR Clave;
		if(encontradoClave) {
			entradaPtr->valor = arg3;
			result = REEMPLAZADO;
		} else {
			new Entrada entradaNew;
			entradaNew->clave = arg2;	
			entradaNew->valor = arg3;
			entradaNew->sig = null;
			entradaPtr->sig = entradaNew;	
			result = OK;
		}
	} else {
		new Entrada entradaNew;
		entradaNew->clave = arg2;
		entradaNew->valor = arg3;
		entradaNew->sig = null;
		new Diccionario diccionarioNew;
		diccionarioNew->id = arg1;
		diccionarioNew->sig = null;
		diccionarioNew->first = entradaNew;	
		diccionarioPtr->sig = diccionarioNew	
		result = OK;
	}
}	 

*******************************************************************************/

	return &result;
}

ResultEntrada *
obtenerasociacion_1_svc(ID arg1, Clave arg2,  struct svc_req *rqstp)
{
	static ResultEntrada  result;

/*******************************************************************************
GET
********************************************************************************

encontradoID = BUSCAR ID;
if(encontradoID) {
	encontradoClave = BUSCAR Clave;
	if(encontradoClave) {
		result->valor = entradaPtr->valor;
		result->e = OK;
	} else {	
		result->e = FALLO;
	}
} else {	
	result->e = FALLO;
}	
 
*******************************************************************************/


	return &result;
}

Estado *
borrarasociacion_1_svc(ID arg1, Clave arg2,  struct svc_req *rqstp)
{
	static Estado  result;

/*******************************************************************************
BORRAR
********************************************************************************

 

*******************************************************************************/

	return &result;
}

ResultDiccionario *
enumerar_1_svc(ID arg1,  struct svc_req *rqstp)
{
	static ResultDiccionario  result;

/*******************************************************************************
ENUMERAR
********************************************************************************

encontradoID = BUSCAR ID;
if(encontradoID) {
	result->entrada = diccinarioPtr->first;
	result->e = OK;
} else {	
	result->e = FALLO;
}
	 
*******************************************************************************/

	return &result;
}
