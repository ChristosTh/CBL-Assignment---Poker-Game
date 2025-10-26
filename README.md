# Poker Game
This project is a **Poker Game** built in Java as part of our CBL Project in the course of Programming.

# Project Overview
- Our poker game was developed to explore object-oriented programming and game logic.
- Authors: Christos Theofanous

---

# Table of Contents
1. [Features](#features)
2. [What we Learned](#what-we-learned)
3. [Resources](#resources)
4. [Project Structure](#project-structure)
6. [How to play](#how-to-play)
7. [Contributors](#contributors)

---

## Features
- Poker gameplay (betting, folding, hand evaluation, etc.)
- Bot opponent with decision-making based on game theory and randomization
- Interactive UI for setup and gameplay
- Player and bot wallet with a betting system

---

## What we Learned
1. We learned how to use **Git** for version control, collaboration and working at the same time.
2. We implemented the bot behaviour using researched formulas and the Math.random method to simulate decision making for more realistic approach.

---

## Resources
1. For Git:
   - https://www.w3schools.com/git/
   - https://stackoverflow.com
   - https://git-scm.com/

2. For game theory for bot decision:
   - https://upswingpoker.com/pot-odds-step-by-step/

3. For UI:
   - https://code.google.com/archive/p/vector-playing-cards/downloads
  
---

## Project Structure

### Core Game Logic
- `Bot.java` – Extends `Player.java`, handles bot decision-making logic  
- `Player.java` – Manages player actions and properties  
- `Card.java` – Represents a single playing card  
- `Deck.java` – Manages the deck of cards (shuffle, draw, etc.)  
- `HandEvaluation.java` – Evaluates player hands to determine winners  
- `Poker.java` – Main class handling the overall game flow  
- `Pot.java` – Manages bets and total pot  
- `Round.java` – Controls the flow of each round  
- `Wallet.java` – Manages player funds  

### User Interface
- `GameSetup.java` – UI for starting and configuring the game  
- `PokerMat.java` – Main game screen (table visualization)  
- `StartMenu.java` – Entry screen for the game

---

## How to play
1. Clone the repo in a directory (git clone https://github.com/LeoFischer-12/CBL-Assignment---Poker-Game)
2. Get in the directory and open the folder in a programming environment or a text editor with Java SDK (e.g. VS Code)
3. Run the Poker.java program
4. Please read the rules before setting up the game!

---

## Contributors
- Christos Theofanous
- Leo Fischer
