package capers;

import java.io.File;

import static capers.Dog.fromFile;
import static capers.Utils.*;
import static capers.Dog.DOG_FOLDER;
/** A repository for Capers 
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD,".capers"); // TODO Hint: look at the `join`
                                            //      function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        // TODO
    if(!CAPERS_FOLDER.exists()){
        boolean s=CAPERS_FOLDER.mkdir();//既有创建一个文件夹，并判断它是否成功
        if(!s){
             throw new java.lang.Error("Cannot create directory");//抛出一个新定义的异常。
        }
    }


    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        File story = Utils.join(CAPERS_FOLDER, "story.txt");
        if(story.exists()){
        String pres=Utils.readContentsAsString(story);
        text = pres +'\n' +text;
        }

            Utils.writeContents(story, text);
            System.out.println(text);


    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog=new Dog(name,breed,age);
        dog.saveDog();
        System.out.println(dog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
       Dog d=fromFile(name);
        if (d == null) {
            throw new java.lang.Error("Dog is not exist");
        }
       d.haveBirthday();
       d.saveDog();


    }
}
