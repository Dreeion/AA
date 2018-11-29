package processing.test.tcdea;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TCDEA extends PApplet {

/***********************************************************/
// Nom du programme : TCDE
// Auteur(s) :Dreion & Daeranil & Hupsy
// Date :29/11/2018
// Version :0.006
///***********************************************************/
// Description du programme :
//===========================
// Un jeu dans l'espace.
/***********************************************************/
 
 
/***********************************************************/
/******************* Entête déclarative ********************/
/***********************************************************/ 



// --- inclusion des librairies utilisées ---


// --- librairie sons ---







// --- déclaration objets ---
boolean a;
int xJoueur;
int yJoueur;
int vitesse = 15;//vitesse
int yf = 0;
int vie = 300;
PImage joueur;
PImage mahmoud;
PImage fond;
PImage UI;
float fin;

float xMahmoud[]= new float[100];
float yMahmoud[]= new float[100];
float xTir[]= new float[50];
float yTir[]= new float[50];

int j = 10;
int p = 1;
PImage pp;
 
 
 int idMahmoud = 1;
 int idTir = 1;
 int m;

Minim minim;
AudioSnippet ping;

// --- déclaration variables globales ---

/********************* Fonction SETUP **********************/
// fonction d'initialisation exécutée 1 fois au démarrage
/***********************************************************/

public void setup ()
{  
  yTir[1]=-100000;
  
  // --- initialisation des musiques ---
  minim = new Minim(this);
  ping = minim.loadSnippet("Musique.wav");
  ping.play();
  
  // --- initialisation fenêtre de base ---
  
   // ouvre une fenêtre xpixels  x ypixels
  background(0); // couleur fond fenetre ( noir )
 
  // ---- initialisation paramètres graphiques utilisés ---
  noCursor();

  // --- attributions des variables images ---
  fond = loadImage("data/Images/stars.jpg");               //Charger l'image du fond d'écran
  fond.resize(width,height);                               // Taille du fond d'écran 

  joueur = loadImage("data/Images/joueur"+j+".png");        //Charger l'image du vaisseau du joueur
  joueur.resize(width/8,height/10);                       //Taille du joueur

  mahmoud = loadImage("data/Images/mahmoud1.png");       //Charger l'image des ennemis
  mahmoud.resize(75,75);                                 //Taille des ennemis

  UI = loadImage("data/Images/UI.png");

  // --- attributions des variables numériques ---

  xJoueur=width/2-width/8/2;yJoueur=height-height/10*2; //Coordonnées de départ du joueur                                         // Vitesse des ennemis                                             //Point de vie de base du joueur                                                  //Coordonnée de départ du fond d'écran (pour le défilement verticale)
  
//menu();
// --- définition des coordonnées des ennemis de la première vague

nouvelle_Vague();
      
}
/********************** Fonction DRAW **********************/
//             fonction exécutée en boucle
/***********************************************************/

public void  draw(){
  
  finVague ();

  fond ();
  ennemis ();
  joueur ();
  tir();
  vie();
  image (UI,0,850);  
  debugMod ();
  
  delay (5);
 }
/********************** Fonction STOP **********************/
//          fonction exécutée quand le programme est fermé
/***********************************************************/
public void stop() {
  ping.close();
  minim.stop();
  super.stop();
}

// --- Affichage et défilement du fond d'écran -----------------------------------------------------------------------------------------------------------------------------------------
public void fond (){
  image (fond,0,yf);
  image (fond,0,yf-height);
  if (yf >= height) {yf = yf-height;}
  yf=yf+1;
}

// --- Création d'une nouvelle vague d'ennemis -----------------------------------------------------------------------------------------------------------------------------------------
public void nouvelle_Vague(){
  while (idMahmoud<=99){
      xMahmoud[idMahmoud] = random(425);     
      yMahmoud[idMahmoud] = random(-10000,-1000);
      idMahmoud=idMahmoud+1;

  }
  joueur = loadImage("data/Images/joueur"+j+".png");
return;
}

// --- Mod developeur -----------------------------------------------------------------------------------------------------------------------------------------
public void debugMod (){
  fill (255);
  text ("niveau="+ (vitesse-14),10,20);
  text ("Vie:"+vie,10,40);
}

// --- Tir -----------------------------------------------------------------------------------------------------------------------------------------
public void tir(){       
  if (yTir[1]<=-100){ xTir[1]=xJoueur+25; yTir[1]=yJoueur+20;}
  if (yTir[2]<=-100){ xTir[2]=xJoueur-30; yTir[2]=yJoueur+20;}
  yTir[1] = yTir[1]-50;
  yTir[2] = yTir[2]-50;
  fill (0xffFF0303);rect(xTir[1],yTir[1],5,-50);
  fill (0xffFF0303);rect(xTir[2],yTir[2],5,-50);
 
  while (idMahmoud<=99){
      if (xMahmoud[idMahmoud] >= xTir[1] && xMahmoud[idMahmoud]<=xTir[1]+5 && yMahmoud[idMahmoud]<=yTir[1]-50  && yMahmoud[idMahmoud]>=yTir[1]){xTir[1]=1000;xMahmoud[idMahmoud]=1000;}
      if (xMahmoud[idMahmoud] >= xTir[2] && xMahmoud[idMahmoud]<=xTir[2]+5 && yMahmoud[idMahmoud]+75<=yTir[1]+0  && yMahmoud[idMahmoud]>=yTir[1]){xTir[2]=1000;xMahmoud[idMahmoud]=1000;}
      idMahmoud=idMahmoud+1;
  }
  idMahmoud=1;
return;
}
// --------------------------------------------------------------------------------------------------------------------------------------------
public void ennemis(){
  while (idMahmoud<=99){
    if (yMahmoud[idMahmoud]>-100 && yMahmoud[idMahmoud]<1100) {image (mahmoud,xMahmoud[idMahmoud],yMahmoud[idMahmoud]);}
    if (yMahmoud[idMahmoud]<1300) {yMahmoud[idMahmoud]=yMahmoud[idMahmoud]+vitesse/2;}
    idMahmoud=idMahmoud+1;} 
  idMahmoud=1;
return;
}

// --------------------------------------------------------------------------------------------------------------------------------------------
public void finVague(){
  while (idMahmoud<=99){
    fin = fin + yMahmoud[idMahmoud];
    idMahmoud=idMahmoud+1;}
    idMahmoud=1;
  if (fin >= 129000) {idMahmoud=1;vitesse=vitesse+1;
    nouvelle_Vague(); 
}
  fin = 0;
return;
}

// --------------------------------------------------------------------------------------------------------------------------------------------
public void joueur(){
  image (joueur,xJoueur-width/8/2,yJoueur-height/10/2);
  if (xJoueur<mouseX){xJoueur=xJoueur+(mouseX-xJoueur)/10;}
  if (xJoueur>mouseX){xJoueur=xJoueur+(mouseX-xJoueur)/10;}
  if (yJoueur>mouseY){yJoueur=yJoueur+(mouseY-yJoueur)/10;}
  if (yJoueur<mouseY){yJoueur=yJoueur+(mouseY-yJoueur)/10;}
return;
}

// --------------------------------------------------------------------------------------------------------------------------------------------
public void vie(){
  fill (0xffFF0000);rect (50,950,vie,30);
  if (vie <= 0) {fill(0);rect(0,0,width,height);}
  while (idMahmoud<=99){
    if ((xJoueur+width/8/2+1>= xMahmoud[idMahmoud]) && (xJoueur<= xMahmoud[idMahmoud]+105) && (yJoueur+height/10/2-5>= yMahmoud[idMahmoud]) &&  (yJoueur-10<= yMahmoud[idMahmoud]+100)) {
      vie=vie-1;
    }
    idMahmoud=idMahmoud+1;
  }
  idMahmoud=1;  
return;
}

// --------------------------------------------------------------------------------------------------------------------------------------------














public void menu (){
image (fond,0,yf);







return;
}
  public void settings() {  size(500, 1000); }
}
