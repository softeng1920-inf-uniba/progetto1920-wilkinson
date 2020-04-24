# USER STORIES
_______________
### 1) MUOVERE UN CAVALLO
  - permettere il movimento del pezzo *cavallo* sulla scacchiera
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando in notazione algebrica abbreviata degli scacchi in italiano  
  >>> - la mossa deve essere legale
  >>> - se si tenta una mossa non valida viene visualizzato un messaggio *mossa illegale* e l'applicazione rimane in attesa di una mossa valida  
  >>> - la mossa deve essere preceduta da una 'C' 
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   |   |   |   |   |   | 8 |
|   |   | x |   | x |   |   |   | 7 |
|   | x |   |   |   | x |   |   | 6 |
|   |   |   | ♞ |   |   |   |   | 5 |
|   | x |   |   |   | x |   |   | 4 |
|   |   | x |   | x |   |   |   | 3 |
|   |   |   |   |   |   |   |   | 2 |
|   |   |   |   |   |   |   |   | 1 |

 >>> - ***movimento del cavallo***
 >>>    - il *Cavallo* si muove e cattura alternativamente su case bianche e case nere
 >>>    - ogni mossa può essere descritta come due passi in orizzontale (verticale) seguito da un passo in verticale (orizzontale), in modo che il tragitto percorso formi idealmente una "L"
 >>>    - il *Cavallo* è l'unico pezzo presente sulla scacchiera a cui è permesso "saltare" i pezzi, sia alleati, sia avversari 
 >>>    - l'unico dietro i pedoni che all'inizio può essere mosso senza bisogno di spostare prima questi ultimi
_______________
### 2) MUOVERE UN ALFIERE
  - permettere il movimento del pezzo *alfiere* sulla scacchiera
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando in notazione algebrica abbreviata degli scacchi in italiano  
  >>> - la mossa deve essere legale
  >>> - se si tenta una mossa non valida viene visualizzato un messaggio *mossa illegale* e l'applicazione rimane in attesa di una mossa valida  
  >>> - la mossa deve essere preceduta da una 'A' 
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
| x |   |   |   |   |   | x |   | 8 |
|   | x |   |   |   | x |   |   | 7 |
|   |   | x |   | x |   |   |   | 6 |
|   |   |   | ♝ |   |   |   |   | 5 |
|   |   | x |   | x |   |   |   | 4 |
|   | x |   |   |   | x |   |   | 3 |
| x |   |   |   |   |   | x |   | 2 |
|   |   |   |   |   |   |   | x | 1 |

 >>> - ***movimento dell'alfiere***
 >>>    - l'*Alfiere* si muove diagonalmente per il numero di caselle libere che ha a disposizione
 >>>    - l'*Alfiere* è l'unico pezzo che non può cambiare il colore delle case su cui si appoggia nei suoi movimenti in diagonale
_______________
### 3) MUOVERE UNA TORRE
  - permettere il movimento del pezzo *torre* sulla scacchiera
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando in notazione algebrica abbreviata degli scacchi in italiano  
  >>> - la mossa deve essere legale
  >>> - se si tenta una mossa non valida viene visualizzato un messaggio *mossa illegale* e l'applicazione rimane in attesa di una mossa valida  
  >>> - la mossa deve essere preceduta da una 'T' 
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   | x |   |   |   |   | 8 |
|   |   |   | x |   |   |   |   | 7 |
|   |   |   | x |   |   |   |   | 6 |
| x | x | x | ♜ | x | x | x | x | 5 |
|   |   |   | x |   |   |   |   | 4 |
|   |   |   | x |   |   |   |   | 3 |
|   |   |   | x |   |   |   |   | 2 |
|   |   |   | x |   |   |   |   | 1 |

 >>> - ***movimento della torre***
 >>>    - la *Torre* si muove sia orizzontalmente sia verticalmente per il numero di caselle libere che ha a disposizione
 >>>    - in congiunzione con il *Re*, può eseguire la mossa dell'arrocco
_______________
### 4) MUOVERE LA DONNA
 - permettere il movimento del pezzo *donna* sulla scacchiera
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando in notazione algebrica abbreviata degli scacchi in italiano  
  >>> - la mossa deve essere legale
  >>> - se si tenta una mossa non valida viene visualizzato un messaggio *mossa illegale* e l'applicazione rimane in attesa di una mossa valida  
  >>> - la mossa deve essere preceduta da una 'D' 
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
| x |   |   | x |   |   | x |   | 8 |
|   | x |   | x |   | x |   |   | 7 |
|   |   | x | x | x |   |   |   | 6 |
| x | x | x | ♛ | x | x | x | x | 5 |
|   |   | x | x | x |   |   |   | 4 |
|   | x |   | x |   | x |   |   | 3 |
| x |   |   | x |   |   | x |   | 2 |
|   |   |   | x |   |   |   | x | 1 |

 >>> - ***movimento della donna***
 >>>    - la *Donna* si può muovere in linee rette verticalmente, orizzontalmente o in diagonale per il numero di case non occupate che trova
 >>>    - combina dunque le mosse della torre e dell'alfiere
_______________
### 5) MUOVERE IL RE
 - permettere il movimento del pezzo *donna* sulla scacchiera
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando in notazione algebrica abbreviata degli scacchi in italiano  
  >>> - la mossa deve essere legale
  >>> - se si tenta una mossa non valida viene visualizzato un messaggio *mossa illegale* e l'applicazione rimane in attesa di una mossa valida  
  >>> - la mossa deve essere preceduta da una 'R' 
  >>> - il *Re* non può muoversi in case minacciate da pezzi avversari 
  >>> - il *Re* può catturare pezzi avversari
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   |   |   |   |   |   | 8 |
|   |   |   |   |   |   |   |   | 7 |
|   |   | x | x | x |   |   |   | 6 |
|   |   | x | ♚ | x |   |   |   | 5 |
|   |   | x | x | x |   |   |   | 4 |
|   |   |   |   |   |   |   |   | 3 |
|   |   |   |   |   |   |   |   | 2 |
|   |   |   |   |   |   |   |   | 1 |

 >>> - ***movimento del re***
 >>>    - il *Re* può muoversi di una casa alla volta in qualsiasi direzione (verticale, orizzontale o diagonale) a condizione che la casa di arrivo non sia minacciata da un pezzo avversario
 >>>    - in congiunzione con la *Torre*, può eseguire la mossa dell'arrocco
_______________
### 6) EFFETTUARE UN ARROCCO CORTO
  - permettere l'esecuzione di un arrocco corto
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando '0-0' in notazione algebrica abbreviata degli scacchi
  >>> - il giocatore non deve aver mai mosso il *Re*
  >>> - il giocatore non deve aver mai mosso la *Torre* coinvolta nell'arrocco corto (deve quindi essere in un angolo di destra della scacchiera) 
  >>> - non ci devono essere pezzi tra il *Re* e la *Torre* coinvolta, né amici né avversari 
  >>> - il *Re* non deve essere minacciato 
  >>> - il *Re*, durante il movimento dell'arrocco, non deve attraversare caselle in cui si troverebbe sotto *scacco* 
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   |   |   |   |   |   | 3 |
|   |   |   |   |   |   |   |   | 2 |
|   |   |   |   | ♚ |   |   | ♜ | 1 |

| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   |   |   |   |   |   | 3 |
|   |   |   |   |   |   |   |   | 2 |
|   |   |   |   |   | ♜ | ♚ |   | 1 |

 >>> - ***definizione di arrocco corto***
 >>>    - l'arrocco è effettuato sull'ala di *Re* (sulla destra della scacchiera)
 >>>    - [***per il Bianco***] il *Re* da **e1** muove in **g1**, la *Torre* da **h1** muove in **f1**
 >>>    - [***per il Nero***] il *Re* da **e8** muove in **g8**, la *Torre* da **h8** muove in **f8**
_______________
### 7) EFFETTUARE UN ARROCCO LUNGO
  - permettere l'esecuzione di un arrocco lungo
  > #### CRITERI DI ACCETTAZIONE
>> scrivendo il comando '0-0-0' in notazione algebrica abbreviata degli scacchi
  >>> - il giocatore non deve aver mai mosso il *Re*
  >>> - il giocatore non deve aver mai mosso la *Torre* coinvolta nell'arrocco lungo (deve quindi essere in un angolo di sinistra della scacchiera) 
  >>> - non ci devono essere pezzi tra il *Re* e la *Torre* coinvolta, né amici né avversari 
  >>> - il *Re* non deve essere minacciato 
  >>> - il *Re*, durante il movimento dell'arrocco, non deve attraversare caselle in cui si troverebbe sotto *scacco* 
  
| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   |   |   |   |   |   | 3 |
|   |   |   |   |   |   |   |   | 2 |
| ♜ |   |   |   | ♚ |   |   |   | 1 |

| a | b | c | d | e | f | g | h |   |
|---|---|---|---|---|---|---|---|---|
|   |   |   |   |   |   |   |   | 3 |
|   |   |   |   |   |   |   |   | 2 |
|   |   | ♚ | ♜ |   |   |   |   | 1 |

 >>> - ***definizione di arrocco lungo***
 >>>    - l'arrocco è effettuato sull'ala di *Donna* (sulla sinistra della scacchiera)
 >>>    - [***per il Bianco***] il *Re* da **e1** muove in **c1**, la *Torre* da **a1** muove in **d1**
 >>>    - [***per il Nero***] il *Re* da **e8** muove in **c8**, la *Torre* da **a8** muove in **d8**
_______________
