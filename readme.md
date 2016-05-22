This is Mastermind REST API. If you don´t know how to play, check <a href="https://en.wikipedia.org/wiki/Mastermind_(board_game)#Gameplay_and_rules" rel="nofollow">Mastermind on Wikipedia</a>

The specification is based on http://careers.axiomzen.co/challenge.

#Future releases
Implement unit tests;
Implement multiplayer feature;
Change in-memory to a database;


#Starting
mvn spring-boot:run


#Playing
To play just use the resources:
Endpoint=http://localhost:8080/mastermind

/new_game
</br>
Post data {
*	"user"
}


/guess
</br>
Post data {
*	"gameKey",
	"code"
}

* These fields are required"# VanHackathonAxiomZen" 
