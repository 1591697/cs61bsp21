package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static gitlet.Utils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 * TODO: It's a good idea to give a description here of what else this Class
 * does at a high level.
 *
 * @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    /*
     *   .gitlet
     *      |--objects
     *      |     |--commit
     *      |     |--blob
     *      |     |--tree
     *      |--refs
     *      |    |--heads
     *      |         |--master
     *      |--HEAD
     *      |--stage
     *      |   |--add
     *      |   |--remove
     */
    /**
     * The current working directory.
     */
   // public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    public static final File COMMIT_DIR = join(OBJECTS_DIR, "commits");
    public static final File BLOB_DIR = join(OBJECTS_DIR, "blobs");
    public static final File TREE_DIR = join(OBJECTS_DIR, "trees");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File HEADS_DIR = join(REFS_DIR, "heads");
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");
    public static final File ADD_FILE = join(STAGE_DIR, "add");
    public static final File REMOVW_FILE = join(STAGE_DIR, "remove");
    public static Commit currentCommit;//不是静态类型
    public static String currentBreath;

    static Stage currentStage;

    static {
        try {
            currentStage = new Stage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Tree currentTree = new Tree();
    static Vertex currentVertex = new Vertex();

    public static void init() throws IOException {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        BLOB_DIR.mkdir();
        COMMIT_DIR.mkdir();
        TREE_DIR.mkdir();
        REFS_DIR.mkdir();
        HEADS_DIR.mkdir();
        HEAD_FILE.createNewFile();
        STAGE_DIR.mkdir();
        ADD_FILE.createNewFile();
        REMOVW_FILE.createNewFile();
        inithead();
        initCommit();
        initheads();
    }

    public static void initheads() {
        File f = join(HEADS_DIR, "master");
        writeContents(f, currentCommit.GetUid());
    }

    //初始化init
    public static void inithead() {
        Utils.writeContents(HEAD_FILE, "master");
    }

    //初始化
    public static void add() throws IOException {
        currentStage.Stage();
    }

    public static void add(String name) throws IOException {
        // currentStage.upDate();
        if (currentStage.SearchFile(name)) {
            currentStage.GetOneIndex(name);
        } else {
            System.out.println("File does not exist.");
        }
    }

    //
    public static void initCommit() throws IOException {
        currentCommit = new Commit();
        currentCommit.CreatCommitFolder();
    }
//这段有问题，编译不成功。
public  static void mergecommit(ArrayList<String> ndis,String branchname) throws IOException {
   // CheckCommit(message);
    String s=GetBreath();
    Commit newcommit = CreatMergeCommit(ndis,branchname);
    newcommit.CreatCommitFolder();
    RecordCommit(newcommit);
    clearaddfile();
}

    public static void commit(String message) throws IOException {
        CheckCommit(message);
//        if (COMMIT_DIR.listFiles().length == 0) {
//            Commit newcommit = new Commit(message, "end");
//            newcommit.CreatCommitFolder();
//            RecordCommit(newcommit);
//            return;
//        }
        Commit newcommit = CreatNewcommit(message);
        newcommit.CreatCommitFolder();
        RecordCommit(newcommit);
       clearaddfile();
    }
    //清除addfile
    public static void clearaddfile(){
        String s = "";
        writeContents(ADD_FILE, s);
        currentStage.index.clear();
    }
    //获取当前的breath分支

    public static String GetBreath() {
        return readContentsAsString(HEAD_FILE);
    }

    //得到currentcommit文件
    public static Commit GetCurrentCommit() throws IOException {
        String breath = GetBreath();
        //得到文件名
        File f1 = new File(HEADS_DIR, breath);
        String c = readContentsAsString(f1);
        File f2 = new File(COMMIT_DIR, c.substring(0, 6));
        File f3 = new File(f2, c);
        Commit c2 = readObject(f3, Commit.class);
        return c2;
    }

    //创建newcommit()
    public static Commit CreatNewcommit(String message) throws IOException {
        currentCommit = GetCurrentCommit();
        String id = currentCommit.GetUid();
        Commit c = new Commit(message, id);
        return c;
    }

    //保存commit，把对象ID写入file？保存在HEADS，在对应的分支
    public static void RecordCommit(Commit c) {
        File f = join(HEADS_DIR, GetBreath());
        writeContents(f, c.GetUid());

    }

    public static void CheckCommit(String message) {
        if (message == null) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        File f3 = new File(String.valueOf(ADD_FILE.getAbsoluteFile()));
        String a = readContentsAsString(f3);

        if (a.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
    }
//更新
    public static void updategetfile(){
        FileLists=new HashSet<>();
    }
    //获取子文件 hashset版
    static HashSet<File> FileLists = new HashSet<>();

    public static HashSet<File> GetworkFilesList(File file) {
        for (File f1 : file.listFiles()) {
            if (f1.getName().equals(".gitlet") && f1.isDirectory()) {
                continue;
            }
            if (f1.isDirectory()) {
                GetworkFilesList(f1);
            } else {
                FileLists.add(f1);
            }
        }
        return FileLists;
    }

    //获取work文件link版本
    public static LinkedList<File> GetworkFiles() {
        LinkedList<File> workfiles = new LinkedList<>();
        HashSet<File> WorkFilesName;
        WorkFilesName = GetworkFilesList(CWD);
        for (File worksfilesname : WorkFilesName) {
            workfiles.add(worksfilesname);
        }
        return workfiles;
    }

    //在工作目录查找文件对象
//    public File SearchFile(File file,String name) throws IOException {
//        for(File f:file.listFiles()){
//            if(f.isDirectory()){
//                SearchFile(f,name);
//            }
//            else{
//                Blob b=new Blob(f);
//                if(b.GetName().equals(name)){
//                    return
//                }
//            }
//        }
//    }

    //判断add-file 文件中有没有这个文件
    public static Boolean CheckAddfileHasFile(File f) throws IOException {
        Blob b = new Blob(f);
        String ids = readContentsAsString(ADD_FILE);
        String[] a = ids.split(",");
        for (String id : a) {
            if (id.equals(b.GetId())) {
                return true;
            }
        }
        return false;
    }
    //判断index有没有这个文件

    //rm文件搜索
    public static File GetrmFile(String name) {
        updategetfile();
        LinkedList<File> h = GetworkFiles();
        for (File f : h) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    //判断currentcommit的bids有没有文件ID
    public static Boolean CheckcurrcommitBids(Blob b) throws IOException {
        Commit c = GetCurrentCommit();
        List<String> ab = c.Bids;
        for (String id : ab) {
            if (id.equals(b.GetId())) {
                return true;
            }
        }
        return false;
    }
    public static Boolean CheckcurrcommitBidsname(String name) throws IOException {
        Commit c = GetCurrentCommit();
        List<String> ab = c.Bids;
        HashSet<String> hs=new HashSet<>();
        for (String id : ab) {
           File f=new File(BLOB_DIR+"/"+id.substring(0,6)+"/"+id);
           Blob b=Utils.readObject(f,Blob.class);
           hs.add(b.GetName());
            }
        if(hs.contains(name)){
            return true;
        }
        return false;
    }
    //寻找file-rm
    public static File searchrmfile(String name){
        updategetfile();
      LinkedList<File> lf=GetworkFiles();
      for(File f:lf){
          if(f.getName().equals(name)){
              return f;
          }
      }
      return null;
    }

    //rm操作
    public static void rmFile(String name) throws IOException {
        File f=GetrmFile(name);
        if(f.exists()){
            Blob b = new Blob(f);
            if (CheckAddfileHasFile(f)) {
                currentStage.index.remove(b.GetId());
                currentStage.WriteAddFile();
            } else if (CheckcurrcommitBids(b) && GetrmFile(f.getName()) != null) {
                b.CreatBlobFolder();
                currentStage.WriteRemoveFile(f);
                f.delete();
                //删除工作目录里面的文件

            }
        }
        else if (CheckcurrcommitBidsname(name) ) {
            currentStage.WriteRemoveFile(f);
        }
        else {
            System.out.println("no reason to remove the file.");

    }}

    //递归方法log
    public static void printlog(String id) {
        if (id!=null) {
            File f = new File(COMMIT_DIR + "/" + id.substring(0, 6) + "/" + id);
            Commit c1 = readObject(f, Commit.class);
            if (c1.parent.size() == 2) {
                System.out.println("===");
                System.out.println();
                System.out.println("commit " + c1.GetUid());
                System.out.println("Merge:" + c1.parent.get(0).substring(0, 6) + " " + c1.parent.get(1).substring(0, 6));
                System.out.println("Date:" + c1.Gettimestamp());
                System.out.println(c1.GetMessage());
                System.out.println();

            }
            else  {
                System.out.println("===");
                System.out.println();
                System.out.println("commit " + c1.GetUid());
                System.out.println("date:" + c1.Gettimestamp());
                System.out.println(c1.GetMessage());
                System.out.println();

            }
            if(!c1.parent.isEmpty()) {
                printlog(c1.parent.get(0));
            }
        }
    }

    //打印log
    public static void log() throws IOException {
        currentCommit = GetCurrentCommit();
        ArrayList<String> parents = currentCommit.parent;

        printlog(currentCommit.GetUid());

    }

    //判断parent是否有两个
    public static Boolean checkparent(Commit c) {
        if (c.parent.size() == 2) {
            return true;
        }
        return false;
    }

    //不分顺序打印
    public static void global_log(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                global_log(f);
            } else {
                Commit c = readObject(f, Commit.class);
                if (checkparent(c)) {
                    System.out.println("===");
                    System.out.println();
                    System.out.println("commit " + c.GetUid());
                    System.out.println("Merge:" + c.parent.get(0).substring(0, 6) + " " + c.parent.get(1).substring(0, 6));
                    System.out.println("Date:" + c.Gettimestamp());
                    System.out.println(c.GetMessage());
                    System.out.println();
                } else {
                    System.out.println("===");
                    System.out.println();
                    System.out.println("commit " + c.GetUid());
                    System.out.println("date:" + c.Gettimestamp());
                    System.out.println(c.GetMessage());
                    System.out.println();
                }
            }
        }

    }

    //find
    static int i = 0;

    public static void find(File files, String message) {
        for (File f : files.listFiles()) {
            if (f.isDirectory()) {
                find(f, message);
            } else {
                Commit c = Utils.readObject(f, Commit.class);
                if (c.message.equals(message)) {
                    i = 1;
                    System.out.println(c.GetUid());
                }
            }
        }
    }

    public static void find(String message) {
        find(COMMIT_DIR, message);
        if (i == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    //status状态
    public static void status() throws IOException {
        currentCommit = GetCurrentCommit();
        System.out.println("=== Branches ===");
        System.out.println("*" + GetBreath());
        for (File f : HEADS_DIR.listFiles()) {
            if (!f.getName().equals(GetBreath())) {
                System.out.println(f.getName());
            }
        }
        System.out.println("=== Staged Files ===");
        String s1 = Utils.readContentsAsString(ADD_FILE);
        String[] as1 = s1.split(" ");
        for (String id : as1) {
            if (id.equals("")) {
                continue;
            }
            File f1 = new File(BLOB_DIR + "/" + id.substring(0, 6) + "/" + id);
            Blob b1 = Utils.readObject(f1, Blob.class);
            System.out.println(b1.GetName());

        }

        System.out.println("=== Removed Files ===");
        String s2 = Utils.readContentsAsString(REMOVW_FILE);
        String[] as2 = s2.split(" ");
        for (String id : as2) {
            if (id.equals("")) {
                continue;
            }
            File f2 = new File(BLOB_DIR + "/" + id.substring(0, 6) + "/" + id);
            Blob b2 = Utils.readObject(f2, Blob.class);
            System.out.println(b2.GetName());

        }
    }

    //得到工作目录里面的文件
    //判断currentcommit是否有这个blob
    public static Blob checkcurrentbids(List<String> a, String name) {
        for (String id : a) {
            File f = new File(BLOB_DIR + "/" + id.substring(0, 6) + "/" + id);
            Blob b = Utils.readObject(f, Blob.class);
            if (b.GetName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    //替换工作目录里面的文件
    public static void changewffile(Blob b1, HashSet<File> hf, String name) throws IOException {

        for (File f : hf) {
            if (f.getName().equals(name)) {
                f.delete();
            }
        }
        File f=new File(CWD+"/"+b1.GetName());
        byte[] bytes=b1.Getbytes();
        writeContents(f,new String(bytes, StandardCharsets.UTF_8));
    }

    //checkout第一种用法
    public static void checkout1(String name) throws IOException {
        currentCommit = GetCurrentCommit();
        List<String> a = currentCommit.Bids;
        if (checkcurrentbids(a, name) != null) {
            Blob b1 = checkcurrentbids(a, name);
            HashSet<File> hf = currentStage.GetworkFilesList(CWD);
            changewffile(b1, hf, name);
        } else {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }

    }

    //第二种
    public static void checkout2(String id, String name) throws IOException {
        File f = new File(COMMIT_DIR + "/" + id.substring(0, 6) + "/" + id);
        Commit c = Utils.readObject(f, Commit.class);
        List<String> ids = c.Bids;

        if (checkcurrentbids(ids, name) != null) {
            Blob b1 = checkcurrentbids(ids, name);
            HashSet<File> hf = currentStage.GetworkFilesList(CWD);
            changewffile(b1, hf, name);
        } else {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
    }

    //判断分支
    public static Boolean checkbranch(String branchname) {
        File f = new File(HEADS_DIR + "/" + branchname);
        if (!f.exists()) {
            System.out.println("No such branch exists.");
            return false;
        }
        String head = Utils.readContentsAsString(HEAD_FILE);
        if (head.equals(branchname)) {
            System.out.println("No need to checkout the current branch.");
            return false;
        }
        return true;
    }
//得到checkout前该主分支的bids
    public static List<String> GetBeCheckoutBids() throws IOException {

      currentCommit=GetCurrentCommit();
      if(!currentCommit.Bids.isEmpty()) {
          List<String> beids = currentCommit.Bids;
          return beids;
      }
return null;
    }
    //将Blob 里面的文件反写
    public static void writeBlobfile(Blob b){
        File f=new File(CWD+"/"+b.GetName());

        byte[] bytes=b.Getbytes();
        writeContents(f,new String(bytes, StandardCharsets.UTF_8));
    }
    //如果分支内是空白的，第一次提交
    //第三种
    //1. head指向分支的最新的提交
    //2. 将工作文件换成改分支提交的文件
    //2.1 文件名被两者追踪 ，如果ID一样不要改，如果不一样要替换
    //2.2 都没有被追踪，删除
    //2.3 被分支最新提交追踪，则直接添加到工作目录
    //2.4 接2.3，如果有同名文件，则报错
    //2.5 head指向提交
    //
    public static void changewkfileby(List<String>cids,List<String> beids ) throws IOException {

        HashSet<String> ids=new HashSet<>();
        for(String s:cids){
            ids.add(s);
        }
        //剔除不属于的
        for(String id:beids){
            if(!ids.contains(id)){
                File f=new File(BLOB_DIR+"/"+id.substring(0,6)+"/"+id);
                Blob b=Utils.readObject(f,Blob.class);
                String name= b.GetName();
                File f1=new File(CWD+"/"+name);
                f1.delete();
                GetworkFiles();
            }
//            if(ids.contains(id)){
//                ids.remove(id);
//            }
        }
        updategetfile();
        HashSet<File> hsf=GetworkFilesList(CWD);
        for(File f1:hsf){
           Blob b1=new Blob(f1);
            if(ids.contains(b1.GetId())){
                ids.remove(b1.GetId());
            }
        }
        ArrayList<String> nameswk=new ArrayList<>();
        updategetfile();
        LinkedList<File> wkf=GetworkFiles();
        HashSet<String> cname=new HashSet<>();
        for(File f:wkf){
            nameswk.add(f.getName());
        }
        for(String id:ids){
            File f=new File(BLOB_DIR+"/"+id.substring(0,6)+"/"+id);
            Blob b=Utils.readObject(f,Blob.class);
            cname.add(b.GetName());
        }
        for(String name:nameswk) {
            if (cname.contains(name)) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
            else{
                File f=GetrmFile(name);
                f.delete();
            }
        }

        //创建
        for(String id:ids){
            File f=new File(BLOB_DIR+"/"+id.substring(0,6)+"/"+id);
            Blob b=Utils.readObject(f,Blob.class);
            writeBlobfile(b);

        }
    }
    public static void checkout3(String branchname,List<String> beids) throws IOException {
        if (checkbranch(branchname)) {
            Utils.writeContents(HEAD_FILE, branchname);
            currentCommit=GetCurrentCommit();
            List<String>cids=currentCommit.Bids;

            changewkfileby(cids,beids);

        } else {
            System.exit(0);
        }


    }

    //新建一个分支
    public static void Creatbranch(String name) throws IOException {
        File f = join(HEADS_DIR, name);
        f.createNewFile();
        currentCommit = GetCurrentCommit();
        writeContents(f, currentCommit.GetUid());
    }

    //删除分支
    public static void detelebranch(String name) {
        String head = Utils.readContentsAsString(HEAD_FILE);
        if (!head.equals(name)) {
            File f = new File(HEADS_DIR + "/" + name);
            if (f.exists()) {
                f.delete();
            } else {
                System.out.println("A branch with that name does not exist.");
                System.exit(0);
            }
        } else {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }
    }

//reset
    static public int i3;
    //判断commitdir有没有这个ID号
    public static Boolean checkcommitid(File file,String id){
        for(File f:file.listFiles()){
            if(f.isDirectory()){
                checkcommitid(f,id);
            }
            else{
                Commit c=Utils.readObject(f,Commit.class);
                if(c.GetUid().equals(id)){
                  i3=1;
                }

            }
        }
        if(i3==1){
            return true;
        }
        else {
            return false;
        }
    }
    //
    public static Commit Getcommit(String commitid){
        File f=new File(COMMIT_DIR+"/"+ commitid.substring(0,6)+"/"+commitid);
        Commit c=Utils.readObject(f,Commit.class);
        return c;
    }
    //把工作文件调换
    //head指向这个ID
    public static void reset(String commitid) throws IOException {
       if(checkcommitid(COMMIT_DIR,commitid)) {
         Commit c= Getcommit(commitid);
         List<String> bids=c.Bids;
         currentCommit=GetCurrentCommit();
        List<String> cbids=currentCommit.Bids;
           changewkfileby(bids,cbids);
           File f=new File(HEADS_DIR+"/"+GetBreath());
           Utils.writeContents(f,commitid);
           clearaddfile();
       }
       else{
           System.out.println("No commit with that id exists.");
           System.exit(0);
       }
    }

    //深度优先的算法
    public static HashMap<Integer,String> deepfirst(String id){
        HashMap<Integer,String> hm=new HashMap<>();
       Commit c=Getcommit(id);
       int i=0;
       while(!c.parent.isEmpty()){
           hm.put(i++,c.parent.get(c.parent.size()-1));
           c=Getcommit(c.parent.get(c.parent.size()-1));
       }
       return hm;
    }
    //得到当前分支和需要合并分支的hm
    public static String Getbranchesdeepfist(String branchname) throws IOException {
        File f=new File(HEADS_DIR+"/"+branchname);
        String sid=readContentsAsString(f);
        String sid1=new String();
        HashMap<Integer,String> hs1=deepfirst(sid);
        currentCommit=GetCurrentCommit();
        HashMap<Integer,String> hs2=deepfirst(currentCommit.GetUid());
        for(int i=0;i<hs1.size();i++){
            if(hs2.containsValue(hs1.get(i))){
                sid1=hs1.get(i);
              break;
            }
        }
        return sid1;
    }
    //判断是否属于两种情况
    public static void checkmerge(String branchname) throws IOException {
        String sid= Getbranchesdeepfist(branchname);
        currentCommit=GetCurrentCommit();
        if(sid.equals(currentCommit.GetUid())){
            Utils.writeContents(HEAD_FILE,branchname);
            System.out.println("Current branch fast-forwarded.");
            System.exit(0);

        }
        File f1=new File(HEADS_DIR+"/"+branchname);
        String oid=readContentsAsString(f1);
        if(sid.equals(oid)){
            System.out.println("Given branch is an ancestor of the current branch.");
            System.exit(0);
        }
    }
    //获取sids,bids,cids的hashset<String>
    public static HashSet<String> Getidshashset(String id){
        Commit c=Getcommit(id);
        HashSet<String> hs=new HashSet<>();
        for(String s:c.Bids){
            hs.add(s);
        }
        return hs;
    }
    //获取名字
    //删除都有的
//    public static ArrayList<String>detelethesameid(HashSet<String> sids,HashSet<String> cids,HashSet<String> bids,ArrayList<String> nids){
//        for(String id:sids){
//            if(bids.contains(id)&&cids.contains(id)){
//                nids.add(id);
//                sids.remove(id);
//                bids.remove(id);
//                cids.remove(id);
//            }
//        };
//        return nids;
//
//    }
    //判断hashset 的size是否为0
    public static Boolean checkHashsetSize(HashSet<String> hs){
        if(hs.size()==0){
            return true;
        }
        return false;
    }
    //检查
    public static void checkMerge(ArrayList<String> nids) throws IOException {
        updategetfile();
        HashSet<File> hs=GetworkFilesList(CWD);
        HashMap<String,String> hs1=new HashMap<>();
        HashSet<String> names=new HashSet<>();
        HashMap<String,String> names1=new HashMap<>();
        for(File f:hs){
            Blob b=new Blob(f);
            hs1.put(f.getName(),b.GetId());
        }
        for(String id:nids){
            Blob b=Getblob(id);
            names.add(b.GetName());
            names1.put(b.GetName(),b.GetId());
        }
        HashMap<String,String> hm=new HashMap<>();
        for( String name:names){
            if(hs1.containsValue(name)&&!hs1.get(name).equals(names1.get(name))){
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }
    }

    //创建commit

    public static Commit CreatMergeCommit(ArrayList<String> ndis,String branchname) throws IOException {
        currentCommit=GetCurrentCommit();
        checkMerge(ndis);
        Commit c=new Commit();
        ArrayList<String> li=new ArrayList<>();
       File f=new File(HEADS_DIR+"/"+branchname);
       String id=Utils.readContentsAsString(f);
        li.add(currentCommit.GetUid());
        li.add(id);
        c.parent=li;//log打印的要改

        c.message="Merged " +GetBreath()+" into "+branchname+".";
        c.Bids=ndis;
       return c;



    }
    //得到blob的
    public static Blob Getblob(String blobid){
        File f=new File(BLOB_DIR+"/"+blobid.substring(0,6)+"/"+blobid);
        Blob b=Utils.readObject(f,Blob.class);
        return b;
    }
    //获取name的hashset
    public static HashSet<String> GetHashsetName(HashSet<String> ids){

        HashSet<String> hs=new HashSet<>();
        for(String id:ids){
            hs.add(Getblob(id).GetName());
        }
        return hs;
    }
    //得到name ,id 的hashmap
    public static HashMap<String,String> GetHashmapNameId(HashSet<String> ids){

        HashMap<String,String> hs=new HashMap<>();
        for(String id:ids){
            Blob b=Getblob(id);
            hs.put(b.GetName(),b.GetId());
        }
        return hs;
    }
   //冲突
    public static void ConfitMerge(HashMap<String,String> bidsnameid,String name,HashMap<String,String> cidsnameid){
      String id1=  bidsnameid.get(name);
        String id2=  cidsnameid.get(name);
File f=new File(CWD+"/"+name);
        Blob b1=Getblob(id1);
        Blob b2=Getblob(id2);
      //  File f=new File(CWD+"/"+b.GetName());

        byte[] bytes=b1.Getbytes();
        writeContents(f,new String(bytes, StandardCharsets.UTF_8));
       String content1= readContentsAsString(f);
        byte[] bytes2=b2.Getbytes();
        writeContents(f,new String(bytes2, StandardCharsets.UTF_8));
        String content2= readContentsAsString(f);
        String conflictContents = "<<<<<<< HEAD\n" + content2 + "=======\n" + content1 + ">>>>>>>\n";
        writeContents(f,conflictContents);
    }
    public static void merge(String branchname) throws IOException {
        ArrayList<String> nids=new ArrayList<>();
       String s=readContentsAsString(ADD_FILE) ;
        if(!s.isEmpty()){
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }
       if(!checkbranch(branchname)) {
           System.out.println("A branch with that name does not exist.");
           System.exit(0);
       }
       if(branchname.equals(GetBreath())){
           System.out.println("Cannot merge a branch with itself.");
           System.exit(0);
       }

        if(!branchname.equals(GetBreath())) {
            checkmerge(branchname);
            String sid= Getbranchesdeepfist(branchname);
            //sids
            HashSet<String> sids=Getidshashset(sid);
           currentCommit=GetCurrentCommit();
        //cids
            HashSet<String> cids=Getidshashset(currentCommit.GetUid());
            //bids
            File f=new File(HEADS_DIR+"/"+branchname);
            String bid=readContentsAsString(f);
            HashSet<String> bids=Getidshashset(bid);
        //记录新的ID

            //第一次删除全部都有的。
            for(String id:sids){
                if(bids.contains(id)&&cids.contains(id)){
                    nids.add(id);
                    sids.remove(id);
                    bids.remove(id);
                    cids.remove(id);
                }
            }
            HashSet<String>sidsname=GetHashsetName(sids);
            HashSet<String>cidsname=GetHashsetName(cids);
            HashSet<String>bidsname=GetHashsetName(bids);
            HashMap<String,String>sidsnameid=GetHashmapNameId(sids);
            HashMap<String,String>cidsnameid=GetHashmapNameId(cids);
            HashMap<String,String>bidsnameid=GetHashmapNameId(bids);
            //第二次
            if(!checkHashsetSize(sids)){
                for(String name:sidsname){
                    if(bidsname.contains(name)&&cidsname.contains(name)){
                        if(bidsnameid.get(name).equals(cidsnameid.get(name))&&!bidsnameid.get(name).equals(sidsnameid.get(name))){
                            nids.add(bidsnameid.get(name));
                            sidsname.remove(name);
                            bidsname.remove(name);
                            cidsname.remove(name);
                            sidsnameid.remove(name);
                            cidsnameid.remove(name);
                            bidsnameid.remove(name);
                        }
                        if(!bidsnameid.get(name).equals(cidsnameid.get(name))&&bidsnameid.get(name).equals(sidsnameid.get(name))){
                            nids.add(cidsnameid.get(name));
                            sidsname.remove(name);
                            bidsname.remove(name);
                            cidsname.remove(name);
                            sidsnameid.remove(name);
                            cidsnameid.remove(name);
                            bidsnameid.remove(name);
                        }
                        if(!bidsnameid.get(name).equals(cidsnameid.get(name))&&cidsnameid.get(name).equals(sidsnameid.get(name))){
                            nids.add(bidsnameid.get(name));
                            sidsname.remove(name);
                            bidsname.remove(name);
                            cidsname.remove(name);
                            sidsnameid.remove(name);
                            cidsnameid.remove(name);
                            bidsnameid.remove(name);
                        }
                        if(!bidsnameid.get(name).equals(cidsnameid.get(name))&&!bidsnameid.get(name).equals(sidsnameid.get(name))&&!cidsnameid.get(name).equals(sidsnameid.get(name))){
                          //冲突
                            ConfitMerge(bidsnameid,name,cidsnameid);
                        }


                    }
                    if(bidsname.contains(name)&&!cidsname.contains(name)){
                        nids.add(bidsnameid.get(name));
                        sidsname.remove(name);
                        bidsname.remove(name);
                        sidsnameid.remove(name);
                        bidsnameid.remove(name);
                    }
                    if(!bidsname.contains(name)&&cidsname.contains(name)){
                        nids.add(cidsnameid.get(name));
                        sidsname.remove(name);
                        cidsname.remove(name);
                        sidsnameid.remove(name);
                        cidsnameid.remove(name);
                    }
                    if(!bidsname.contains(name)&&!cidsname.contains(name)){

                    }


                }
            }
            else{
                if(bidsname.size()!=0&&cidsname.size()!=0){
                    for(String name:bidsname){
                        if(cidsname.contains(name)){
                            if(bidsnameid.get(name).equals(cidsnameid.get(name))){
                                nids.add(cidsnameid.get(name));
                                bidsname.remove(name);
                                bidsnameid.remove(name);
                                cidsname.remove(name);
                                cidsnameid.remove(name);
                            }
                        }
                        else{
                            nids.add(bidsnameid.get(name));
                            bidsname.remove(name);
                            bidsnameid.remove(name);
                        }
                    }
                }
                if(bidsname.size()==0&&cidsname.size()!=0){
                    for(String name:cidsname){
                        nids.add(cidsnameid.get(name));
                        cidsname.remove(name);
                        cidsnameid.remove(name);
                    }

                }
            }


        }
        mergecommit(nids,branchname);

    }
    public static void checkinit(){
        File f=new File(CWD+"/"+".gitlet");
        if(f.exists()){
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
    public static void main(String[] args) throws IOException {

    }

}
