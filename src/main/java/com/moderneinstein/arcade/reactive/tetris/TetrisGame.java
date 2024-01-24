package com.moderneinstein.arcade.reactive.tetris   ;

import  javax.swing.JPanel ;
import java.awt.Component ; 
import java.awt.Graphics ;  
import java.awt.image.BufferedImage ;   
import java.awt.event.KeyListener ; 

import java.awt.Dimension   ;  
import javax.imageio.ImageIO ; 
import javax.swing.JFrame ; 
import java.awt.Rectangle ; 
import java.awt.Color ;

import javax.swing.JLayeredPane ; 
import java.awt.LayoutManager ; 
import  java.awt.GridLayout ; 
//import javax.swing.LayeredPane ; 
import javax.swing.WindowConstants ; 
import java.awt.event.WindowEvent ; 
import java.awt.BorderLayout ; 
import java.awt.Point ; 

import java.util.Arrays ; 
import java.util.Vector ; 
import java.util.Map ;
import java.util.function.Consumer ; 
import java.util.List ; 
import java.awt.event.KeyEvent ;  

import java.util.ArrayList ; 
import java.io.InputStream ; 
import java.io.File ; 
//import java.awt.BufferedImage ; 
import java.util.TreeMap ; 
import javax.swing.JButton ; 
import java.io.FileInputStream ; 
import java.io.IOException ; 

import java.util.HashMap ;
import javax.imageio.stream.ImageInputStream ; 
import java.awt.Button ;


import javax.sound.midi.MidiSystem ; 
import javax.sound.midi.spi.MidiFileReader ; 
import javax.sound.midi.Sequence ; 
import javax.sound.midi.Sequencer ; 
import javax.sound.midi.Track ;  
import javax.sound.midi.MidiFileFormat ; 

import com.moderneinstein.arcade.reactive.tetris.CustomPane.CustomButton ; 


public class TetrisGame extends JFrame{
    //  List<ContentPane>
  //  public List<Component> below ; 
    public Graphics  central ; // graphics ; 
    private Map<Integer,List<Consumer<Void>>>  mapper ; 
     public TetrisGrid gridder ; 
    private List<JLayeredPane> panes ;  
    public String[] RootPath = { "src/main/resources/static/","../src/main/resources/static/" } ;  
    public static String[]  ImageName = {"TetrisImage.jpg"} ;  
    public static String[] MUSIC_FILES = {"100444.mid"} ; 
    //"terminalBell.mp3" ; // "zgachaaf.mp3" ;
    public JLayeredPane active ;    
    public CustomPane PausePane ;   
    private Map<Integer,Component> buttons ;   
    public Map<String,Object> options ;   
    public KeyListener listener ;  
    public Sequencer sequencer ;  

  //  public TetrisDetails details ;  
    /*   panes.add (this.getLayeredPane()) ;
        panes.add(configurePane()) ; */  
        //   setLayeredPane(active) ;  
    public TetrisGame( ){    
        super( )  ; 
       // central  =  new Button().getGraphics( ) ;   
       mapper =  new HashMap<Integer,List<Consumer<Void>>>() ; 
        panes = new ArrayList<JLayeredPane>() ;     
         gridder = new TetrisGrid() ;  
         buttons = new TreeMap<Integer,Component>() ;   
         options = new TreeMap<String,Object>() ; 
     //   details = new TetrisDetails () ; 
        this.init4( ) ;
          this.init5( )  ;   
        this.init6 () ;  
        this.init7() ;   
        this.playSound( ) ;
   ///   this.add(gridder) ;     
        // this.add(gridder) ;    
       //  configurePausePane() ; 
         //  this.setLayeredPane(PausePane) ;
    }     
    public void init7(){
         this.configureMapper() ; 
        this.configureListener() ;  
          this.setImage(ImageName[0]) ;   
          active = panes.get(1) ;    
          PausePane =  (CustomPane)panes.get (2)  ;   
           configurePane2() ; 
          configurePane3() ;  
          this.validate() ; 
    } 

      public JLayeredPane configurePane1(){
       JLayeredPane created = new JLayeredPane() ; 
        created.setLayout(new  GridLayout()) ;  
        created.add(gridder,1) ;        
      //  created.add(details,1) ; 
        created.setLayer(gridder,JLayeredPane.MODAL_LAYER,2);     
        created.setVisible (true) ;    
         created.requestFocus( ) ;  
       //  created.validate() ;  
          return  created ;  //null ;    //    return created ; 
    }  
    /*        setBounds(new Rectangle(610,610))   ; 
        setBounds(new Rectangle(700,700 ) ) ;   */
        //  this.setLayeredPane(created) ;   
     //  gridder = new TetrisGrid() ; 
        //()this.getKeyListener( ) ;   
             // created.setBounds(new Rectangle(100 ,100,200,200 )) ;   
      
    public void init4(){
        this.frameInit( ) ;  
        this.setEnabled( true ) ;
        this.setFocusTraversalKeysEnabled(true) ;
        this.setVisible(true) ; 
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE ) ;    
        this.setLayout(new BorderLayout( )) ;  
        processWindowEvent (new WindowEvent(this,WindowEvent.WINDOW_ACTIVATED)) ; 
            }   
            //   KeyAdapter Adapter = new EventListener( ) ;
    public void init5( ){
        this.setPreferredSize( new Dimension(500,500 ) ) ; 
        this.setBounds( new Rectangle(500,500) );    
        this.setAutoRequestFocus(true) ; 
        this.requestFocus() ;  
       this.setPreferredSize(new Dimension(750,750 )) ; 
          this.setBounds( new Rectangle( 750,750) );      
    //   this.setBackground(new Color(160,140,120)) ;
   //     this.setLocationRelativeTo(null) ;   
    }    
    public void initialiseMap(){
     //    options 
    }
    public void configureListener(){
        EventListener Adapter = new EventListener( ) ;
        Adapter.append(this.mapper ) ; 
        Adapter.append(this.gridder.deriveBindings()) ;     
         this.addKeyListener(Adapter) ;  
        this.createBufferStrategy(4) ;  
        listener = Adapter ;   
     //   System.out.println(Adapter.handler.toString()) ; 
    }
    public void init6(){ 
        this.setLayout(new BorderLayout( ))  ;
       panes.add(this.getLayeredPane()) ;
       panes.add(this.configurePane1())  ;  
       panes.add(new CustomPane()) ; 
      this.setLayeredPane(panes.get(1)) ;   
       this.setLocation(new Point( 450,100)) ;   
       this.validate() ;     }
    /*@Override 
    public  void paint( ){   
        super.paint() ;
        // 
    } */
      //    this.getContentPane().update(graphics) ;   
      public void changePane(JLayeredPane    present){
        this.setLayeredPane( present) ;    
      //  present.requestFocus( ) ; 
        present.validate(); 
         this.validate() ;    
        this.requestFocus() ;  

      }
    @Override 
    public void update(Graphics graphics){  
    if(active!=null){    
      //  active.setLocation (350,350) ; 
      active.repaint( ) ;
        active.update(graphics) ;         
      } 
   if(this .gridder!=null){
       this.gridder.repaint() ; 
       this.gridder.update(graphics)  ;  
       gridder.paint(graphics) ;
      }     
      PausePane.repaint()  ;  
      PausePane.paint(graphics) ; 
       PausePane.update(graphics) ;  
       SetButtonTexts() ; 
    }
 //   for(int ct=0;ct<panes.size();ct++){
    //    panes.get(ct).update(graphics) ; }   
    //this.panes.get(1).update( graphics) ;   
          ///  gridder.paint(graphics.create() ) ;  //    } 
    public File createFile(String point,String[] alias){   
      int height = alias.length ;   
      File file4 =  null ; 
      for (int  rc=height-1;rc>=0;rc-- ){
        String verse = new String(alias[rc]).concat(point) ; 
        file4 = new File(verse) ; 
        if(file4.exists()){
            break    ;    }  } 
        return file4 ; 
    }  
/* String first = new String(alias[1]).concat(point) ; 
        File file4 = new File(first)  ; 
        if(!file4.exists()){
          String second = new String(alias[0])  ; 
          file4 =new File(second) ; 
        } 
        return file4 ;  */  
    public void setImage(String source){  
        try{  
        File point =  createFile(source,RootPath) ;
        ImageInputStream streams = ImageIO.createImageInputStream(point) ;  
            BufferedImage buffered = ImageIO.read(streams) ; 
                this.setIconImage(buffered) ;
    }catch(IOException except){
        System.out.println(except.toString()) ;
    }   }

      /*    try{  
          String created = new String(RootPath[1]).concat( source); 
        File point =   new File(created) ;  //createFile(source) ;  
        if(!point.exists()){
          created = new String(RootPath[0]).concat(source)  ; 
          point = new File(created) ; 
        } */
    public void configurePausePane(){ 
        PausePane.setLayout(new GridLayout()) ;
        CustomButton custom1 = new CustomButton(new int[]{110,100},new int[]{210,90},
         new Consumer<Void>(){
          @Override 
          public void accept(Void voided){
             }  }) ;  
        CustomButton custom2 = new CustomButton(new int[]{430,100},new int[]{210,90},
         new Consumer<Void>(){
           @Override 
           public void accept(Void voided){
                   }  }) ;   
         // CustomButton custom3 = new CustomButton(new int[]{110,100},new int[]{210,})
         this.PausePane.includeComponent(2,custom2)  ; 
         this.PausePane.includeComponent(1,custom1) ;  
         this.PausePane.validate() ; 
    }    
        //   panes.get(1).remove(0) ; 
         //   gridder = new TetrisGrid() ; 
         //   panes.get(1).add(gridder,1) ;     
         
    public void configurePane2(){  
      GridLayout gridLayout =  new GridLayout(2,2) ; 
      PausePane.setLayout(gridLayout) ; 
      CustomButton custom1 =  new CustomButton(
        new Consumer<Void>(){
          @Override 
          public void accept(Void voided){changePane(panes.get(1)) ;  
            gridder.emplaceState(true) ;  
           // System.out.println(43) ;  
            panes.get(1).requestFocus() ;  
            requestFocus() ;   }  }  ) ;    
      CustomButton custom2 = new CustomButton( 
        new Consumer<Void>(){
          @Override 
          public void  accept (Void voided){    
         gridder.restart() ;    
            gridder.emplaceState(true) ;     
            panes.get(1).requestFocus() ; 
            requestFocus() ;    }  }  ) ;         
       PausePane.add(custom1) ;     
       PausePane.add(custom2) ;  
      buttons.put(2, custom2) ; 
      buttons.put(1,custom1) ;  
      PausePane.validate() ;      }      
            //   ((EventListener)(listener)).append(gridder.deriveBindings()) ;     
           //   addKeyListener(listener) ; 
    public void configurePane3(){
      CustomButton  button3= new CustomButton(
        new Consumer<Void>(){
          @Override 
          public void accept(Void voided){
              sequencer.start() ; 
          }
        }
      ) ;  
      CustomButton button4 = new CustomButton(
        new Consumer<Void>(){
          @Override 
          public void accept (Void voided){
            sequencer.stop() ; 
          }
        }
      ) ;   
      buttons.put(4,button4) ; 
      buttons.put(3, button3) ;   
      PausePane.add(button3) ; 
      PausePane.add(button4) ; 
       PausePane.validate() ;
      SetButtonTexts( ) ; 
    }
    public void configureMapper(){
         Integer event1 = KeyEvent.VK_SHIFT ;  
        Consumer<Void> consumer1 = new Consumer<Void>(){
          @Override 
          public void accept(Void voided){
            changePane(panes.get(2)) ;    
             gridder.emplaceState(false) ;   
          //   System.out.println(44) ;   
          }
        }  ; 
        EventListener.update(event1,consumer1,this.mapper) ;  
        Integer event2 = KeyEvent.VK_CONTROL  ; 
        Consumer<Void> consumer2 = new Consumer<Void>(){
          @Override 
          public  void accept(Void voided){
            changePane(panes.get(1)) ;  
            gridder.emplaceState(true) ;     
          //  System.out.println(55); 
             
          }
        }  ;  
        EventListener.update(event2,consumer2,this.mapper) ;  
    }
    
        //   super.update(graphics)  ;      
    //this.setContentPane(active) ;   
  //  graphics.setColor(new Color(40,100,60)) ;
   // graphics.fillRect(50,50,100,100) ; 
  ////  graphics.drawRect(50,50,100,100) ; 
       //     created.setBounds(new Rectangle(0 ,0,400,400 )) ;     
      //  this.setLayeredPane(created) ;     
             // created.setAutoRequestFocus(true) ;   
   //  created.setLocation( 100,100) ;  // setContentPane(created) ;   
       //  created.setLayout(new BorderLayout()) ;    
              // getLayeredPane().add(gridder,0) ; 
       //   created.add(gridder,0) ;    
            //  panes.add(this.getLayeredPane()) ;    

          public void SetButtonTexts(){
              ((JButton)buttons.get(4)).setText("Pause Music") ; 
              ((JButton)buttons.get(3)).setText("Play Music") ;
            ((JButton) buttons.get(2)).setText("Restart") ; 
              ((JButton)buttons.get(1)).setText("Resume") ;
          } 
        public void playSound (){  
          File source ; 
          InputStream streams ; 
            try{
                source  =  createFile(MUSIC_FILES[0],RootPath) ;   // new File(MUSIC_FILES[0]) ; 
                streams = new FileInputStream(source)  ; 
                System.out.println (source.exists() ) ; 
       //   MidiFileReader reader = new MidiFileReader () ;
          Sequence sequence = MidiSystem.getSequence (streams) ;   
          sequencer =  MidiSystem.getSequencer()  ;
           Track[] buffers =  sequence.getTracks () ; 
          sequencer.setSequence(sequence) ;  //(sequence) ; 
          sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY) ;   
          sequencer.setLoopStartPoint(0l) ;   
       sequencer.open() ; 
          sequencer.start() ;  
        }catch(Exception except){
          except.printStackTrace() ;  
        } 
        }
}  
 //  this.configurePane() ;  //   
      ///    gridder = new TetrisGrid() ;   
    //  this.setLayout(new BorderLayout())  ;  
    //  this.setComponentZOrder(active,0)   ;  
        //  beginLoop() ;       

      /*    public void beginLoop(){   
       // this.add( this.gridder) ; 
      //  active.setLocation (450,550) ;
       // active.repaint() ;     
         //  this.setPreferredSize(new Dimension(600,600 )) ; 
      //    this.setBounds( new Rectangle(600,600) );      
       //   this.setLocation(new Point(150, 150)) ;   
    //  this.setLayeredPane( active) ;   
     //  this.setContentPane(active) ;  
     //  BufferedImage  verse = new BufferedImage(50,50,BufferedImage.TYPE_INT_RGB) ; 
         // Graphics taken = this.getGraphics( ) ;  
      //    Graphics  graphics = verse.getGraphics( ) ;
     /////   while(true){
            //this.update( taken ) ;  
      //      this.update(graphics) ; 
     //   }
    }
    // JLayeredPane[] //     JLayeredPane[] buffers =  new JLayeredPane  
    // created.setLayout(new GridLayout(1,1))   ;     */