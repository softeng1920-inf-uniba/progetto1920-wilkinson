# USER STORIES
_______________
### 1) CREARE ELENCO COMANDI
  - visualizzare elenco dei comandi
  > #### CRITERI DI ACCETTAZIONE
  >> eseguendo il comando  
  >> [ ***>help*** ]  
  >> il risultato è una lista di comandi (uno per riga)
  >>> - **esempio comandi**:  
  >>> *>board*,  
  >>> *>import*,  
  >>> *>export*,  
  >>> *>quit*,  
  >>> *...*  
_______________
### 2) INIZIARE UNA NUOVA PARTITA
  - permettere all'utente di interfacciarsi ad una partita
  > #### CRITERI DI ACCETTAZIONE
  >> eseguendo il comando  
  >> [ ***>play*** ]  
  >> l'applicazione si predispone a ricevere la prima mossa di gioco
  >>> - il sistema è in grado di ricevere altri comandi in questo stato, ad esempio  
  >>> *>mostra scacchiera*,  
  >>> *>annullare*,  
  >>> *...*
_______________
### 3) CHIUDERE IL GIOCO
  - permettere di uscire dalla partita in corso
  > #### CRITERI DI ACCETTAZIONE
  >> eseguendo il comando  
  >> [ ***>quit*** ]  
  >> l'applicazione lascia il controllo al sistema operativo
  >>> - (il sistema non accetta più altri comandi e termina)
_______________
### 4) MOSTRARE LA SCACCHIERA
  - permettere la visualizzazione a schermo (da CLI) della scacchiera nello stato attuale
  > #### CRITERI DI ACCETTAZIONE
  >> eseguendo il comando  
  >> [ ***>board*** ]  
  >> l'applicazione stampa a video la posizione sulla scacchiera
  >>> - ogni pezzo è visualizzato con posizione aggiornata all'ultima mossa inserita  
  >>> - i pezzi sono visualizzati in formato [*Unicode*](https://en.wikipedia.org/wiki/Chess_symbols_in_Unicode)
_______________
### 5) MUOVERE UN PEDONE
  - permettere il movimento del pezzo *pedone* sulla scacchiera
  > #### CRITERI DI ACCETTAZIONE
  >> scrivendo il comando in notazione algebrica abbreviata degli scacchi in italiano  
  >>> - la mossa deve essere legale
  >>> - se si tenta una mossa non valida viene visualizzato un messaggio *mossa illegale* e l'applicazione rimane in attesa di una mossa valida  
  >>> - la mossa deve essere scritta come  
  >>> [**nome pezzo**] + [**casella di arrivo sulla scacchiera**] + [**simbolo speciale**]  
  >>> dove:    
  >>>      - [***nome pezzo***] è una lettera riportata nella tabella qui sotto (nel caso dei pedoni non viene utilizzata nessuna lettera)  
  >>>      - [***casella di arrivo sulla scacchiera***] è un combinazione lettera+numero (id colonna + id riga) [>*chessboard*](https://upload.wikimedia.org/wikipedia/commons/5/51/AlgebraicNotationOnChessboard.png)  
  >>>      - [***simbolo speciale***] è uno dei simbolo utilizzati per evidenziare il verificarsi di un evento particolare (*scacco*, *scacco matto*, *promozione*, *cattura*, *...*)  
  
| `nome pezzo` | Re  | Donna | Torre | Alfiere | Cavallo |
|--------------|-----|-------|-------|---------|---------|
| simbolo      | ♔ ♚ | ♕ ♛ | ♖ ♜ | ♗ ♝ | ♘ ♞ |
| lettera ITA  | R   | D     | T     | A       | C       |
| lettera ENG  | K   | Q     | R     | B       | N       |

| `eventi` | scacco | scacco doppio | cattura | arrocco corto | arrocco lungo | presa en passant | scacco matto |
|----------|--------|---------------|---------|---------------|---------------|------------------|:------------:|
| simbolo  | +      | ++            | x       | 0-0           | 0-0-0         | e.p.             | #            |

 >>> - ***movimento del pedone***
 >>>    - il pedone può muoversi solo di una casa in avanti, tolta la possibilità di avanzare di due case solo dalla posizione iniziale   
 >>>    - può catturare i pezzi avversari che si trovano in una delle due caselle oblique a lui adiacenti (a eccezione della presa [en passant](https://lh3.googleusercontent.com/proxy/HdFbE8mPf0x5pYtg7hqGuf2lYdxeZZWUVjgbkjy3ofzEqL_EHpY7zvgWleUN-oAH7ZcafKkFByJezfbaukukb97P0wPq7pWUZo0b0xoA2jBXSSWRv4vT6SBDRgf1UsJOzmRAKghF)): è quindi l'unico pezzo che mangia in modo diverso dal proprio normale movimento  
 >>>    - se un pedone riesce a raggiungere il lato opposto della scacchiera, il proprietario del pedone lo deve promuovere sostituendolo con un qualsiasi altro pezzo a sua scelta (purché dello stesso colore, e che non sia il re)
_______________
### 6) MOSTRARE MOSSE GIOCATE
  - permettere di visualizzare lo storico delle mosse giocate
  > #### CRITERI DI ACCETTAZIONE
  >> eseguendo il comando  
  >> [ ***>moves***]  
  >> l'applicazione mostra la storia delle mosse compiute fino a quel momento
  >>> - le mosse sono visualizzate in notazione algebrica abbreviata in italiano
_______________
### 7) VISUALIZZARE LE CATTURE
  - permettere di visualizzare a video i pezzi catturati nel corso della partita
  > #### CRITERI DI ACCETTAZIONE
  >> eseguendo il comando  
  >> [***>captures***]  
  >> l'applicazione mostra tutte le catture della partita in corso fino a qual momento
  >>> - mostrare le catture del Bianco e del Nero in caratteri Unicode
_______________
