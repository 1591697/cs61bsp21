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
    public static void main(String[] args)  {
        if(args.length==0){
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validArgs(args, 1);
                try {
                    Repository.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
                // TODO: handle the `init` command
            case "add":
                Repository.checkinit();
                validArgs(args, 2);

                try {
                    Repository.add(args[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // TODO: handle the `add [filename]` command
                break;
            case "commit":
                Repository.checkinit();
                validArgs(args, 2);

                try {
                    Repository.commit(args[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            // TODO: FILL THE REST IN

            case "rm":
                Repository.checkinit();
                validArgs(args, 2);

                try {
                    Repository.rmFile(args[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "log":
                Repository.checkinit();
                validArgs(args, 1);

                try {
                    Repository.log();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "global-log":
                Repository.checkinit();
                validArgs(args, 1);

                Repository.global_log(COMMIT_DIR);
                break;
            case "find":
                Repository.checkinit();
                validArgs(args, 2);

                Repository.find(args[1]);
                break;
            case "status":
                Repository.checkinit();
                validArgs(args, 1);

                try {
                    Repository.status();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "checkout":
                Repository.checkinit();
                if (args.length == 3) {
                    if (!args[1].equals("--")) {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    if(args[1].equals("--")){

                        try {
                            Repository.checkout1(args[2]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
               else if (args.length == 4) {
                    if (!args[2].equals("--")) {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    if (args[2].equals("--")) {
                        try {
                            Repository.checkout2(args[1], args[3]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
               else if(args.length == 2){

                    try {
                        Repository.checkout3(args[1],Repository.GetBeCheckoutBids());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
               else{
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                break;
            case "branch":
                Repository.checkinit();
                validArgs(args, 2);


                try {
                    Repository.Creatbranch(args[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "rm-branch":
                Repository.checkinit();
                validArgs(args, 2);

                Repository.detelebranch(args[1]);
                break;
            case "reset":
                Repository.checkinit();
                validArgs(args, 2);

                try {
                    Repository.reset(args[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "merge":
                Repository.checkinit();
                validArgs(args, 2);

                try {
                    Repository.merge(args[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);

        }
    }
    private static void validArgs(String[] args, int num) {
        if (args.length != num) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}
