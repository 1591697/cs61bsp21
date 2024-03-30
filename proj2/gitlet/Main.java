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
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
               Repository.init();
               break;
                // TODO: handle the `init` command
            case "add":
                if(args.length==2){
                    Repository.add(args[1]);
                }
                // TODO: handle the `add [filename]` command

                break;
            case "commit":
               Repository.commit(args[1]);
            // TODO: FILL THE REST IN

            case "rm":
                Repository.rmFile(args[1]);

            case "log":
                Repository.log();
            case "global-log":
                Repository.global_log(COMMIT_DIR);
            case "find":
                Repository.find(args[1]);
            case "status":
                Repository.status();
            case "checkout":
                if (args.length == 3) {
                    if(args[1].equals("--")){

                        Repository.checkout1(args[2]);
                    }
                    else {
                        Repository.checkout3(args[1],Repository.GetBeCheckoutBids());
                    }
                }
                if (args.length == 4) {
                    if (args[2].equals("--")) {
                        Repository.checkout2(args[1], args[3]);
                    }
                }
            case "branch":
                Repository.Creatbranch(args[1]);
            case "rm-branch":
                Repository.detelebranch(args[1]);
            case "reset":
                Repository.reset(args[1]);
            case "merge":
                Repository.merge(args[1]);

        }
    }
}
