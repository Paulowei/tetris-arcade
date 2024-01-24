package com.moderneinstein.arcade.reactive.tetris ; 

import java.awt.event.KeyEvent ; 
import java.awt.event.KeyAdapter ; 
import java.awt.event.KeyListener ;  

import java.util.Map ;  
import java.util.TreeMap ;
import java.util.ArrayList;
import java.util.HashMap ;  
import java.util.Vector ; 


import java.util.function.Consumer ; 
import java.lang.Void ; 
import java.util.Iterator ; 
import java.util.List  ; 
import java.util.Set  ;
import java.util.TreeSet ; 


public class EventListener extends KeyAdapter implements KeyListener{

    public  Map<Integer,List<Consumer<Void>>> handler ; 
    
    public  EventListener( ){
        handler = new  HashMap<Integer,List<Consumer<Void>>>()  ;
     }

     public void append(Map<Integer,List<Consumer<Void>>> partial){ 
            Set<Map.Entry<Integer,List<Consumer<Void>>>> setty = partial.entrySet() ;
            Iterator<Map.Entry<Integer,List<Consumer<Void>>>> iterand = setty.iterator() ; //getContentPane  
            while(iterand.hasNext()){
                Map.Entry<Integer,List<Consumer<Void>>> current =iterand.next() ; 
                if(!handler.containsKey(current.getKey( ))){
                    handler.put(current.getKey(),new Vector<Consumer<Void>>())  ;  } 
                    List<Consumer<Void>> listed = handler.get(current.getKey()); 
                for( int vt=0;vt<current.getValue().size();vt ++){
                    listed.add(current.getValue().get(vt)) ;
                }
            }
     }   
     // lanes.run( ) ;
     public static void express(List<Consumer<Void>> serial){
            final Iterator<Consumer<Void>> iterates = serial.iterator() ; 
            while(iterates.hasNext()){
                final Consumer<Void> lanes = iterates.next() ;   
                final  Void voided = null ; 
                Thread  thread = new Thread(){
                    @Override 
                    public void run( ){lanes.accept(voided) ;  }
                } ; 
                thread.start() ;
            }
     }
     //       for(Consumer<Void> caller:events ){   ,boolean asyncs
    public static void dispatch(List<Consumer<Void>> events){ 
        Iterator<Consumer<Void>> iterand = events.iterator() ; 
            while(iterand.hasNext()){ 
            Consumer<Void> caller =  iterand.next() ; 
            Void voided = null ; 
           caller.accept(voided);      // if (asyncs==tr)
        }   
    }  
    //   Consumer<Void> action = handler.get(types ) ;  
    //   action.accept(nulls ) ;   
    @Override 
     public void keyPressed(KeyEvent events ) {
          Integer types = events.getKeyCode( ) ;  
          List<Consumer<Void>> action = handler.get(types ) ;
          if(action==null){return ; }   
          Void nulls = null  ; 
        ///  express(action) ; 
     //   dispatch(action) ; 
      } 
      //  Consumer<Void> consumes  = handler.get(types)  ;   
       // consumes.accept(voids)   ;  
    @Override 
    public void keyReleased(KeyEvent events){
        Integer types = events.getKeyCode( ) ;
        List<Consumer<Void>> consumes  = handler.get(types)  ; 
        if(consumes==null){return  ; } 
        Void voids = null ; //  System.out.println(55) ; 
        dispatch(consumes) ;
    }    
    //   Consumer<Void> functions = handler.get (codes) ; 
    @Override 
    public void keyTyped(KeyEvent events){
        Integer codes = events.getKeyCode ( )  ;   
        List<Consumer<Void>> versions =  handler.get(codes) ; 
         if(versions==null){return ; } 
        Void clean = null  ;//  System.out.println(44)  ; 
      //  functions.accept (clean ) ; 
      dispatch(versions) ; 
    }  
      public static void update(Integer digit,Consumer<Void> consumes,Map<Integer,List<Consumer<Void>>> above){
        if(!above.containsKey(digit)){
            List<Consumer<Void>> listed  = new ArrayList<Consumer<Void>>()  ;
            listed.add(consumes)  ;
            above.put(digit,listed)  ; 
        }else{
            List<Consumer<Void>> previous = above.get(digit)  ;  
            previous.add (consumes )  ; 
        }
    }  
    public static void  replace(Integer digit,Consumer<Void> consumes,Map<Integer,Consumer<Void>> mapper){
        if(mapper.containsKey(digit)){
            mapper.replace(digit,consumes)   ;
        }else{
            mapper.put(digit,consumes)  ;
        }
    }
}