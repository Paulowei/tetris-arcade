package com.moderneinstein.arcade.reactive.tetris ; 

import java.lang.Thread;
import java.lang.Runnable ; 
import java.util.concurrent.ConcurrentLinkedQueue ; 
import java.util.Queue ;  

import java.util.List ; 
import java.util.Random ; 
import java.util.LinkedList ; 
import java.util.function.Supplier ; 


/*Objects of this class are to be run 
asyncronously with the main thread ,
as the main event loop may block the main thread
 if it runs  synchronously with the main thread  ;     */
public class SpawnerThread  extends Thread{  

    private  Queue<TetrisBlock> queue  ;  // bricks ; 
    private Random  spawner ; 
     public static int DEFAULT_LIMIT  = 20  ;
     public int current_limit ;   
     public static int[] DEFAULT_POINT = new int[]{0,0} ;   
     public static Supplier<TetrisBlock> supplier ;   
     //  bricks = new ConcurrentLinkedQueue<TetrisBlock>()   ;  
     //         this.supplier =  new  // this::RandomBlock  ;  
    public SpawnerThread (LinkedList<TetrisBlock> linear){ 
        spawner  = new Random (System.currentTimeMillis( )) ;
        spawner = new Random (System.nanoTime  ()) ;  
        queue =  linear ;  // new ConcurrentLinkedQueue<TetrisBlock>()   ;   
        this.current_limit = DEFAULT_LIMIT  ;   
        this.supplier =  new  Supplier<TetrisBlock>(){
            @Override 
            public TetrisBlock get(){
                TetrisBlock  brick = RandomBlock() ; 
                return  brick  ;
            }
        } ;
            }    
       // this.bricks = new ConcurrentLinkedQueue<TetrisBlock>() ; 
       //         this.supplier =  new  // this::RandomBlock  ;
    public SpawnerThread(int  insert_limit,LinkedList<TetrisBlock> serial){  
        this.queue =   serial ;  // new ConcurrentLinkedQueue<TetrisBlock>() ; 
        spawner = new Random (System.nanoTime ()) ; 
     //   spawner = new Random (System.currentTimeMillis()) ;
        this.current_limit = insert_limit ;   
        this.supplier =  new Supplier<TetrisBlock>(){ 
            @Override
            public TetrisBlock get(){
            TetrisBlock block =  RandomBlock() ; 
            return block ;      
            }
        } ;
    }   
    public TetrisBlock RandomBlock(){
         int[][][] frames = TetrisBlock.Forms.metres ; 
        int point = spawner.nextInt (frames.length) ; 
        int[][]  configs  = frames[point] ;  
        TetrisBlock tetris =   new TetrisBlock(configs,DEFAULT_POINT,new int[]{TetrisGrid.SPANSX,TetrisGrid.SPANSY}) ; 
        tetris.setPosition(new int[]{spawner.nextInt(TetrisGrid.WIDTH),0}) ;
         return tetris ;  
    }  
    public Random  deriveRandom( ){
        Random rands  =  spawner ;  // new Random() ;  
        return rands ; 
    }
    public void fillQueue(){ 
         int doubles = current_limit*2 ; 
         int diffs = doubles-queue.size( )  ; 
        int lapse = diffs;  
        boolean trialsz =  Thread.currentThread().isAlive() ; 
        while( lapse>0){
            TetrisBlock created =  supplier.get() ; //.supply( ) ; //RandomBlock() ;   
            queue.offer(created)  ;
            lapse = lapse-1; 
        }
    }
    @Override 
    public void run(){
        while(true){   
            if(queue.size()<current_limit){
                fillQueue() ; 
            }    
            boolean cases = Thread.currentThread().isInterrupted() ;
        }  
    }      
    //    System.out.println(queue.size()) ; 
    public TetrisBlock yields(){
        if(this.queue.size( )==0){
            return null ;    }
        TetrisBlock   recents = this.queue.element() ; 
        this.queue.poll( ) ; 
         return recents ; 
    }
    public Supplier<TetrisBlock> getSupplier( ){
        return  this.supplier ; 
    }
    public void setSupplier(Supplier<TetrisBlock> supplies){
        this.supplier =   supplies ;  // new Supplier<TetrisBlock>(supplies) ; 
    }

}