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
The main bulk of this part is placed in `pa.lab4.didactic`, with exemple in the  `pa.lab4.compulsory.Main`. 

Requirements and their status:

 #### - Create an object-oriented model of the problem. You should have at least the following classes: Student, School, and the main class..~✔️
 #### - Create all the objects in the example using streams.~✔️
  I used *stream* to create the students and school objects and their names (.
 
 ````java
        //pa.lab4.compulsory.Main.generateStudentList()
        ------------------------------------------------------------
        List<Student> students = new LinkedList<>();
        
        Stream.of(0, 1, 2, 3).forEach(i -> {
            students.add(new Student(String.format("S%d", i), "", String.format("serial_number_%d", i)));
        });
 ````

  I also used on another occasion in order to generate a string having the format "S0: (H0, H1, H2)":
 ````java
    //pa.lab4.stablematching.PreferencePrinter.preferenceToString(Map<T, Set<M>> preferences, String title)
    /* ------------------------------------------------------------ */
        
    public String preferenceToString(Map<T, Set<M>> preferences, String title) {
        String output = "\n-----" + title + "----\n";

        for(Map.Entry<T, Set<M>> entry : preferences.entrySet()) {
            T student = entry.getKey();

            output = output + String.format("%s:(", student.getName());
            output += entry.getValue()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.joining(","));

            output += ")\n";
        }

        output = output + "-----" + title + "----end\n";

        return output;
    }
 ````
 
 
 #### - Create a list of students, using LinkedList implementation. Sort the students, using a comparator.~✔️
  In `pa.lab4.compulsory.Main.generateStudentList()`, the list is generated and sorted by name:
  
 ````java
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
 ````
 
  #### - Create a set of schools, using a TreeSet implementation. Make sure that School objects are comparable.~✔️
  In `pa.lab4.compulsory.Main.generateSchoolList()`, the list is generated. The `School` class implements the required interface (`Comparable<T>`), where is ordered by the registration number, a string field in the class that looks like the name (a word with a number concatenated):
  
 ````java
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    public class School implements Comparable<School> {
        private String name;
        private final String registrationNumber;

        @Override
        public int compareTo(School o) {
            return this.getRegistrationNumber().compareTo(o.getRegistrationNumber());
        }
    }
 ````
 
 #### - Create two maps (having different implementations) describing the students and the school preferences and print them on the screen.~✔️
  This is done using a map from student to a LinkedHashSet of schools by which order the preference is given. The same mapping is done in reverse for the schools. These 2 variables are generated in compulsory main `generateStudentPreferences(List<Student> students, Set<School> schools)` and `generateSchoolPreferences(List<Student> students, Set<School> schools)`. The text printed is generated in `pa.lab4.stablematching.PreferencePrinter.preferenceToString(Map<T, Set<M>> preferences, String title)`. `PreferencePrinter` uses a generic type to avoid using 2 different functions that do the same thing.
  
  ````
  
     ------------------------------------------------------------STUDENTS-PREFERENCES----
     S0:(H0,H1,H2)
     S3:(H0,H2)
     S1:(H0,H1,H2)
     S2:(H0,H1)
     ------------------------------------------------------------STUDENTS-PREFERENCES----end


     ------------------------------------------------------------SCHOOLS-PREFERENCES----
     H1:(S0,S2,S1)
     H2:(S0,S1,S3)
     H0:(S3,S0,S1,S2)
     ------------------------------------------------------------SCHOOLS-PREFERENCES----end
  ````
  
  
  ### Optional (2p) 
For this part, besides changes to the `didactic` package, I created a new package (`pa.lab4.stablematching`) with generic type classes that specialize in solving the [*Stable Marriage*](https://www.youtube.com/watch?v=ZeIBwYK0DEQ) problem. An example that illustrates the requirements from optional is done in `pa.lab4.optional.Main`. 

Requirements and their status:

 #### - Create a class that describes the problem and one that describes a solution (a matching) to this problem.~✔️
  The matching problem is solved using the Gale Shapley algorithm. I adapted this algorithm to my implementation: 
  
````
  Initialize all men and women to free
  while there exists a free man m who still has a woman w to propose to 
  {
      w = m's highest-ranked such woman to whom he has not yet proposed
      if w is free
         (m, w) become engaged
      else some pair (m', w) already exists
         if w prefers m to m'
            (m, w) become engaged
             m' becomes free
         else
            (m', w) remain engaged    
  }
  
  https://www.geeksforgeeks.org/stable-marriage-problem/

````
  The difference between the above implementation and what we need is that a school can be 'engaged' to multiple students. For this, I created a generic class *Woman<W, M>* that holds the School (W) and Student (M). In this class, the preferences are stored as well as the list of 'fiances'. 
 
  The most important method of this class is `Pair<M, Boolean> manProposes(M newMan)`. This method adds a man to a list of fiances if the respective man is preferred more than at least one of the current list of fiances or the list of fiances is empty. 
  
  The class that resolve the problem is `pa.lab4.stablematching.StableMarriage`, in `Map<M, W> generateStableMatching()`. This method creates a map from W to a Woman<W, M> from the list of W and each W object's preferences for a list of M objects, initialized in the constructor. This method implements the above-described algorithm using generics and adapted to work with polygamy.
 
 #### - Create all the objects in the example using streams.~✔️
  I used streams on multiple occasions, where I found it helpful. 
  For example, this is the way I printed the results from stable matching, in `pa.lab4.stablematching.PreferencePrinter`:
  
````java
    /**
     * ------------------------------------------------------------Matches-------------------
     * [(Christian Zemlak:The Alabama College),(Na Davis:South Colorado College),(Fumiko Hodkiewicz:The Alabama College),(Rosenda Wilderman:Western Mississippi College)]
     * ------------------------------------------------------------Matches-------------------
     * @param matches
     * @return - string representing matches
     */
    public String matchesToString(Map<T, M> matches) {
       return String.format("\n------------------------------------------------------------Matches-------------------\n[%s]\n------------------------------------------------------------Matches-------------------\n",
               matches
                .entrySet()
                .stream()
                .map((Map.Entry<T, M> match) -> String.format("(%s:%s)", match.getKey().getName(), match.getValue().getName()))
                .collect(Collectors.joining(","))
       );
    }
````


  I also used them to convert a set to a list (`pa.lab4.didactic.SchoolAllocation.allocateStudents()`):
````java
        /*
         * convert these to lists
         */
        Map<Student, List<School>> manPreferences = new HashMap<>();
        this.studentPreferences.entrySet().stream().forEach(entry -> {
            manPreferences.put(entry.getKey(), entry.getValue().stream().collect(Collectors.toList()));
        });
````
  I used *stream* on regular occasions, as are very useful.
  
 #### - Use a third-party library to generate random fake names for students and schools.~✔️
 For this one I search on google on how to add external libraries and found a list of [jar files](https://jar-download.com/artifacts/com.github.javafaker) that helped me install the *JavaFaker* library. I installed all of them by adding them into *Project Structure -> Project Settings -> Modules -> + -> JARS or Directories*.
  I generated first name and last name for students (`faker.name()`) and school names (`faker.university()`) in `pa.lab4.optional.Main`:
  
````java
     public static Set<School> generateSchoolList() {
        Set<School> schools = new TreeSet<>();

        Faker faker = new Faker();

        Stream.of(0, 1, 2).forEach(i -> {
            schools.add(new School(faker.university().name(), String.format("serial_school_number_%d", i)));
        });

        return schools;
    }
    
    
    public static List<Student> generateStudentList() {
       /* create a list of students using a linked list */
       List<Student> students = new LinkedList<>();

       Faker faker = new Faker();

       Stream.of(0, 1, 2, 3).forEach(i -> {
           students.add(new Student(faker.name().firstName(), faker.name().lastName(), String.format("serial_number_%d", i)));
       });
       [....]
    }
````


#### - Implement an algorithm for creating a matching, considering that each student has a score obtained at the evaluation exam and the schools rank students based on this score.~✔️
   I added a new data member to `pa.lab4.didactic.School`, *grades*, a map from Student to a number. To not break the prior implementation, I added a second constructor without the grades. Another thing I did in this matter is a second constructor for `pa.lab4.didactic.SchoolAllocation`, one that doesn't receive the school preference, but it generates based on the grades of each student that applied or had a grade greater than 0. The following method does that by filtering and sorting the students:
  
````java
     /**
     * @param students
     * @param schools
     * @return - a map from a school to an ordinated set of students (the preference is given by the order in which are pushed)
     */
    private Map<School, Set<Student>> schoolPreferenceBasedOnGrades(List<Student> students, Set<School> schools) {
        Map<School, Set<Student>> schoolPreferences = new HashMap<>();
        Object[] schoolsArray = schools.toArray();

        for(int  i = 0; i < schoolsArray.length; i++) {
            School school = (School) schoolsArray[i];

            /*
             * preferences are given by the order of the grades
             */
            Set<Student> preferredStudents = new LinkedHashSet<>();

            /* iterate trough a filtered and sorted list of students using school grades */
            this.students.stream().filter(student -> {
                return school.getStudentGrade(student) != null;
            }).sorted((s1, s2) -> {
                return Integer.compare(school.getStudentGrade(s2), school.getStudentGrade(s1));
            }).forEach(student -> {
                preferredStudents.add(student);
            });

            schoolPreferences.put(school, preferredStudents);
        }
        return schoolPreferences;
    }
````
 #### - Test your algorithm.~✔️
