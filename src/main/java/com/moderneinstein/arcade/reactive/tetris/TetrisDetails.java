package com.moderneinstein.arcade.reactive.tetris;
import javax.swing.JPanel ; 
import java.awt.Graphics ; 
import java.awt.Rectangle ; 
import java.awt.image.BufferedImage  ;
import  java.util.Queue ; 

import java.awt.TextField ; 
import java.awt.Color ; 
import java.util.List ; 
import java.util.Vector ; 
import java.util.LinkedList  ; 
import java.util.Iterator ; 
import java.util.function.Function ; 

import javax.sound.midi.spi.MidiFileReader ;
    public class TetrisDetails extends JPanel{
            
      //  private TetrisGame games ; 
        private TetrisGrid grids ; 
        private TextField field5 ; 
      //  public long score ;   
        public static  Color background = new Color(180,180,240,90) ;
        /*  public TextField field6 ;   
        public TextField field4 ;  */  
        public static int OFFSETX = 45 ; 
        public static int OFFSETY = -20 ;  // 25 ;  
        public static int MULTIPLIER = 5 ; 
        public static int WIDTH =  TetrisGrid.SPANSX*MULTIPLIER ;  // 60 ;
        public  static int HEIGHT =  TetrisGrid.HEIGHT*TetrisGrid.SPANSY-OFFSETY ;    
        public static int SHIFTX = 0   ; 
        public static int SHIFTY  = TetrisGrid.SPANSY*5   ;   
        public static int[] point = {TetrisGrid.OFFSETX+TetrisGrid.WIDTH
            *TetrisGrid.SPANSX+OFFSETX,  TetrisGrid.OFFSETY+OFFSETY } ;  
            public int limit = 4 ;
         public void bindScore(long millis){
            String created = new String("Score : ") ; 
            created =created.concat(Long.toString(grids.score));  
            field5.setText(created) ;
        }
        public TetrisDetails(TetrisGrid  source){
            this.grids = source ;   
          //  this.score = 0l ; 
            field5 = new TextField() ; 
             bindScore(source.score) ;  // new TetrisGrid()   
            field5.setBounds(new Rectangle(260,100)) ;   
            grids.add(field5) ;      
            grids.validate() ;
             }  
            // (LinkedList<TetrisBlock>)  
            //System.out.println(created.toString()) ;   
            // new int[]{TetrisGrid.SPANSX,TetrisGrid.SPANSY   
                // (new int[]{TetrisGrid.WIDTH+3,alterY}) ;  
                // (new int[]{point[0],point[1]+alterY}) ;   

        public void paintBricks(Graphics frames){
            LinkedList<TetrisBlock> linked = grids.stream  ;  //.clone() ;  
            Iterator<TetrisBlock> iterator = linked.iterator() ; 
             int lapse = 1 ;   
             int alterY = 5 ;       
             if(grids.holder!=null) {
             TetrisBlock held = new TetrisBlock(grids.holder) ; 
            held.setPosition( new int[]{TetrisGrid.WIDTH+4,1}) ;
          //  alterY = alterY+5 ;  
             held.paint(frames) ; }
             if(linked.size()<=limit){return ; }
             for(int  tr=1;tr<=limit;tr++){  
                if(linked.size()<=tr){continue ; }
                TetrisBlock current = linked.get(tr-1) ; 
                TetrisBlock created = new TetrisBlock(current) ; 
                created.setPosition(new int[]{TetrisGrid.WIDTH+4,alterY}) ; 
                 created.paint(frames)  ;  
                alterY = alterY +5 ; 
             } 
        }  
         // created.paint(frames.create())    ;     
         // SHIFTY ;   
            //    created.emplaceColor(current.deriveColor()) ;  
        /* while(lapse<=3&&iterator.hasNext()){
                TetrisBlock current = iterator.next() ; 
                TetrisBlock created = new TetrisBlock(current.deriveMarks()
                ,current.derivePosition(),current.deriveSpans()) ; 
                created.setPosition(new int[]{}) ;      
             } */ 
        public  void paintScore(Graphics graphics){
            field5.paint(graphics) ;   
             graphics.drawRect(TetrisGrid.OFFSETX,TetrisGrid.OFFSETY-75,
             TetrisGrid.WIDTH*TetrisGrid.SPANSX,75 ) ; 
             graphics.fillRect(TetrisGrid.OFFSETX,TetrisGrid.OFFSETY-75, 
             TetrisGrid.WIDTH*TetrisGrid.SPANSX,75) ; 
              /*  let mut  crest:TcpStream = TcpStream::new(SockAddr:new(  )) ;   */
        }   
        @Override 
        public void paint(Graphics graphics ){ 
            graphics.setColor(background) ;     
            graphics.drawRect(point[0],
            point[1],WIDTH,HEIGHT ) ;   
            graphics.fillRect(point[0],
            point[1],WIDTH,HEIGHT) ;   
            paintScore(graphics) ; 
            paintBricks(graphics) ;   
        }   

            /*        int[] limits = deriveLimits(current) ;  
               int[] lanes =  TetrisBlock.confirmRotation(current,WIDTH,limits[1]) ;//WIDTH,HEIGHT); limits[0]
                int[][] frames =  current.deriveMarks() ;   
            boolean noted =   confirmRotationA(current,lanes) ; 
            if(noted==false){System.out.println("Anticlockwise") ; return   ; }  
            current.setPosition(lanes) ;  */
        @Override 
        public void update(Graphics graphics){ 
            field5.update(graphics) ; 
            long leads = this.grids.score ; 
            bindScore(leads) ;
        } 
        /* int[][] bytes = current.deriveMarks() ;   
                int[]  limits = deriveLimits(current) ; 
                int[] yields =  TetrisBlock.confirmRotation(current,WIDTH,limits[1]); //WIDTH,HEIGHT) ; limits[0]   
                boolean  notes = confirmRotationC(current,yields) ;     
                if (notes==false){System.out.println("Clockwise") ; return ; }  
                  current.setPosition(yields) ;  */
        public  boolean confirmShift(int[] alter,TetrisBlock  value){
            int[] position = value.derivePosition() ; 
            position[1]+=alter[1] ;
            position[0]+=alter[0] ; 
            int[][] frames = value.deriveMarks() ; 
            for(int vt=0;vt<frames.length;vt++){
                if(frames[vt][0]+position[0]<0||frames[vt][0]+position[0]>=TetrisGrid.WIDTH){return false ; } 
                if(position[1]+frames[vt][1]<0||frames[vt][1]+position[1]>=TetrisGrid.HEIGHT){return false ; } 
                if(grids.filled[position[1]+frames[vt][1]][frames[vt][0]+position[0]]==true ){return false; }
            }  
            return true  ; 
        } 
        //   int[] alter,     
        /*	public static final int[][] forms2 = {{0,0},{1,0},{2,0},{3,0}} ;  //I-block
		public static final int[][] forms3 = {{0,0},{0,1},{1,1},{1,0}}  ;  //Square-block
		public static final int[][] forms4 = {{0,1},{1,1},{2,1},{2,0}} ;  //L-block-1
		public static final int[][] forms5  = {{0,0},{1,0},{2,0},{2,1}};  //L-block-2
		public static final int[][] forms6={{0,0},{1,0},{1,1},{2,0}} ;  //T-block ;
		public static final int[][] forms7 = {{0,0},{1,0},{1,1},{2,1}} ; //Z-block-2 ; 
		public static final int[][]forms8 = {{0,0},{1,1},{1,1},{1,2}} ;  //Z-block-1 ; */
        public   int[] confirmRotation(TetrisBlock  input,Function<int[][],int[][]> function){
            int[] point = input.derivePosition() ; 
            int[][] marks = input.deriveMarks() ;  
            int[][] apply = function.apply(marks) ;  
            int shift = 1 ;   
            for(int vf=0;vf<marks.length;vf++){ 
                boolean cases =  true  ; 
            for(int dr=0;dr<apply.length;dr++){  
                if(apply[dr][1]+point[1]>=TetrisGrid.HEIGHT||apply[dr][1]+point[1]<0){cases=false;break;} 
                  if(point[0]+apply[dr][0]<0||apply[dr][0]+point[0]>=TetrisGrid.WIDTH){cases =false;break ; } 
                if(grids.filled[point[1]+apply[dr][1]][point[0]+apply[dr][0]]==true){cases = false ; break ;} }  
            if(cases ==true){ return point ; } 
        point[1] = point[1]-shift  ;    }  
         return new int[]{-1,-1} ; 
        }    
        /*  //     if (point[0]<=0){return ;  }  
           //     if(filled[point[1]][point[0]-1]==true){return ; }
                int[][] types = current.deriveMarks() ; 
                for(int ft=0;ft<types.length;ft++){
                    if(point[0]+types [ft][0]<= 0){return ;  } 
                 if(filled[point[1]+types[ft][1]][types[ft][0]+point[0]-1]==true){return  ; } }  */

    //    if(!validate(bytes[0]+crest[0],bytes[1]+crest[1])){return false ; } 
    public  boolean ensureBrick(TetrisBlock temps ){ 
            int[][] nests = temps.deriveMarks() ;  
            int[] bytes = temps.derivePosition()  ; 
            int[] crest = temps.DeriveShifts() ;  
            for(int dr=0;dr<nests.length;dr++){
            if(bytes[1]+nests[dr][1]>=TetrisGrid.HEIGHT||bytes[1]+nests[dr][1]<0){return false ;} 
            if(nests[dr][0]+bytes[0]>=TetrisGrid.WIDTH||bytes[0]+nests[dr][0]<0){return false ; }  
            if (grids.filled[nests[dr][1]+bytes[1]][nests[dr][0]+bytes[0]]==true){return false ;}  }
            // for(int  vr=0;vr)  
            return true ; 
        }      
        public  void detach(){
            grids.remove(field5) ; 
            grids.validate()  ;
        }   
        public  int[]  adjustBrick(TetrisBlock bricks,int[]  begin){
            int[] point =  new int[]{begin[0],begin[1]} ;  //bricks.derivePosition() ;  
            int[][] marks = bricks.deriveMarks ()  ;    
            int alterX = -1 ; 
            for(int  tc=0;tc<=3;tc++){  
                boolean allows = true ; 
            for(int  ft=marks.length-1;ft>=0;ft--){  
                boolean phase = grids.CheckPoint(marks[ft][0]+point[0],marks[ft][1]+point[1]) ;
                if(phase==false){allows = false ; break ; }
            }  
            if(allows==true){return  point ;  } 
             point[1]= point[1]+alterX ; }
            return new int[] {-1,-1} ; 
        }
    /*    //
    }      if(posit[0]+ drift[0]-1>=WIDTH-1){return ;  }  
          //      if(filled[posit[1]][posit[0]+drift[0]]==true){return ; }  
                int[][] lanes = current.deriveMarks()  ;
                for(int tr=0;tr<lanes.length;tr++){  
                    if(lanes[tr][0]+posit[0]>=WIDTH-1){return ;  }
                    if(filled[lanes[tr][1]+posit[1]][lanes[tr][0]+posit[0]+1]==true){return  ; }
                } */
            }
