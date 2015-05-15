/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgui;

import GUI.*;

public class ChatGUI {

    public static void main(String[] args) {
  
        
        ClienteView clienteView = new ClienteView();
        
        String nombre;
        
        CapturarNombre capturarNombre = new CapturarNombre(clienteView, true);
        
        nombre = capturarNombre.getNombre();
        
        clienteView.setNombre(nombre);
        
        clienteView.showView();
        
        
        
    }
    
}
