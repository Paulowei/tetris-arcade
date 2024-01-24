package com.moderneinstein.arcade.reactive.tetris ;

import java.lang.Runnable ;
import java.lang.Thread ;
import java.util.function.Consumer ;
import java.util.function.Predicate ;  


public class SyncThread extends Thread implements Runnable  {
    
    private Predicate<Object> judge ;
    private Consumer<Object> callback ;   
    private Object delegate ;   
    private long counts ; 
    public long  cycle ; 
    public static  long DEFAULT_CYCLE =  20l ; 
    public boolean async  ;     
    private boolean paused ; 
    
    public SyncThread(Predicate<Object>  params1,Consumer<Object> params2,Object  params3){
         this.callback = params2 ; 
         this.judge = params1 ;   
         this.cycle = DEFAULT_CYCLE ;  
        this.counts = 0  ;  
        delegate = params3  ; 
        this.paused = false ; 
    } 
    public SyncThread(Predicate<Object> params1,Consumer<Object> params2,Object params3,long params4 ){
          this.cycle = params4 ;      
        this.counts = 0 ;    
        this.delegate = params3 ;    
         this.callback = params2 ;   
         this.judge  = params1 ;      
         this.paused =false ; 
    }           

    public void SetAsync(boolean brace ){
        this.async = brace ;   
    } 
    public boolean GetAsync( ){
        return this.async ;   
    }     
    public void activate(boolean method){  
        Thread actor = new Thread(){
             @Override 
             public void run( ){
                callback.accept(delegate) ;
            } } ;  
   //  TetrisGrid grids =(TetrisGrid)delegate ; 
  //  grids.updateBrick() ;  
   //  long current = System.currentTimeMillis() ;  
    // while(System.currentTimeMillis()-current<3500){ }
     //   } ; 
        if (method==true){actor.start( ) ;  } 
        else{actor.run( ) ; } 
    }  
    //      if()
    public void respond(){
        boolean cases  = judge.test(delegate) ; 
        if(cases==true){
            activate(this.async ) ;
        }  
    }   
    public boolean derivePaused(){
        return this.paused ; 
    } 
    public  void   emplacePaused( boolean point ){
        this.paused = point  ; 
    }
    //As this object will run within an indefinite event loop (while(true)) ; 
    //It is to be run on a background thread( ) ; not along the main thread( )  ;
    //Failure to do this could lead to  performance drawbacks on the main thread ;
    @Override    
    public void run( ){    
        while(true){       
            if(paused==false){
            counts = counts+1 ;    
          //  if (counts>=cycle){   
                counts = 0   ;    
                respond( )  ;   
           // }   
        }    
        }
    }     
}