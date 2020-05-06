# INF1B Code Commentary (Assignment 2)

## Code Readability (Pair 35)

Generally, the code from Student B has a better code readability compared to that from Student A in the following aspects:



1) Method documentations

Every method written by Student B is well-documented with dedicated Javadoc tags.

An example is the static method isValidMove under FoxHoundUtils.java.

Student B describes the general function of this method and explain how to use this method by listing all the parameters, returned values and possible exceptions of this method. Student B also uses the standard Javadoc tags to identify the essential information of the method, This makes the method much more readable and allows these information to be displayed in the generated API documentation. Although Student A attempted to describe the step-by-step process of this method using inline comment, this is not a good documentation style because it is harder for user to know about the method at a glance. In addition, these inline comments will not appear in the generated API documentations.

To improve the documentations, Student A should adopt the Javadoc comments in every method (using Javadoc comment convention). 



2) Inline comments

Inline comments in the code should only be included when the code is difficult to understand, and commenting obvious statements will reduce the code readability because it catches unnecessary attentions from the readers. Under this aspect, Student B has done a better job compared to Student A.

The following portions of source code from Student A (FoxHoundUI.java) includes unnecessary inline comments:

```Java
//loops through numbers
for (int j = 1; j <= dimension; j++) {
  //loops through letters (columns)
 for (int i = 0; i < dimension; i++) {
 }
}
```



```Java
//if input is false then error
if (!input) {
  System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
}
```

These comments are not useful for developers who knows the basic Java syntax and they make the code more obscure to read.

In contrast, Student B only uses inline comments if it is hard to interpret the code at the first glance: (FoxHoundUI.java)

```Java
if (!validInput) {
  System.out.println("Please enter valid number.");
  input = -1; // reset input variable
}
stdin.nextLine(); // throw away the rest of the line
```



```Java
if (!validInput) {
  System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
  input = null; // reset input variable
}
```

Having less but essential inline comments not only make the code cleaner, but also catches necessary attentions from the developers to the core logic behind every method. Student A should therefore consider to remove all the unnecessary inline comments in the code.



3) Naming variables

Good names for variables can help users identify its purpose quickly without looking at any other comments. In this case, Student B is doing a better job compared to Student A.

A typical example for this is the displayBoard method and its associated helper functions under FoxHoundUI.java, which contains a large amount of loops in the code. (Note that Student B disintegrates the several loops into several helper methods, which are generateColChars, displayFirstLastRow and displayRows)

Most of the time, Student B uses meaningful names for every variables in the method, such as rowNum, spacing, divider, making them easier to be recognised and understood. On the other hand, Student A uses meaningless names such as i,j and k (apparently auto-generated variables by IDE). 

In addition, there are some inconsistency in naming the variables for Student A. By convention, any variable names should be in camel case. In isValidMove method in FoxHoundUtil.java, names such as "origincol", "Destcol" and "Destrow" do not follow this convention. In contrast, variable names made by Student B are consistent all the time, making the code easier for maintenance. 

Although users of the code may only use the methods without actually inspecting the source code, this makes the debugging process much more difficult, especially if the one who debugs the code is not the one who developed that code. Proper naming for variables is more important if the method contains lots of logic, conditions and loops (like displayBoard method)

Student A should consider to rename these variables (e.g. i,j and k) to some meaningful names.



## Code structure (Pair 41)

In this pair, Student B has a better code structure compared to Student A.

Breaking functions into several helper functions is a good coding practice because it allows us to approach a complex task into many simpler tasks. It allows us to reuse these helper functions across multiple programs without repeating similar codes and update them if needed without affecting the caller code. 

A typical example in this pair is the inValidMove method in FoxHoundUtil.java. 

Student B breaks this complex functions into several simple functions. Each function only perform a single check. For example, the requireDimInRange method only checks whether the dimension falls within the the specified range and the isEmpty method only checks if the destination coordinate is occupied by another entity. The uses of several simple helper functions reduces the isValidMove function into a reasonable and readable size (less than 20 short lines), which makes this method significantly easier to read and maintain. In addition. these simple helpers functions also can be reused by other methods. For example, the method requireDimInRange is not only used in isValidMove but also called by other functions across multiple programs such as initialisePositions, isHoundWin and displayBoard methods. This type of reusable methods allows developers to maintain the code easily by updating them if needed without changing the caller code (Information hiding) and also reduce the repeated code across multiple programs.

Also, in isValidMove method, Student B groups all the parameter-checking method into a single Boolean array and check them together using allTrue method:

``` Java
boolean[] testCases = {
	inBounds(dim,origin),
	inBounds(dim,destination), 
	isEmpty(players,destination), 
	correctFigure(players,figure,origin), 
	validMoveForFigure(figure,origin,destination)
};
return allTrue(testCases);
```

This measure makes uses of multiple helper functions with the same return type and it is definitely a good practice. Because if at some points developers want to add additional constraints to isValidMove method, they can simply create a new Boolean method and insert it directly to the testCases array. 

Similar approach was also taken in displayBoard method in FoxHoundUI.java.

On the other hand, Student A do not extract the checker codes from isValidMove method into several helper functions, making isValidMove method very long to read and maintain (about 85 lines). Although Student A attempts to breaks down this method to different parts by inline comments, it does not significantly improve the readability of the code. 

Also some portions of isValidMove are repeated across different methods. For example, the following dimension check code has repeatedly appeared in isValidMove, initialisePositions and other methods which perform the dimension check. 

``` Java
if(dim < MIN_DIM || dim > MAX_DIM)
{throw new IllegalArgumentException("Invalid Dimension supplied.");}
```

This is a very bad practice. Because if developers want to add conditions in dimension check or simply change the message appeared for IllegalArgumentException, they need to update the changes to every occurrences of dimension check code, which is inefficient and sometimes less effective. To improve this, it is better to extract all the checker codes  into multiple simple methods like what Student A did and to ensure that all the methods are kept in a reasonable length. If any method or function appears to be too long to be interpreted, it is better to further divide the codes into several steps and extract them into simpler methods. 



## Usage of the Java Language (Pair 38)

Generally, Student B has made a better use of Java language features compared to Student A in the following aspects:



1) String concatenation

In the method displayBoard in FoxHoundUI.java, students A and B take different approaches in displaying board. Student A prints every component individually using System.out.print method with String objects, while Student B uses StringBuilder object to store all the individual String objects needed to display the board and print this StringBuilder object only once at the end of the method. 

In my opinions, using StringBuilder objects like what Student B does is a better approach compared to printing individual String objects. First of all, because System.out.print method internally involves creation of a new PrintStream object, calling it multiple times will create multiple PrintStream objects and occupy more memory. StringBuilder is optimised in dealing with String concatenations and no matter how many String objects are added to a StringBuilder object, it do not create extra objects and occupy more stack memory. In addition, since Student B only call System.out.print method once at the end of the method, only one PrintStream object is created. The method from Student B will run faster and consume less memory at the end, which reduces the risk of stack overflow. 

Performance impacts aside, using StringBuilder objects also makes the code looks cleaner. Printing segments of board across the entire method makes the code very confusing to read because it is preferred by convention to print a complete board at one call rather than calling the print method multiple times for multiple segments of a complete board. 

Therefore, Student A may consider to use StringBuilder to concatenate String objects instead of printing String objects individually.



2) Error handling using try/catch

In the method saveGame and loadGame in FoxHoundIO.java, both students A and B uses try/catch block to handle different exceptions encountered in the caller code. However, only Student B uses try/catch block as intended, which is to respond to exceptions in a meaningful way.

For example, under method saveGame in FoxHoundIO.java, when IOException is caught, Student A prints the entire stack trace of exceptions using printStackTrace method while Student B return false to indicate the saving game is not successful and let the caller code decide any further actions.

Student A:

``` Java
try {
    //Code omitted
}
catch (IOException e) {
    e.printStackTrace();
}
```

Student B:

``` java
try {
    //Code omitted 
}
catch (IOException x) {
    return false;
}
```

Although methods from both students return the same result after passing an invalid path, Student B responds to this exception in a better way, in my opinions. Because aside from returning false to the caller code, the method from Student A also produces a long and meaningless (for user) error trace directly to the user interface (see Errors session under Run Basic Tests from Student A).

This not only causes confusions to the users but also exposes the security weaknesses to potential hackers (considering it is a file-manipulation method). To improve upon this, it is suggested to either return false to indicate an error and let the caller code to decide any further actions or print a user-friendly error message to the console before returning false. 

