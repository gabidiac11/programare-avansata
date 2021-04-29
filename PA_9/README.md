
# Lab 9
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

Database resource: `PA_9\src\main\resources\pa_database.sql.gz`

Config file (jpa/jdbc): `PA_9\src\main\resources\config.json`

### Compusory

#### - Create a persistence unit (use EclipseLink or Hibernate or other JPA implementation).
#### - Verify the presence of the persistence.xml file in your project. Make sure that the driver for EclipseLink or Hibernate was added to to your project classpath (or add it yourself).
#### - Define the entity classes for your model (at least one) and put them in a dedicated package. You may use the IDE support in order to generate entity classes from database tables.
#### - Create a singleton responsible with the management of an EntityManagerFactory object.
#### - Define repository clases for your entities (at least one). They must contain the following methods:
##### -> create - receives an entity and saves it into the database;
##### -> findById - returns an entity based on its primary key;
##### -> findByName - returns a list of entities that match a given name pattern. Use a named query in order to implement this method.
#### - Test your application.

   An exemple with multiple methods is used in `pa.lab9.compulsory.Main`. The JPA part of the project is split in 3 parts: 
   1. Entities - auto-generated using Intellij IDE, with @ManyToMany adnotation for Movie and Genre entities + custom queries for the findById and findByName features
   2. Factory - FactoryManager, singleton that holds the hibernate session (and FactoryJba intended to be used for Optional part)
   3. Repository - made up by a class for each entity, implements create, findById, findByName features. Each repository is derived from a abstract class that uses generics (for entities)

   Exemple of an output resulting from a movie selection by id or by name:
   
````
Apr 25, 2021 6:21:27 PM pa.lab9.compulsory.Main testSelectMovieById
INFO: MovieEntity{title='LInferno', releaseDate='1911-03-06', duration=68, score=68, movieId='tt0002130', genreEntities=[Adventure, Drama, Fantasy]}
Apr 25, 2021 6:21:27 PM pa.lab9.compulsory.Main testSelectMovieById
INFO: MovieEntity{title='The Bargain', releaseDate='1914-12-03', duration=70, score=70, movieId='tt0003657', genreEntities=[Western]}
 ````
 
 ### Optional (2p)

#### - Add support for charts. A chart has a name, a creation date and an ordered list of movies.
   I added 2 extra queries for the movie entities in which I fetch 100 records and sort them by rating or by release date (as is requested). The output consist of a html file with the list:
   
  [![N|Solid](https://github.com/gabidiac11/programare-avansata/blob/main/PA_9/optional-chart-output.png)](https://github.com/gabidiac11/programare-avansata/blob/main/PA_9/optional-chart-output.png)
  
#### - Create a generic AbstractRepository using generics in order to simplify the creation of the repository classes. You may take a look at the CrudRepository interface from Spring Framework.
#### - Implement both the JDBC and JPA implementations and use an AbstractFactory in order to create the DAO objects (the repositories).
#### - The application will use JDBC or JPA depending on a parameter given in an initialization file. (At least for one entity!)
   I've created a Repository<T> interface responsable with basic functionalities described in the compusory module above (find by name or id, create), for each setting (jpa & jdbc). In the same manner I've also created a Factory interface responsable with with generating a repository for movie and one for the genres based on a configuration file (Config file jpa/jdbc is at `PA_9\src\main\resources\config.json`). The configuration file is json parsed with one property that holds the value of a setting ("jpa", "jdbc", values found in the `pa.lab9.cinema.factory.ConnectionType` enum). A certain class is reponsable with parsing the file and returning the factory of the jpa or the factory class of the jbdc.
   The optional package has 2 distinct parts: jpa and jdbc packages. Jdbc has Dao classes from the previous lab and uses raw sql. Jdbc also have its own implementation of repository factory, that helps the abstract factory to generate the same functionality based on the configuration file. Jdbc and Jpa shares the same entities as models for each table (which are placed in jpa package (/entities) as it's more closed to jpa and contains its adnotations).
   
   A Main class is provided with example for this part in the optional package.
   
   
 ### Bonus (2p) 

#### -We say that two movies are related if they have the same director (for example). Each day you want to see exactly two movies and you want to create the longest possible #### -playlist that satisfies the following constraints:
each day you will watch two related movies;
any two movies from different days cannot be related;
#### -Implement an efficient algorithm (for a bonus+) or use one from a third-party library, like JGraphT.
#### -Test your algorithm for large subsets of movies from your database and describe the runtime performance in a suggestive manner.
   In `pa.lab9.bonus.MoviePlaylist`:
````
/**
 * To implement this functionality I used org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching to create a pair of movies
 *
 * The process follows these steps:
 * 1. generate a graph representing each connection movies have with each other (share at least one director)
 * 2. solve the maximum cardinal matching and obtain a graph with edges that are not adjacent with each other (meaning they don't share the same director)
 * 3. filter out the result pairs with edges that still share the a director
 *
 * Output, a html file (pa.lab9.hml.output) containing:
 * - a side by side images of initial graph and the resulting graph
 * - a list of pairs of movies per day
 */
````

  [![N|Solid](https://github.com/gabidiac11/programare-avansata/blob/main/PA_9/bonus-output.PNG)](https://github.com/gabidiac11/programare-avansata/blob/main/PA_9/bonus-output.PNG)

