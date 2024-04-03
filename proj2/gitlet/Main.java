package gitlet;
import java.io.IOException;
import static gitlet.Repository.*;


/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        if(args.length==0){
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validArgs(args, 1);
               Repository.init();
               break;
                // TODO: handle the `init` command
            case "add":
                validArgs(args, 2);

                    Repository.add(args[1]);

                // TODO: handle the `add [filename]` command
                break;
            case "commit":
                validArgs(args, 2);

               Repository.commit(args[1]);
                break;
            // TODO: FILL THE REST IN

            case "rm":
                validArgs(args, 2);

                Repository.rmFile(args[1]);
                break;
            case "log":
                validArgs(args, 1);

                Repository.log();
                break;
            case "global-log":
                validArgs(args, 1);

                Repository.global_log(COMMIT_DIR);
                break;
            case "find":
                validArgs(args, 2);

                Repository.find(args[1]);
                break;
            case "status":
                validArgs(args, 1);

                Repository.status();
                break;
            case "checkout":

                if (args.length == 3) {
                    if (!args[1].equals("--")) {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    if(args[1].equals("--")){

                        Repository.checkout1(args[2]);
                    }

                }
               else if (args.length == 4) {
                    if (!args[2].equals("--")) {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    if (args[2].equals("--")) {
                        Repository.checkout2(args[1], args[3]);
                    }
                }
               else if(args.length == 2){

                        Repository.checkout3(args[1],Repository.GetBeCheckoutBids());

                }
               else{
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                break;
            case "branch":
                validArgs(args, 2);


                Repository.Creatbranch(args[1]);
                break;
            case "rm-branch":
                validArgs(args, 2);

                Repository.detelebranch(args[1]);
                break;
            case "reset":
                validArgs(args, 2);

                Repository.reset(args[1]);
                break;
            case "merge":
                validArgs(args, 2);

                Repository.merge(args[1]);
                break;

        }
    }
    private static void validArgs(String[] args, int num) {
        if (args.length != num) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}
