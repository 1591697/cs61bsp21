package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/**
 * Represents a gitlet commit object.
 * TODO: It's a good idea to give a description here of what else this Class
 * does at a high level.
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    public String message;
    private String timestamp;
    public ArrayList<String> parent=new ArrayList<>();
    private Date date;
    private String Uid;
    public List<String> Bids;
    public String RemoveFile;


    public  Commit(String message,String parentn,List<String> bids,String rids) throws IOException {
        this.message = message;
        this.Bids = GetBidsFileLink(bids,rids);
        this.Uid = GetUSHA1();
        this.parent.add(parentn);
        this.date = new Date();
        this.timestamp = DateToTimestamp(date);
       this.RemoveFile=removefile();


    }

    public  Commit() throws IOException {//初始化的两种不同方式,这个没有文件
        this.message = "initial commit";
        this.date = new Date();
        this.timestamp = DateToTimestamp(date);
       this.parent=new ArrayList<>();//不是很理解
     //   this.parent=null;
        this.Bids = new ArrayList<>();
        this.Uid = GetUSHA1();
    }
        public String removefile() throws IOException {
            File file=new File(REMOVW_FILE.getAbsolutePath());
            String con= Utils.readContentsAsString(file);
            String[] s=con.split(" ");
            for (int i=0;i<s.length;i++){
                s[i]=s[i].replace(" ","");
            }

                Blob b=new Blob(file);
           b.CreatBlobFolder();
           return b.GetId();

        }
    public String DateToTimestamp(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(date);
    }

    //得到加密哈希码；
    public String GetUSHA1() {

        Uid = sha1(Getindexstring(),message);
        return Uid;
    }

    //得到指向Bids的连接，通过index就好了??add-file文件不要忘记啊
    public List<String> GetBidsFileLink(List<String> bids,String rids) throws IOException {
        HashSet<String> indexblobs;
        ArrayList<String> indexs = new ArrayList<>();
        Stage stage = new Stage(bids,rids);
        indexblobs = stage.GetIndexs();
        for (String id : indexblobs) {
            indexs.add(id);
        }
        return indexs;
    }


    //得到blob文件链表
//    public LinkedList<Blob> GetBlobLink() throws IOException {
//        List<String> WFileName = Utils.plainFilenamesIn(CWD);
//        LinkedList<Blob> BlobList = new LinkedList<>();
//        for (String name : WFileName) {
//            File f = new File(CWD + "/" + name);
//            Blob b = new Blob(f);
//            BlobList.add(b);
//        }
//        return BlobList;
//    }

    //得到工作列表的名字。初始化blob的时候可以用
    public void GetWorkFileNames() {
        Utils.plainFilenamesIn(CWD);
    }

    //public void GetBidsName(String Uid){
//        String path= OBJECTS_DIR.getAbsoluteFile().toString()+ Uid;
//        File file=new File(path);
//        Bids=Utils.plainFilenamesIn(file);
//}
    //得到commit的路径,并且创建文件夹。
    public String GetCommitPath() throws IOException {
        return CreatCommitFolder().getAbsolutePath();
    }

    //创建文件夹,并把object写入文件。
    public File CreatCommitFolder() throws IOException {
        File f = Utils.join(COMMIT_DIR, Uid.substring(0, 6));
        f.mkdir();
        File f2 = Utils.join(f.getAbsoluteFile(), Uid);
        f2.createNewFile();
        Utils.writeObject(f2,this);
        return f2;
    }

//    public String GetParent() {
//        return parent.;
//    }

    public String GetMessage() {
        return message;
    }

    public String GetUid() {
        return Uid;
    }
public String Gettimestamp(){
        return timestamp;
}
        public String Getindexstring(){
        String ids=new String();
        for(String id:Bids){
            ids=ids+id;
        }
        return ids;
        }



    /* TODO: fill in the rest of this class. */
    public static void main(String[] args) throws IOException {

    }

}
