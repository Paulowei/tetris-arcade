package com.moderneinstein.arcade.reactive.tetris;


import java.awt.Graphics ; 
import java.awt.Component ; 
import javax.swing.JLayeredPane  ;  
import java.awt.Color ; 
import java.awt.GridLayout ; 
import java.awt.Button ; 

import java.awt.event.KeyListener ; 
import java.awt.event.InputEvent ; 
import java.awt.event.KeyEvent  ; 
import javax.swing.JButton ; 

import java.awt.event.KeyAdapter ; 
import java.awt.event.ActionListener ; 
//import java.awt.event.ActionAdapter ; 
import java.awt.event.ActionEvent ; 

import java.util.Vector  ; 
import java.util.List ; 
import java.util.function.Consumer ;  
import java.util.Map ; 
import java.util.TreeMap ; 


// public class PausePane extends JLayeredPane 
public class CustomPane extends JLayeredPane {


        //  public ActionAdapter listener  ;  
        //  public KeyAdapter listener  ;
    public static class CustomButton extends JButton{
        private int  posX ; 
        private int posY ; 
        public int width ; 
        public int  height  ;      
        private int OFFSETX ; 
        private int OFFSETY ;   
        public Color color ;  
        public ActionListener listener  ;
        private Consumer<Void> callback ;   
      /*   @Override 
        public void paint(Graphics graphics){
            Graphics replica = graphics.create() ; 
            replica.setColor(this.color) ; 
            replica.drawRect(this.posX+OFFSETX,this.posY+OFFSETX,this.width,this.height) ; 
            replica.fillRect(this.posX+OFFSETY,this.posY+OFFSETY,this.width,this.height) ; 
        }  */
        public void setColor(Color input){
           // this.color =  // new Color(255,255,255,255) ; 
            Color created =  new Color(
                input.getRed(),input.getGreen(),
                input.getBlue(),input.getAlpha() ) ;  
            this.color = input ; 
        }
        @Override 
        public void update(Graphics source){
                // To-implement ;  //  
          //      this.addKeyListener(null) ; 
        } 
        public void setListener(Consumer<Void> grace){
            listener = new ActionListener(){
                @Override 
                public void  actionPerformed(ActionEvent event){    
                    int  identity = event.getID() ; 
                    int modify = event.getModifiers( ) ;
                    String straps = event.paramString() ; 
                    Object objects = event.getSource() ; 
                    long times = event.getWhen() ; 
                    Void voided =null ; 
                    grace.accept(voided) ; 
                }
            }  ; 
        }   
        public CustomButton(Consumer<Void> object) {
            super() ;  
            setListener (object) ; 
            this.addActionListener(listener )  ;     
            this.color = CustomPane.defaults  ; 
        }
        public CustomButton(int  pointX,int pointY,int spansX,int spansY){
            this.height =  spansY   ; 
            this. width = spansX ; 
            this.posY = pointY ; 
            this.posX = pointX ;     
            this.color = CustomPane.defaults ;    
            addActionListener(this.listener) ; 
        }    
        public CustomButton(int[] posits,int[] dists,Consumer<Void> caller){
                posY  = posits[1] ; 
                posX =  posits[0] ;  
                height = dists[1] ;  
                width = dists[0] ;  
                callback = caller ;   
                setListener (callback) ;  
                this.color = CustomPane.defaults  ;  
                addActionListener(this.listener) ; 
        }
        public  void setOffsets(int[] lines){
            this.OFFSETY =lines[1]  ; 
            this.OFFSETX = lines[0] ;   

        }  
        
    } 
    // List<CustomButton>    buttons 
    private List<Component> components ;   
    private Map<Integer,Component> mapper ; 
    private int OFFSETX ; 
    private int OFFSETY  ;
    public static Color defaults = new Color(60,80,140,180) ;  
    public static Color BackGround = new Color(130,160,70,80) ;
    public void initialisePane(){
      //  buttons.add(new CustomButton())  ;
    }  
    // //   buttons  new Vector<Component>() ;  // new  Vector<CustomButton>() ; 
    public CustomPane(){
        components =  new Vector<Component>() ; 
        mapper = new TreeMap<Integer,Component>() ; 

    }
    /* 
    @Override 
    public void update (Graphics graphics){
        int width = components.size() ; 
        for(int  rc=width-1;rc>=0;rc--){
            Component custom = components.get(rc) ; 
            custom.update(graphics) ;
        }
    }
    @Override  
    public void paint(Graphics graphics){
        int spans = components.size() ;
        for(int  rt=spans-1;rt>=0;rt--){ 
           Component custom = components.get(rt) ; 
            custom.paint(graphics) ; 
        }    
        graphics.setColor(BackGround)  ;   
        graphics.fillRect(0,0,this.getWidth(),this.getHeight()) ;
      //  System.out.println(this.getHeight()) ; 
      //  System.out.println(this.getWidth()) ;   
    }   */
    public void includeComponent(int notice,Component component){
        this.components.add (component) ;  
        this.mapper.put(notice,component ) ; 
    }   
    public Component  collectComponent(int post){
        Component reach = mapper.get(post) ; 
        return reach ;
    }   

    public void defaultConfigure(JLayeredPane subject,TetrisGame tetrisGame){
        subject.setLayout(new GridLayout())   ; 
        Consumer<Void> consumer1 = new Consumer<Void>(){
                @Override 
                public void accept(Void voided){

                } 
        } ; 
    }
}
