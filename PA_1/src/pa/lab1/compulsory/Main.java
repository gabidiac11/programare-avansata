package pa.lab1.compulsory;

public class Main {
    public static void main(String[] args) {

        /*
        Compulsory 1. Display on the screen the message "Hello World!". Run the application. If it works, go to step 2 :)
        * */
        System.out.println("Hello world!");

        /*
        Compulsory 2. Define an array of strings languages, containing {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"}
        **/
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        /*
        Compulsory 3. Generate a random integer n: int n = (int) (Math.random() * 1_000_000);
        **/
        int n = (int) (Math.random() * 1000000);
        System.out.printf("Random number: %d \n", n);

        /*
        Compulsory 4.Compute the result obtained after performing the following calculations:
          multiply n by 3;
          add the binary number 10101 to the result;
          add the hexadecimal number FF to the result;
          multiply the result by 6;
        **/
        n = n * 3 + Integer.parseInt("10101",2) + Integer.parseInt("FF",16);
        n *= 6;
        System.out.printf("Random number after operations: %d \n",
                n);

        /*
         Compulsory 5.Compute the sum of the digits in the result obtained in the previous step.
         This is the new result. While the new result has more than one digit, continue to sum the digits of the result.
        **/
        String[] digits = Integer.toString(n).split("");
        int sum = 0;
        for (String aDigit: digits) {
            sum += Integer.parseInt(aDigit);
            if(sum / 10 == 0) {
                break;
            }
        }
        System.out.printf("Sum of digits for the above number: %d \n", sum);

        /*
         Compulsory 6.Compute the sum of the digits in the result obtained in the previous step. This is the new result. While the new result has more than one digit, continue to sum the digits of the result.
         Display on the screen the message: "Willy-nilly, this semester I will learn " + .
        **/
        int result = sum;
        System.out.printf("Willy-nilly, this semester I will learn %s \n", languages[result]);
    }
}
