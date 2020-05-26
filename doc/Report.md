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
<img width="700" alt="ModelDomainv2-ids1920.png" src="./drawings/ModelDomainv2-ids1920.png">
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
  <img width="600" src="drawings/diagr_package.png">
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

* ***Diagramma delle classi*** per le User Stories che interessano i **comandi speciali** da inviare all'applicazione

<center>
	<img width="500" alt="diagr_class_commands.png" src="./drawings/diagr_class_commands.png">
</center>

<br> <br>

... ***Diagramma di sequenza*** relativo alla User Story del **comando 'play'** ...

<center>
	<img width="900" alt="diagr_seq_commands.png" src="./drawings/diagr_seq_commands.png">
</center>

<br> <br>

... ***Diagramma delle classi*** per le User Stories che interessano il **movimento di un pezzo** sulla scacchiera ...

<center>
	<img width="900" alt="diagr_class_moves.png" src="./drawings/diagr_class_moves.png">
</center>

<br> <br>

... ***Diagramma di sequenza*** relativo alle User Stories del **movimento di un pezzo** sulla scacchiera ...

<center>
	<img width="900" alt="diagr_seq_moves.png" src="./drawings/diagr_seq_moves.png">
</center>

<br> <br>

... ***Diagramma delle classi*** per le User Stories che interessano l'**arrocco** corto e lungo ...

<center>
	<img width="650" alt="diagr_class_castle.png" src="./drawings/diagr_class_castle.png">
</center>

<br> <br>

... ***Diagramma di sequenza*** relativo alle User Stories dell'**arrocco** corto e lungo.

<center>
	<img width="900" alt="diagr_seq_castle.png" src="./drawings/diagr_seq_castle.png">
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

<br><br>
[Torna all'indice...](#Indice)

# Manuale utente


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
