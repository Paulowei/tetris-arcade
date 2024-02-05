package com.moderneinstein.arcade.reactive.tetris  ; 

import java.awt.Component ; 
import javax.swing.JComponent ; 
import java.awt.image.BufferedImage ; 
//import java.awt.
import javax.swing.JPanel ; 
import java.awt.Color ; 
import javax.swing.JFrame ; 
import java.awt.TextField ; 

import java.awt.Graphics ; 
import java.awt.Dimension ; 
import java.awt.Point ; 
import java.awt.Rectangle ; 

import java.util.Map ; 
import java.util.List ; 
import java.util.ArrayList  ;
import java.util.Arrays ; 
import java.util.LinkedList ; 
import java.util.Iterator ; 

 import java.util.function.Consumer ;
import java.util.function.Predicate ;  
import java.util.function.Supplier  ; 
import java.util.TreeMap ;
import java.awt.event.KeyEvent ; 
import java.util.Random ; 

// TetrisGrid extends Component
public class TetrisGrid extends JPanel{

    public TetrisBlock current ; 
    private SyncThread syncs ; 
    private  SpawnerThread spawner ; 
    public static int  HEIGHT = 25 ; 
    public static int  WIDTH = 22 ;   
    public static int OFFSETY = 75 ;
    public static int  SPANSX = 20 ; 
    public static int  SPANSY = 20 ;  
    public static int OFFSETX = 100 ; 
    public  static Color[] colors =  {new Color(190,220,250), new Color(190,250,220),new Color(250,220,190)} ; 
    public boolean[][] filled ;  
    private static Map<Integer,List<Consumer<Void>>> bindings ;   
    private boolean should ;  
    private Color[][] nested ;    
    public static long times  ;  
    public LinkedList<TetrisBlock> stream  ; 
    public TetrisDetails details ;    
    public long score ;   
    public TetrisBlock holder ; 
        /*   private int[]  options =  {0} ;
  //  public  int[]  DEFAULTS = {0}  ;  */
    //   grids.setOptions (0,1) ;   }
    //   Thread verse = Thread.currentThread()  ;   
     //    verse =Thread.currentThread() ;   
       //   Thread.currentThread().notify() ;  //  System.out.println(44) ;
    public void configureSyncs(){ 
        Predicate<Object> preds =  new Predicate<Object>(){
            @Override 
            public boolean test (Object object){
                TetrisGrid grids = (TetrisGrid)object ;   
                List<Integer> values = grids.probe(); 
              //  if(values.size()>0){return true  }   
            //  while(System.currentTimeMillis()-times<350){
                boolean trials = grids.checkBrick(grids.current ) ;  
                times = System.currentTimeMillis() ; 
           /*   if(trials==true){ 
                grids.current.setDirection(new int[]{0,0}) ;
                while(System.currentTimeMillis()-times<3500){  
            Thread.currentThread().setPriority(5) ; //   Thread.currentThread().notify() ;  
                    if(!grids.checkBrick(grids.current)){
                        grids.current.setDirection(new int[]{0,1}) ;  
                 Thread.currentThread().setPriority(5) ; //  Thread.currentThread().notify() ; //  System.out.println(66) ;    
                        return false ;  } }    
                        return  trials ;  //  true  ; 
            }      */
            return trials ;  // false ; 
          }   } ;   
        ///  {return false  ; } if(should==false){return false ; }
              //    return true   ;  //  return trials ;  
              //   }  } ; 
        Consumer<Object> consumes = new Consumer<Object>(){
            @Override 
            public void  accept(Object object){
                TetrisGrid grids = (TetrisGrid)object  ;  
                grids.updateBrick() ;    
                grids.restate() ; 
              //  grids.setOptions(0,1);  
                 }  }  ; 
      syncs =  new SyncThread(preds,consumes,this) ;     
    }

    public void restate(){
        List<Integer> linear =  probe() ;
        for(int cr=linear.size()-1;cr>=0;cr--){
            Integer data =  linear.get(cr) ; 
            eliminateRow(data) ;  
        }
    }
    public void configureFilled( ){
        for(int ct=HEIGHT-1;ct>=0;ct--){
            for(int  vr=WIDTH-1;vr>=0;vr--){
                this.filled[ct][vr] = false ; 
            }
        }
    }  
    public void configurePoint( ){
        this.setPreferredSize(new Dimension(550,550)) ;
        this.setEnabled( true) ; 
        this.setVisible( true)  ; 
        this.setLocation(new Point(60,60) ) ; 
        this.setBounds (new Rectangle(80,80,550,550)) ; 
    }  
    // LinkedList<TetrisBlock> linked  //linked ) ;
    public void configureSpawner(){   
         stream = new LinkedList<TetrisBlock>() ; 
        spawner= new SpawnerThread(15 ,stream ) ; 
        spawner.setSupplier(
            new Supplier<TetrisBlock>(){
                @Override 
                public TetrisBlock get(){  
                    TetrisBlock created =  spawner.RandomBlock( ) ;   
                    return  created ; 
                     //   return null ; 
                }
            }
        ) ;
    }
    //  this.filled = new boolean[SPANSY][SPANSX] ; 
    //    bindings = new TreeMap<Integer,List<Consumer<Void>>>( ) ;   
     //   syncs = new SyncThread( ) ;
    public TetrisGrid( )  {  
        super( ) ;   
     this.configurePoint( ) ;        
        this.filled = new boolean[HEIGHT][WIDTH] ;  
        nested = new Color[HEIGHT][WIDTH] ;      
        bindings = new TreeMap<Integer,List<Consumer<Void>>>() ;  
            //  field4 = new TextField("Score : 0 ") ; 
        score = 0l ;   //  this.add (field4) ;     
           details = new TetrisDetails(this)   ; 
        configureSpawner() ;   
         configureSyncs( ) ;     
         configureBinds1(bindings) ;  
         configureBinds2(bindings) ;    
       this.setLocation(60,60) ; 
        this.should = true ;      
        configureBrick() ; 
        startThreads(true) ; 
     }  

     public void startThreads(boolean cases){
      //  this.add(current,0) ; //  this.remove(current) ; 
      syncs.setPriority(7) ; 
     spawner.setPriority(4) ;        
        if(cases==true){
        syncs.start() ; 
       spawner.start() ;           
     }
   //   this.current.setPosition(new int[]{SPANSX*6,SPANSY*6}) ; 
     }   
     public  void configureBrick(){  
        int point =  new Random().nextInt(TetrisBlock.Forms.metres.length) ;
        this.current = new TetrisBlock(TetrisBlock.Forms.metres[point],new int[]{6,6},new int[]{SPANSX,SPANSY}) ;  //   this.current = spawner.RandomBlock () ;  
        this.current.setDirection(new int[]{0,1}) ;  
     }
    //       //  this.add(newer) ;  
     /* public void updateBrick( ){
        
    }  */ 
    // this.checkBrick()
     @Override 
     public void update(Graphics graphics){       
        if(should==true){ 
       super.update(graphics.create()) ; 
    //    if( should){
        current.update(graphics.create()) ;     
       //  String placed = new String("Score : ") ; 
       //  placed.concat(Long.toString(this.score)) ;  
        details.update(graphics) ;   }
      //  field4.setText(placed) ;  
      //  boolean possible = this.checkBrick() ;   
      //  if(possible){      
      //          updateBrick() ; }   
      //  }
          //  restate( )   ;   
     //  this.current.paint(graphics) ; 
      //    this.paint(graphics) ;  
      //    System.out.println(55) ; 
        }    
        /* //   score.setLocation(new Point(OFFSETX,OFFSETY)) ;  
         field4.setText("This is a Text")  ; */  

        public void  emplaceState(boolean cause){
            this.should = cause ; 
        } 
        public boolean deriveState(boolean cause){
            return  this.should ;
        }
        /*   Random  random = new Random(System.nanoTime()) ; 
            int hattrick =  random.nextInt(TetrisBlock.Forms.metres.length) ; 
            TetrisBlock parts =  */
        public void restart(){
             configureFilled() ;    
             this.configurePoint() ;  
           //  this.configureSyncs() ;  
           // this.configureSpawner() ;    
           stream.clear() ;  //= new LinkedList<TetrisBlock>() ;  
           details.detach() ; 
            details =new TetrisDetails(this) ; 
            configureBrick() ;   
            startThreads(false) ;

        } 
        // long times  //     // times = System.currentTimeMillis() ; 
        /*       //  new Thread(){ 
           //     @Override 
         //       public void run(){  */      //    }}.run() ;
    public void updateBrick(){ 
            this.current.setDirection(new int[]{0,0}) ;   
                 long verse = System.currentTimeMillis( ) ;  
            while(true){ 
                if(System.currentTimeMillis()-verse>3000){break ; }  
                boolean first = Thread.currentThread().isAlive() ;
                boolean cases = checkBrick(current) ; 
                if(cases==false){ 
                    boolean second = Thread.currentThread().isInterrupted()  ; 
                     current.setDirection(new int[]{0,1}) ;  return  ; }
               long  temps = System.currentTimeMillis() ;    }   
            TetrisBlock  newer= this.spawner.yields() ;    
            while(!details.ensureBrick(newer)){
                newer = spawner.yields( ) ;  
            }
             updateFilled(this.current) ;  
            newer.setDirection(new int[]{0,1}) ;    
            this.current = newer ;    
            this.incrementScore(2l) ;   
        }      
        //   if(!TetrisDetails.ensureBrick(created)){continue ; }
  //  updateFilled(Arrays.stream(current.deriveMarks()).toList()) ;  
    //System.gc() ; // System.out.println(44) ;  
      //  @Override  //   System.out.println( cases) ;
        public void paint2(Graphics g) {
            // g=this.getParent().getGraphics();
         //   super.paint(g);
           Color   foreColor = new Color(50, 150, 250);
          //  this.setForeground(foreColor);
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if ((i + j) % 2 == 0) {
                       Color  backColor = colors[0] ;  // new Color(colors2[0], colors2[1], colors2[2]);
                        g.setColor(backColor);
                        g.drawRect(SPANSX * i, SPANSY * j, SPANSX, SPANSY)  ;     
                         g.fillRect(SPANSX*i,SPANSY*j,SPANSX,SPANSY);
                    }
                    if ((i + j) % 2 == 1) {
                        Color backColor =  colors[1] ;  //new Color(colors[0], colors[1],colors[2]);
                        g.setColor(backColor);
                        g.drawRect(SPANSX * i, SPANSY * j, SPANSX, SPANSY);
                         g.fillRect(SPANSX*i,SPANSY*j,SPANSX,SPANSY);
                    }
                }
            }  
        }   
        /*     //  super.paint(graphics) ;   
   ///  this.setForeground( new Color(20,20,20)) ; 
  // this.current.paint (graphics) ;     
     //    graphics.setColor (colors[2]) ; 
      //     graphics.fillRect(100,100,40,40) ; */  

    @Override 
    public void paint(Graphics graphics){    
         details.paint(graphics.create( )) ; 
        for(int fv=0;fv<HEIGHT;fv++){
            for( int df=0;df<WIDTH;df++){  
                 if(filled[fv][df]==true){  
                            graphics.setColor(nested[fv][df]) ; //( colors[2]  ) ; 
                            graphics.drawRect(df*SPANSX+OFFSETX,fv*SPANSY+OFFSETY,SPANSX,SPANSX) ; 
                            graphics.fillRect(df*SPANSX+OFFSETX,fv*SPANSY+OFFSETY,SPANSX,SPANSY) ;  
                    continue ;   }  
                if((df+fv)%2==0){  
                    graphics.setColor( colors[1] ) ; 
                    graphics.drawRect(df*SPANSX+OFFSETX,fv*SPANSY+OFFSETY,SPANSX,SPANSY) ; 
                //    graphics.fillRect(df*SPANSX+100,fv*SPANSY+100,SPANSX,SPANSY ) ; 
                }
                if((df+fv)%2==1){
                    graphics.setColor( colors[0]  ) ; 
                    graphics.drawRect(df*SPANSX+OFFSETX,fv*SPANSY+OFFSETY,SPANSX,SPANSY) ; 
                 //   graphics.fillRect(df*SPANSX+100,fv*SPANSY+100,SPANSX,SPANSY) ; 
                 } } 
                 }   
        this.current.paint(graphics.create())  ;   
    }    
    // paintSideLines() ; 
    /*   graphics.drawRect(100,100,50,50) ; 
                    graphics.fillRect(100,100,50,50) ;  */
    public static  boolean validate(int valueX,int valueY){
        if(valueY>=HEIGHT){return false ; } 
        if(valueX>=WIDTH){return false ; }
        if(valueX<0){return false ;  } 
         if(valueY<0){return false ;  }
         return true ;  
    }    
    public  boolean CheckPoint(int valueX,int valueY){
        if(valueY<0){return false ; } 
        if( valueX<0 ){return false ; }   
        if(valueY>=HEIGHT) {return false ; } 
        if(valueX>=WIDTH) {return false ; } 
        if(filled[valueY][valueX]==true){return false ;  }   
        return true ; 
    }
    //  if(checks[parts[1]+1][parts[0]]==true ){
    public boolean  checkBrick(TetrisBlock subject){  
          int[][] lines = subject.deriveMarks() ;   
          int[] start = subject.derivePosition() ;  
       //   try{
          for(int ve=lines.length-1;ve>=0;ve--){ 
            int[] parts =  lines[ve] ;      
            if(!validate(start[0]+parts[0],start[1]+parts[1]+1)){//System.out.println(33) ;
              return  true  ;  }
            if(start[1]+parts[1]+1>=HEIGHT){//System.out.println(55) ;
                 return true  ;  }// Thread.sleep(150) ;
          //  if(subject.derivePosition()[1]+subject.deriveMarks()[ve][1]+1>=HEIGHT){  return true ;}//else{return false ; }  }
            if( filled[start[1]+parts[1]+1][start[0]+parts[0]]==true ){ //  System.out.println(44 ) ;
                 return true ;}// Thread.sleep(150) ; 
          //    if(filled[subject.deriveMarks()[ve][1]+1+subject.derivePosition()[1]]
         //     [subject.deriveMarks()[ve][0]+subject.derivePosition()[0]]==true)
        //      { return true ; }//else{return false ; }   } 
        } //}catch(InterruptedException error){ }  
     //   System.out.println("Checks") ; 
          return false ;  
    }    
    // List<int> serial
    public void updateFilled(TetrisBlock blocks ) {
        int[][]  nested = blocks.deriveMarks() ; 
        int[] point = blocks.derivePosition() ;
        for(int cr=nested.length-1;cr>=0;cr-- ){
            int[] parts = nested[cr]; 
            this.filled[point[1]+parts[1]][point[0]+parts[0]] = true ;  
            this.nested[point[1]+parts[1]][point[0]+parts[0]] = blocks.deriveColor() ; //getColor( ); 
        }  
      //  System .out.println(Arrays.deepToString(filled)) ;
        return  ;  
         }  
         public void incrementScore(long delta){
            this.score = this.score+delta  ; 
         }
    public void eliminateRow(int row){  
        if(row>=HEIGHT){return ; }
        for( int cd=row;cd>=0;cd--){   
            if(cd==0){filled[cd] = new boolean[WIDTH] ;
            Arrays.fill(filled[cd],false) ; continue ; }
            for( int df=WIDTH-1;df>=0;df--){
                filled[cd][df] = filled[cd-1][df ];  
                nested[cd][df] = nested[cd-1][df] ; 
            }
        }  
           this.incrementScore(5l) ; 
    }   
     // boolean state = false ; 
    public  List<Integer> probe(){
        List<Integer> starter = new ArrayList<Integer>() ; 
        for(int ce=HEIGHT-1;ce>=0;ce--){  
            boolean  checks  = true ; 
            for( int  fr=WIDTH-1;fr>=0;fr--){
                 if(filled[ce][fr]==false){checks=false ; break ; }  
            } 
            if(checks==true){starter.add(ce) ;  }  }  
        return starter ;     } 

    public Map<Integer,List<Consumer<Void>>>  deriveBindings(){  
        Map<Integer,List<Consumer<Void>>> mapper = new TreeMap<Integer,List<Consumer<Void>>>()  ;
        Iterator<Map.Entry<Integer,List<Consumer<Void>>>> iterator =  bindings.entrySet().iterator () ;   
        Iterator<Integer> iterand = bindings.keySet ().iterator () ; 
        while (iterand.hasNext()){
            Integer keys = iterand.next () ;  
            List<Consumer<Void>> value = bindings.get(keys) ; 
            List<Consumer<Void>> listed = new ArrayList<Consumer<Void>>(value) ; 
            mapper.put(Integer.valueOf (keys),listed) ; 
        }
         return  mapper ;   //this.bindings   ;      
    } 
  
    // if(posit[0]+brace[1]>=WIDTH){}  
  /*   public void confirmRotation(TetrisBlock block){ 
        int[] crest = block.DeriveShifts() ;    
         int[] frames =  block.derivePosition() ;      
        int positX = crest[0]+frames[1]-1 ;    
        int positY = crest[0]+frames[0]-1 ;  
        if(positX>=WIDTH){
            int deltaX= (positX-(WIDTH-1)) ; 
         block.displace(deltaX*-1,0) ;
        }   
        if(positY>=HEIGHT){
            int deltaY =  (positY-(HEIGHT-1)) ; 
            block.displace(0,deltaY*-1) ;
        }
    } */  
    /* int verse = posit[0]+brace[1]-1 ; 
                if(verse>=(WIDTH)){
                int diffs = Math.abs(verse-(WIDTH-1)) ; 
                current.displace(diffs*-1,0) ;  }  */   
        public  int[] deriveLimits(TetrisBlock input){ 
               int[] posit  =input.derivePosition() ; 
               int[] brace =  input.DeriveShifts() ; 
               int  limitY =  posit[1] ; 
                int limitX = posit[0] ;  
                 for(limitY=posit[1];limitY<HEIGHT;limitY++){
                    boolean  breaks =  false  ; 
                for(int ce=posit[0];ce<brace[1]+posit[0];ce++){  
                    if(ce>=WIDTH){continue ; }
                     if(filled[limitY][ce]==true){breaks=true ; break ; }  }  
                    if(breaks==true){break ; } }  
                for(limitX=posit[0];limitX<WIDTH;limitX++){
                    boolean  crest = false ; 
                    for(int vn=posit[1];vn<posit[1]+brace[0];vn++ ){  
                        if(vn>=HEIGHT){continue ; }
                        if(filled[vn][limitX]==true){crest= true; break ; } } 
                    if(crest==true){break ; }  }  
                return new int[]{limitX,limitY} ; 
        }
     public boolean confirmRotationC(TetrisBlock reference,int[] point ){
        int[][] nested =  reference.deriveMarks() ; 
        int[] crest = reference.derivePosition()  ; 
        int[][] recents =  reference.rotateC(nested) ; 
        for(int dr=0;dr<recents.length;dr++){   
            if(!validate(point[0]+recents[dr][0],point[1]+recents[dr][1]))
            { System.out.println(1) ;  return false ; }
            if(filled[recents[dr][1]+point[1]][recents[dr][0]+point[0]]==true)
            {System.out.println(2) ; return false ;}
        } 
        return true ;
    }
        public boolean confirmRotationA(TetrisBlock input,int[] point){
            int[][]  reach = input.deriveMarks() ;  
            int[] bytes =  input.derivePosition() ;  
            int[][] altered = TetrisBlock.rotateA(reach) ; 
            for(int  se=0;se<reach.length;se++){  
                if(!validate(altered[se][0]+point[0],point[1]+altered[se][1]))
                {  System.out.println(3) ;   return false; }
                if(filled[point[1]+altered[se][1]][altered[se][0]+point[0]]==true)
                {System.out.println(4) ; return  false  ; }
            }  
            return true  ; 
        }  
        
    public void configureBinds1(Map<Integer,List<Consumer<Void>>>  center){
         Integer value4 = KeyEvent.VK_UP ;
        Consumer<Void> consumer4  = new Consumer<Void>(){
            @Override 
            public void accept(Void voided){ 
                int[]  space =  current.derivePosition() ; 
                int[] lines = current.DeriveShifts() ;       
                int[] point =  details.confirmRotation(current,TetrisBlock::rotateA) ; 
                if(point[1]==-1){return  ; }  
                current.setPosition(point) ; 
                current.AntiClockWise () ;
            }
        } ; 
        EventListener.update(value4,consumer4,center) ; 
        Integer value5 = KeyEvent.VK_DOWN ; 
        Consumer<Void> consumer5 = new Consumer<Void>(){
            @Override 
            public void accept(Void voided){    
                int[] posit = current.derivePosition() ; 
                int[] brace = current.DeriveShifts() ;   
                int[] lines = details.confirmRotation(current,TetrisBlock::rotateC) ; 
                if(lines[0]==-1){return   ; }
                current.setPosition(lines) ;
                current.ClockWise() ; 
            }
        }  ;       
        EventListener.update(value5,consumer5,center ) ;  
    }  //   throws Exception
    public void configureBinds2(Map<Integer,List<Consumer<Void>>> center ){
        Integer value6 = KeyEvent.VK_LEFT ; 
        Consumer<Void> consumer6 = new Consumer<Void>(){
            @Override 
            public void accept(Void empty){  
                int[] point =  current.derivePosition() ;
                int[] shift =current.DeriveShifts( )  ;
                boolean probable =  details.confirmShift(new int[]{-1,0},current) ;   
                if(probable==false){return ; }
                current.displace(-1,0) ;  
              //  System.out.println(55) ; 
              }
        }  ; 
        EventListener.update(value6,consumer6,center) ; 
        Integer value7 =  KeyEvent.VK_RIGHT ; 
        Consumer<Void> consumer7 = new Consumer<Void>(){
            @Override 
            public void accept(Void clean){  
                int[]  posit =  current.derivePosition() ; 
                int drift[] = current.DeriveShifts( ) ;
                boolean probable =   details.confirmShift(new int[]{1,0},current ) ;  
                if(probable==false){return ;  }
                current.displace(1,0 ) ;    
            }
        } ; 
        EventListener.update(value7,consumer7, center ) ;  
        Consumer<Void> consumer8 = new Consumer<Void>(){
            @Override 
            public  void accept(Void nulls){
                if(holder==null){
                    TetrisBlock  recent = stream.poll( ) ;   
                    int[]  reach =  details.adjustBrick(recent,current.derivePosition( )) ;    
                    if(reach[1]==-1){return ;  }  
                    holder = current ;  
                    current =recent ;  
                    current.setPosition(reach) ;  //(holder.derivePosition( )) ; 
                    current.setDirection(holder.deriveDirection( ))  ;
                }else {
                    TetrisBlock temps =  holder  ;  
                    int[]  brace = details.adjustBrick(holder,current .derivePosition( )) ; 
                    if (brace[0]==-1){return ; }
                     holder= current ; 
                    current = temps  ;    
                    current.setPosition(brace ) ;  //(holder.derivePosition()) ; 
                    current.setDirection(holder.deriveDirection( )) ;      }   
                    long temps = System.currentTimeMillis() ; 
                    while(System.currentTimeMillis()-temps<50){ } }  }  ;    
            EventListener.update(KeyEvent.VK_END,consumer8,center ); 
    }       
               //  current.setDirection(new int[]{0,1}) ;  
    /*      for(int de=bytes.length-1;de>=0;de--){ 
                if(bytes[de][1]+yields[1]>=WIDTH-1){}
                if(filled[][bytes[de][0]+yields[0] ]) } */
    /*      for(int vr=0;vr<frames.length;vr++){  
                if(frames[vr][1]>0&& frames[vr][0]+space[0]<WIDTH-1){
                    if(filled[space[1]+frames[vr][1]][space[0]+frames[vr][0]+1]==true){return  ; }
                } 
                if(frames[vr][0]>0&&frames[vr][1]+space[1]>0){
                    if(filled[frames[vr][1]+space[1]-1][space[0]+frames[vr][0]]==true){return ; }
                }   
                if(frames[vr][1]<0&&frames[vr][0]+space[0]>0 ){
                    if( filled[frames[vr][1]+ space[1]][frames[vr][0]+space[0]-1]==true){return  ; }
                }
              }    */   
        /*  for(int vr=0;vr<bytes.length;vr++){  
                    if(bytes[vr][1]+posit[1]<HEIGHT-1&&bytes[vr][0]>0){
                        if(filled[bytes[vr][1]+posit[1]+1][bytes[vr][0]+posit[0]]==true){return  ;} }  
                    if(bytes[vr][1]<0&&bytes[vr][0]+posit[0]<WIDTH-1){
                        if(filled[bytes[vr][1]+posit[1]][bytes[vr][0]+posit[0]+1]==true){return  ; }
                    }   
                    if(bytes[vr][1]>0&&bytes[vr][0]+posit[0]>0){
                        if(filled[bytes[vr][1]+posit[1]][bytes[vr][0]+posit[0]-1]==true){return  ; }
                    }
                }     */
}  