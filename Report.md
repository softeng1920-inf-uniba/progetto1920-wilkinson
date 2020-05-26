# INDICE
1. [Introduzione](#Introduzione)
2. [Modello di dominio](#Modello-di-dominio)
3. [Requisiti specifici](#Requisiti-specifici)
    - [Requisiti funzionali](#Requisiti-funzionali)
    - [Requisiti non funzionali](#Requisiti-non-funzionali)
4. [System Design](#System-Design)
    - [Stile architetturale adottato](#Stile-architetturale-adottato)
    - [Diagramma dei package](#Diagramma-dei-package)
    - [Diagramma dei componenti](#Diagramma-dei-componenti)
    - [Commenti](#Commenti-SD)
5. [OO Design](#OO-Design)
    - [Diagrammi delle classi e diagrammi di sequenza](#Diagrammi-delle-classi-e-diagrammi-di-sequenza)
    - [Design pattern utilizzati](#Design-pattern-utilizzati)
    - [Commenti](#Commenti-OO)
6. [Riepilogo del test](#Riepilogo-del-test)
7. [Manuale utente](#Manuale-utente)
8. [Processo di sviluppo e organizzazione del lavoro](#Processo-di-sviluppo-e-organizzazione-del-lavoro)
9. [Analisi retrospettiva](#Analisi-retrospettiva)
    - [Soddisfazioni](#Soddisfazioni)
    - [Insoddisfazioni](#Insoddisfazioni)
    - [Cosa ci ha fatto impazzire](#Cosa-ci-ha-fatto-impazzire)


# Introduzione

**SCACCHI** è figlia del corso di INGEGNERIA DEL SOFTWARE tenuto dal [*Professore Filippo Lanubile*](http://www.di.uniba.it/~lanubile/) nell'Anno Accademico 2019-2020, dal gruppo ***wilkinson*** composto da:
- [Gianluca Laera](https://github.com/gian01pie)
- [Stefano Lozito](https://github.com/stefanolozito)
- [Vincenzo M.G. Martemucci](https://github.com/raimondoDiSangro)
- [Andrea Perruggini](https://github.com/Perru21)
- [Nicola Ragone](https://github.com/nicolara96)
- [Giuseppe Sancesario](https://github.com/Giuseppe199925)
- [Pierpaolo Ventrella](https://github.com/pventrella20)
 <br>
 
<br> <br>
[Torna all'indice...](#Indice)

# Modello di dominio

<center>
<img width="700" alt="Modello_di_dominio.jpg" src="./drawings/ModelloDiDominio/Modello_di_dominio.jpg">
</center>

<br> <br>
[Torna all'indice...](#Indice)

# Requisiti specifici

### Requisiti funzionali

### Requisiti non funzionali

<br> <br>
[Torna all'indice...](#Indice)

# System Design

### Stile architetturale adottato

<br>
  <img width="600" src="drawings/SystemDesign/MVP.PNG">
<br>

### Diagramma dei package

<br>
  <img width="600" src="drawings/SystemDesign/diagramma-package.png">
<br>

### Diagramma dei componenti

<br>
  <img height="400" src="drawings/SystemDesign/diagramma-componenti.PNG">
<br>

### <a name="Commenti-SD"></a>Commenti delle decisioni prese


<br><br>
[Torna all'indice...](#Indice)

# OO Design
### <a name="Diagrammi-delle-classi-e-diagrammi-di-sequenza"></a> Diagrammi delle classi e diagrammi di sequenza

* Diagramma delle classi ...

<center>
	<img width="900" alt="Classes.png" src="./drawings/OODesign/Classes.png">
</center>

<br> <br>

... Diagramma di sequenza relativo alla user story x dello sprint x ...

<center>
	<img width="900" alt="Sprint1Query1Sequence.png" src="./drawings/OODesign/Sprint1Query1Sequence.png">
</center>

<br> <br>

... Diagramma di sequenza relativo alla user story y dello sprint y.

<center>
	<img width="900" alt="Sprint2Query1Sequence.png" src="./drawings/OODesign/Sprint2Query1Sequence.png">
</center>

<br> <br>

<a name="Design-pattern-utilizzati"></a>

### Design patterns
 

<a name="Commenti-OO"></a>

### Commenti


<br><br>
[Torna all'indice...](#Indice)

# Riepilogo del test

<br>

<img height=700 src="drawings/RiepilogoTest/coverall-List.PNG">

<img height=400 src="drawings/RiepilogoTest/coverall.PNG">

<img src="drawings/RiepilogoTest/jacoco.PNG">

<img height="120" src="drawings/RiepilogoTest/TestSummary.PNG">

### <a name="Commenti-TEST"></a>Commenti delle decisioni prese
* abbiamo preso la decisione di non testare i metodi di stampa (per visualizzare le catture, la scacchiera o le mosse effettuate) poichè ritenuti di poco interesse e di funzionalità facilmente testabile "visivamente".  
<br><br>
* in particolare questi ultimi sono dislocati all'interno delle classi *Board.java* e *Game.java*, inoltre le singole classi dei pezzi (derivate dalla classe astratta *Piece.java*) contengono un metodo che ritorna il carattere Unicode corrispondente, anch'esso non testato per i motivi di cui sopra.

<br><br>
[Torna all'indice...](#Indice)

# Manuale utente

Per avviare l'applicazione bisogna:

 1. Lanciare il comando per scaricare l'immagine docker più recente
```
    docker pull docker.pkg.github.com/softeng1920-inf-uniba/docker_1920/wilkinson:latest
```
 2. Lanciare il comando per avviare l'immagine docker in locale
```
    docker run -it --rm docker.pkg.github.com/softeng1920-inf-uniba/docker_1920/wilkinson:latest
```
   per utilizzare Scacchi sul proprio terminale, occorre scaricare l'immagine Docker dell'ultima build, ed in seguito, avviarla.  
###### N.B.: se l'utente sta utilizzando Git Bash for Windows, deve aggiungere il prefisso "winpty" prima dell'intero comando
###### N.B.: Per eseguire l'applicazione sui terminali Windows e terminale macOS, a prescindere dalla shell selezionata, deve essere lanciata prima l'applicazione docker e caricati i container Linux.
<br><br>
* L'applicazione consente di giocare una partita di **scacchi** da un singolo terminale, condividendo il metodo di input con un altro giocatore o svolgere una partita in solitaria.
<br><br>
* All'interno del gioco sono presenti diversi comandi utili all'utente(i) al fine di rendere migliore l'esperienza di gioco; alcuni di essi sono utilizzabili correttamente solo a partita in corso.
<br><br>
* il comando `play` consente di iniziare una nuova partita e offre queste funzionalità:  
   - la possibilità di introdurre mosse in `notazione algebrica abbreviata` per proseguire con la partita appena iniziata  
   - `board`: mostra la scacchiera con la configurazione attuale dei pezzi presenti sulla scacchiera  
   - `moves`: mostra in notazione algebrica tutti i movimenti eseguiti fino al lancio del comando  
   - `captures`: mostra tutte le catture avvenute fino al momento in cui viene eseguito il comando  
   - `help`: mostra i comandi disponibili per l'applicazione  
   - `play`: a partita in corso, consente di interrompere la partita corrente, e previa conferma, iniziarne una nuova  
   - `quit`: permette all'utente di abbandonare la partita in corso, previa conferma  

<br> <br>
[Torna all'indice...](#Indice)

# Processo di sviluppo e organizzazione del lavoro


<br> <br>
[Torna all'indice...](#Indice)

# Analisi retrospettiva
- ### Soddisfazioni
    
    <br> 

- ### Insoddisfazioni

    <br>

- ### Cosa ci ha fatto impazzire

    <br>

<br> <br>
[Torna all'indice...](#Indice)
