
# Lab 4
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

This piece is aiming at offering a better understanding concerning the following aspects of the source code:

- problem-solving strategies that were used
- justification for choices made regarding object modeling 
- what exactly are the exercises that I assume are completed (~✔️), partially completed (⏰) or are not started (❌)

## Addressed exercises 
### Compulsory (1p) 

Requirements and their status:

 #### - Create an object-oriented model of the problem. You should have at least the following classes: Catalog and two item classes at your choice. Consider using an interface or an abstract class in order to describe the items in a catalog.~✔️
 I've created a abstract class `pa.lab5.multimedia.Media` that is extended by other classes, *Book* and *Song*. *Book* has a publication and *Song* a genre, and other than that they share the same properties using the their parent class (path, name, author, rating, year).
 The *Catalog* class is present into `pa.lab5.multimedia.Media.library.Catalog`.
 
 #### - Implement the following methods representing commands that will manage the content of the catalog:~✔️
  ##### add: adds a new entry into the catalog;
    The *Catalog* instance has a list of *Media* objects. `Catalog.add(Media media)` adds a new element to the list.
  ##### list: prints the content of the catalog on the screen;
    `Catalog.list()` converts a list of items to json then prints the result using `org.apache.logging.log4j`. 
    ````JSON
    12:28:20.548 [main] OFF   pa.lab5.multimedia.library.Catalog - 
        --------------------------------------LISTA----------------------------------------------
        {
          "list": [
            {
              "author": "Feodor Dostoevsky",
              "name": "Insermari din subterana",
              "path": "src\\main\\java\\pa\\lab5\\files\\books\\Feodor_Mihailovici_Dostoievski_Insemnari_Din_Subterana.pdf",
              "year": "1846",
              "rating": "1000000",
              "publication": "POLIROM"
            },
            {
              "author": "Lev Tolstoy",
              "name": "Invierea",
              "path": "src\\main\\java\\pa\\lab5\\files\\books\\Lev_Tolstoi_Invierea.pdf",
              "year": "1889",
              "rating": "1000000",
              "publication": "POLIROM"
            },
            {
              "author": "Marin Preda",
              "name": "Cel mai iubit dintre pamanteni",
              "path": "src\\main\\java\\pa\\lab5\\files\\books\\Preda_Marin_Cel_mai_iubit_dintre_pamanteni.pdf",
              "year": "1980",
              "rating": "1000000",
              "publication": "POLIROM"
            },
            {
              "author": "Liviu Rebreanu",
              "name": "Ion (John)",
              "path": "src\\main\\java\\pa\\lab5\\files\\books\\liviu.pdf",
              "year": "1920",
              "rating": "9",
              "publication": "Curierul literar"
            },
            {
              "author": "Alexandrov Ensemble",
              "name": "To serve Russia",
              "path": "src\\main\\java\\pa\\lab5\\files\\songs\\Russian_Federation_1991_Military_March_To_Serve_Russia.mp3",
              "year": "1945",
              "rating": "1000000",
              "genre": "Classical, folk tunes, hymns, operatic arias, popular music"
            },
            {
              "author": "Gica Petrescu",
              "name": "Du-ma acasa, mai stramvai!",
              "path": "src\\main\\java\\pa\\lab5\\files\\songs\\gica_petrescu.mp4",
              "year": "1970",
              "rating": "90",
              "genre": "Pre-89"
            }
          ]
      }
```
  #####  play: playback using the native operating system application (see the Desktop class);
  
  ##### save: saves the catalog to an external file (either as a text or binary, using object serialization);
  
  #####  load: loads the catalog from an external file.
