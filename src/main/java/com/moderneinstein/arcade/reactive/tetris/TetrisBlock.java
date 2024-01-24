package com.moderneinstein.arcade.reactive.tetris ; 

import java.awt.Component ; 
import java.awt.Graphics ;  
import java.awt.image.BufferedImage ; 
import java.awt.Color ; 

import java.util.Arrays ; 
import java.util.Vector  ;
import java.util.LinkedList ; 
import java.util.Random ; 

public class TetrisBlock extends Component  {
	
	/*		public static final int[][] forms2 = {{0,0},{1,0},{2,0},{3,0}} ;  //I-block
		public static final int[][] forms3 = {{0,0},{0,1},{1,1},{1,0}}  ;  //Square-block
		public static final int[][] forms4 = {{0,1},{1,1},{2,1},{2,0}} ;  //L-block-1
		public static final int[][] forms5  = {{0,0},{1,0},{2,0},{2,1}};  //L-block-2
		public static final int[][] forms6={{0,0},{1,0},{1,1},{2,0}} ;  //T-block ;
		public static final int[][] forms7 = {{0,0},{1,0},{1,1},{2,1}} ; //Z-block-2 ; 
		public static final int[][]forms8 = {{0,0},{0,1},{1,1},{1,2}} ;  //Z-block-1 ; */
	public static class Forms{	
		//	public static final int[][] forms1 ={{}}; 
		public static final int[][] forms2 = {{-1,0},{0,0},{1,0},{2,0}} ;  //I-block
		public static final int[][] forms3 = {{0,0},{0,1},{1,1},{1,0}}  ;  //Square-block
		public static final int[][] forms4 = {{0,0},{0,1},{0,2},{1,0}} ;  //L-block-1
		public static final int[][] forms5 = {{0,0},{0,1},{0,2},{1,0}};  //L-block-2
		public static final int[][] forms6={{0,0},{-1,0},{0,1},{1,0}} ;  //T-block ;
		public static final int[][] forms7 = {{0,0},{1,0},{-1,1},{0,1}} ; //Z-block-2 ; 
		public static final int[][]forms8 = {{0,0},{-1,0},{1,0},{1,1}} ;  //Z-block-1 ;
		public static final int[][][] metres = {forms2,forms3,forms4,forms5,forms6,forms7,forms8} ; 
	}
	
	private int[][] marks; 
	private int[] position ; 
	private int[] spans ;   
 	private  long thresh ;
	private long counts  ;   // System.nanoTime() ; 
	private int[] direction ;  
	public static Color[]  colors = {new Color(60,150,170)} ;
	public static final long[] defaults = new  long[]{350l,18l,18l} ;  
	private  Color color  ; 

	public TetrisBlock(int[][] centre,int[] start){ 
		super( ) ; 
		this.marks =  new int[centre.length][] ; // (int[][])centre.clone() ; 
		for(int cr=0;cr<centre.length;cr++){
			marks[cr] = (int[])centre[cr].clone() ;	}
		  position = (int[])start.clone() ;   
		counts = 0l  ; 
 		thresh = (long)defaults[0] ;  
 		this.spans = new int[]{(int)defaults[1],(int)defaults[2]} ;   
		direction = new int[]{0,0} ;   
		this.color = RandomColor( ) ; 
	}
	public TetrisBlock(int[][] centre,int[] start,int[] spread){  
		super( ) ; 
		this.marks = new int[centre.length][]  ; 
		for(int cd=centre.length-1;cd>=0;cd--){
		   marks[cd] = (int[])centre[cd].clone() ;} 
		position = (int[])start.clone()  ; 
		thresh = (long) defaults[0] ; 
 		counts = 0l  ;
		spans = Arrays.copyOf(spread,spread.length) ;  
		this.direction = new int[]{0,0} ;  
		this.color = RandomColor( ) ;  } 
	public TetrisBlock(int[][] lines,int[] begin,long delay){  
		super( ) ; 
		marks = new int[marks.length][3] ; 
		for(int fc=lines.length-1;fc>=0;fc--){
		   marks[fc] = Arrays.copyOf(lines[fc],lines[fc].length);	} 
	         spans =  new int[]{(int)defaults[1],(int)defaults[0]} ;  
		position = Arrays.copyOf(begin,begin.length) ;  
		this.thresh = delay   ;
		this.counts = 0l ;   
		this.direction= new int[]{0,0} ;  
		this.color = RandomColor( ) ;		
	}
	 public TetrisBlock(int[][] signs,int[] place,int[] spread,long delay){  
		super( )  ;
		marks =  new int[signs.length][] ; 
		for(int vf=signs.length-1;vf>=0;vf--){
			marks[vf] =Arrays.copyOf(signs[vf],signs[vf].length) ;	} 
		this.spans =  (int[])spread.clone() ;  
		position = Arrays.copyOf(place,place.length) ; 
 		this.counts = 0l; 
		this.thresh = delay  ;   
		direction = new int[]{0, 0} ;   
		this.color =RandomColor( ) ; 
		} 
		public Color RandomColor( ){
			Random random =   new Random(System.nanoTime( )) ; 
			Color  created = new Color(random.nextInt(256),
			random.nextInt(256 ),random.nextInt(256)) ; 
			return created ;   
			  //spawner.deriveRandom( ) ; 
			 
		}  
		public TetrisBlock(TetrisBlock input ){
			this.marks = new int[input.marks.length][] ; 
			for(int rt=0;rt<input.marks.length;rt++){
				this.marks[rt] =  (int[])input.marks[rt].clone() ; 
			} 
			this.spans =  Arrays.copyOf(input.spans,input.spans.length) ; 
			this.position =  (int[])input.derivePosition().clone() ; 
			this.thresh =  input.deriveThresh () ; 
			this.counts = 0l ; 
			this.emplaceColor(input.deriveColor()) ; 
		}
		/*	 this.position[1] = this.position[1]+direction[1] ; 
		 this.position[0] = this.position[0]+direction[0] ;     */
	@Override 
	public void update(Graphics source){  
		///this.counts = this.counts+1l ;  
		// this.paint(source.create( )) ; 	
		 if(System.currentTimeMillis()-counts>=thresh){
		//if(counts>= thresh){  
			this.counts = System.currentTimeMillis() ;  //  0l ; 
			displace (this.direction[0],this.direction[1]) ;  
			// this.ClockWise( ) ;   //this.AntiClockWise() ; 
	//	}
		}
	}   
	public void emplaceColor(Color input){
		this.color = new Color(input.getRed( ),input.getGreen( ),input.getBlue()) ;
	}
	public  Color  deriveColor (){
		Color output = new Color(color.getRed (),color.getGreen( ),color.getBlue()) ; 
		return output ; 
	}  
	public  int[] DeriveShifts(){
		int[][] lanes =  this.deriveMarks()   ; 
		int[] verse =  DeriveAlter(lanes) ;
		return verse ;
	}
	public static int[] DeriveAlter(int[][] input){
		int[]  crest[] =new int[][]{{0,0},{0,0}} ;   
		int xMax = 0 ; int xMin = 0 ; int yMax = 0 ; int yMin = 0 ; 
		for( int rv=0;rv<input.length;rv++ ){
			crest[1][1] = Math.max(crest[1][1],input[rv][1])  ; 
			crest[0][1] = Math.max(crest[0][1],input[rv][0])  ;   
			crest[1][0] = Math.min(crest[1][0],input[rv][1]) ; 
			crest[0][0] = Math.min (crest[0][0],input[rv][0]) ;
		}  
		return   new int[]{crest[0][1]-crest[0][0]+1 , crest[1][1]-crest[1][0]+1} ;  //   crest ;   
	 }      

	@Override
	public void paint(Graphics graphs){ 
	  	graphs.setColor(this.color) ;   //(colors[0]) ;    
		Graphics voltage = this.getGraphics( ) ; 
	//	System.out.println(System.currentTimeMillis()) ;  //(marks.length) ;  
	//	graphs.fillRect (20,20,10,10) ; 
	// graphs.drawRect(120,140,20,20) ; 
    //	System.out.println(Arrays.toString(this.position))  ; // deep
	  //   System.out.println(Arrays.toString(this.DeriveShifts())) ; 
		for(int dr=0;dr<marks.length;dr++){   
		///	graphs.fillRect(10,10,10,10) ; 
	//	graphs.setColor(RandomColor( )) ; 
			graphs.drawRect((position[0]+marks[dr][0])*spans[0]+TetrisGrid.OFFSETX,
			(position[1]+marks[dr][1])*spans[1]+TetrisGrid.OFFSETY,spans[0],spans[1]) ;  
		//	graphs.setColor(this.color)  ; 
 			  graphs.fillRect((marks[dr][0]+position[0])*spans[0]+TetrisGrid.OFFSETX,
			(marks[dr][1]+position[1])*spans[1]+TetrisGrid.OFFSETY,spans[0],spans[1]) ; } 
		 }  
		
	  // 		int lapse = 0 ;    
	  /*for (int fd=0;fd<width;fd++){	int[] parts  = } */  
	  // 	int[] recents =  new int[]{bytes[1]-portion[1]-1,portion[0]}  ;  
	  public void displace(int shiftX,int shiftY ){
		position[1] = position[1]+shiftY ;  
		position[0] = position[0]+shiftX ; 
 	  }
	public void ClockWise(){  
		int width = marks[0].length ; 
		int height = marks.length ; 
		int[][] created =  rotateC(marks) ; //new int[height][width]  ;  
		int[] bytes = this.DeriveShifts( ) ; 
		//for(int cr=0;cr<height;cr++ ){
//			int[] portion  =  marks[cr] ;  
		//	created[cr] =  new int[]{bytes[1]-portion[1]-1,portion[0]}  ;	} 
		this.marks = created ;   
	 //  this.counts+=System.currentTimeMillis()-thresh+150l    ;  
 } 

 	public static int[][] rotateC(int[][] source){
		int range = source.length ; 
		int[][] created = new int[range][] ;  
		int[] bases = DeriveAlter(source) ; 
		for(int  vr=0;vr<range;vr++){
			int[] parts  = source[vr] ;
			created[vr] = new int[]{parts[1],parts[0]*-1} ;//{bases[1]-parts[1]-1,parts[0]}  ;
		}
		return created ; 
	}    
	//   {brace[1],spaces[0]-brace[0]-1} ;
	public static int[][] rotateA(int[][] input){
	    int[][]  nested =   new int[input.length][] ; 
		int[]  spaces =  DeriveAlter(input)  ;
		for(int  ft=input.length-1;ft>=0;ft--){
			int[] brace =  input[ft] ; 
			nested[ft] = new int[]{brace[1]*-1,brace[0]} ;
		}
		return  nested ; 
	}  
  
		//	return decent ;     	}
	public void AntiClockWise(){
		int  spans = marks.length ; 
		int spread = marks[0].length ;
		int[][]  decent = rotateA(marks) ; // new int[spans][] ; 
		int[] dists = this.DeriveShifts() ;  
	for (int vt=0 ;vt<spans;vt++){
	//		int[] portion  =  marks[vt] ; 
	//	decent[vt] = new int[]{portion[1],dists[0]-portion[0]-1}  ; 
		} 
		 this.marks=decent ;    
	//	this.counts+=System.currentTimeMillis()-thresh+150l ;  
	  	}
	
	public void setPosition(int[] input){
		this.position =  Arrays.copyOf(input,input.length) ; 
	}    
	public void setSpans(int[] input){
		this.spans =  (int[])input.clone() ;
	}  
	public void setThresh(long digit){
		this.thresh = digit ; 
	}   
	public int[] deriveSpans(){  
		int[] verse = (int[])Arrays.copyOf(this.spans,spans.length) ;
		return  verse  ;   //  this.spans  ;
	}   
	public int[] derivePosition(){ 
		int[] crease =  (int[])this.position.clone() ;
		return  crease ; 
	}   
	 public  long deriveThresh (){ 
		return this.thresh ; 
	 }   
	public void setDirection(int[] movement){  
		int draft = movement.length ; 
		this.direction =  new int[movement.length] ; 
		for(int  rt=0;rt<draft;rt++){
			this.direction[rt] = movement[rt] ; }
	}
	public int[] deriveDirection( ){ 
		int[] brace = (int[])Arrays.copyOf(direction,direction.length) ;
		return   brace  ;  // this.direction ; 
	}  
	public void emplaceMarks(int[][] nested){
		 this.marks = new int[nested.length][] ; 
		 for(int vr=0;vr<nested.length;vr++){
			this.marks[vr]  =  (int[])Arrays.copyOf(nested[vr],nested[vr].length) ;
		 }
	}  
	public int[][] deriveMarks( ){  
		int[][] nested =new int[marks.length][] ; 
		for (int   index=0;index<marks.length;index++){
			nested[index] =  (int[])marks[index].clone() ; 
		}
		return  nested  ; //  this.marks  ; 
	}  
	public  static int[] confirmRotation(TetrisBlock block,int limitX,int limitY){ 
        int[] crest = block.DeriveShifts() ;    
         int[] frames =  block.derivePosition() ;      
        int positX = crest[1]+frames[0]-1 ;    
        int positY = crest[0]+frames[1]-1 ;  
        if(positX>=limitX){    
            int deltaX= (positX-(limitX-1)) ; 
     //    block.displace(deltaX*-1,0) ;      
	//	 frames[0]-=deltaX ; 
		   } 
        if(positY>=limitY){
            int deltaY =  (positY-(limitY-1)) ; 
       //     block.displace(0,deltaY*-1-1) ;  
			frames[1]-=(deltaY+0) ;  }
		return frames ;  }  
	}