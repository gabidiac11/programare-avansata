
# Lab 9
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

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
 ```
