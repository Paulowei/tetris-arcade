package com.moderneinstein.arcade.reactive.tetris  ; 

import java.lang.Runnable ; 
import java.lang.Thread ; 
import java.util.Arrays ; 

import java.awt.Graphics ;
import  java.awt.Component ;
import java.awt.image.BufferedImage ;
import java.awt.Button ; 
import java.awt.event.KeyAdapter ; 
import javax.swing.JLayeredPane   ; 


public class StarterClass extends KeyAdapter{

    public static  TetrisGame  games ; 
    public static Graphics graphics ; 
    public static Thread createThread(){
        Thread created = new Thread(){
            @Override 
            public void run(){
            while(true){
                games.update(graphics ) ;
            }
            }
        } ; 
        return created; 
    }

        public static void main(String[] args){ 
            BufferedImage  buffers = new BufferedImage(50,50,BufferedImage.TYPE_INT_RGB) ;
            games = new TetrisGame() ;  
            Button buttons = new Button () ;   
        //    graphics =   buffers.getGraphics( ) ;
          //games.getGraphics( ) ;  //buttons.getGraphics() ;  
          //  Thread threaded=  createThread( ) ;    
          long current = System.currentTimeMillis( ) ;  
          JLayeredPane saved = games.getLayeredPane( ) ;
         // games.setLayeredPane(new JLayeredPane( ) ) ; //(games.getContentPane()) ;
         //   while(System.currentTimeMillis()-current<5000){
              graphics =  buffers.getGraphics() ;  /// buffers.createGraphics( ) ;
          //  }
        //  System.out.println( buffers.toString()) ;  
      //    games.setLayeredPane(saved) ;
            while (true){
              ///  games.update(graphics ) ;   
             //    System.out.println(66) ;   
           //  games.update(buttons.getGraphics( )) ;   
           games.update(graphics) ; 
           }
         //  threaded.run() ; 
        }
}