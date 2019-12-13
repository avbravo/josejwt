/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.josejwt;

/**
 *
 * @author avbravo
 */
public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
              // TODO code application logic here
   
      MyConsumer mc = new MyConsumer();
      mc.run();
      
              
        } catch (Exception e) {
            System.out.println("main()");
        }
      
    }

}
