# Documentation
## List of Contents
1. Basic Overview
2. Modules

# Modules

## User Management

- As a user of this application, I want to be able to
    - use it as a guest with all the limitations that implies
    - to register
    - to log in if I'm already registered
    
## Tournament Core Functionality

- As a tournament organizer, I want to 
    - be able to create a tournament
    - have the tournament be of matching type:
        - Swiss
        - Single Elim
        - Double Elim
        - Round Robin????? (pointless?)
        - for Single Elim and Double Elim, I want to be able to determine whether the brackets will be generated
        at the start, or the pairings between winners will be random after each round 
        (eg. classic bracket vs. winners play random winners) - let's call it the "static bracket" option,
        where static means the bracket is pre-generated and anyone can see one of 2 teams they will meet 
        in the next round, provided they win the current one (and potentially determine which half of the bracket 
        they could meet in the finals)

- As a participant, I want to:
    - be able to join a tournament using some unique identifier
        (code, link, ...)
    - be able to enter my own round results and view my standings
        after individual rounds
    

## Web Functionality

TODO