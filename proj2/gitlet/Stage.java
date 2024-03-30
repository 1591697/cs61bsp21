package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static gitlet.Repository.*;

public class Stage implements Serializable {
    public static final File CWDS = new File("C:\\Users\\Administrator\\Desktop\\gitlet\\proj2\\gitlet");
    public HashSet<String>  index=new HashSet<>();
    public ArrayList<Blob> Bloblists = new ArrayList<>();//记录存在的Blob文件
    public ArrayList<Blob> Blobs = new ArrayList<>();//从Blob文件中找到的对象
    public HashSet<String> Blobss = new HashSet<>();//从Blob文件中找到的对象
    static public HashMap<String, String> RecordnameShal = new HashMap<String, String>();//记录对应文件名所对应的shal;

    //得到index,has s
    public HashSet<String> GetIndexs() {
        return index;
    }

    public  Stage() throws IOException {
        WriteAddToIndex();

}
//把add-file Shal写到index
    public void WriteAddToIndex() throws IOException {
        if(ADD_FILE.exists()) {
            String s = Utils.readContentsAsString(ADD_FILE);
            String[] as = s.split(" ");
            for (String id : as) {
                if (id.equals("")) {
                    continue;
                }
                index.add(id);
            }
        }
    }

    public void Stage() throws IOException {
        CreateWFileBlob();
        GetIndex(BLOB_DIR);
        WriteAddFile();
    }



    //把index写入add file文件里面
    public void WriteAddFile() {
        File file = new File(ADD_FILE.getAbsolutePath());
      String ids=new String();
        for(String id:index){
         ids=ids+id+" ";
        }
       Utils.writeContents(file,ids);

    }
    //把index 写入remove file文件
    public void WriteRemoveFile(File f) throws IOException {
        Blob b=new Blob(f);
        b.CreatBlobFolder();
        File f1=new File(REMOVW_FILE.getAbsolutePath());
       String con= Utils.readContentsAsString(f1);
        con=con+b.GetId()+" ";
        Utils.writeContents(f1,con);

    }

    //把ID写入index
    public void GetIndex(File file) throws IOException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                GetIndex(f);
            } else if (!f.isDirectory()) {
                Blob b = Utils.readObject(f, Blob.class);
                index.add(b.GetId());
            } else {
                continue;
            }
        }
    }
    //单个修改index......

    //记录object目录里面的文件
    public File GetObjectFile(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                GetObjectFile(f);
            } else {

                return f;
            }
        }
        return null;
    }


    //获取子文件 hashset版
    HashSet<File> FileLists = new HashSet<>();
    public HashSet<File> GetworkFilesList(File file) {
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
    public LinkedList<File> GetworkFiles() {
        LinkedList<File> workfiles = new LinkedList<>();
        HashSet<File> WorkFilesName;
        WorkFilesName = GetworkFilesList(CWD);
        for (File worksfilesname : WorkFilesName) {
            workfiles.add(worksfilesname);
        }
        return workfiles;
    }

    //生成全部的Blob  //得到工作目录里面的文件并生成Blob
    public LinkedList<Blob> CreateWFileBlob() throws IOException {
        HashSet<String> hs=GetObjectBlob(BLOB_DIR);
        LinkedList<File> workfiles;
        LinkedList<Blob> wfBlob = new LinkedList<>();
        workfiles = GetworkFiles();
        if(BLOB_DIR.listFiles().length>0) {
            for (File f : workfiles) {
                Blob b = new Blob(f);
                if (!hs.contains(b.GetId())) {
                    String name = b.GetName();
               Delete(name);
                    System.out.println("delete"+name);

                    WriteAddFile();
                }
            }
        }
        for (File f : workfiles) {
            Blob b = new Blob(f);
            b.CreatBlobFolder();
            System.out.println(b.GetName());
            wfBlob.add(b);
            Bloblists.add(b);
        }
        return wfBlob;
    }
    //生成Blob但是不生成文件
    public LinkedList<Blob> GetWFileBlob() throws IOException {
        LinkedList<File> workfiles;
        LinkedList<Blob> wfBlob = new LinkedList<>();
        workfiles = GetworkFiles();
        for (File f : workfiles) {
            Blob b = new Blob(f);
            wfBlob.add(b);
            Bloblists.add(b);
        }
        return wfBlob;
    }
    //删除Blob文件
    public void Delete(String name) throws IOException {
        String shal = GetSameBlobShal(name);
//        File f = new File(BLOB_DIR + "/" + shal.substring(0, 6) + "/" + shal);
//        f.delete();
//        File f1 = new File(BLOB_DIR + "/" + shal.substring(0, 6));
//        f1.delete();
        if(index.contains(shal)) {
            index.remove(shal);
        }
        WriteAddFile();
    }

    //单个Blob的sha输入index里面
    public void GetOneIndex(String name) throws IOException {
        if (SearchObjectBlob(BLOB_DIR, name)) {
          Delete(name);
          Blobs.clear();
        }
        Blob b = CreatOneBlob(name);
        index.add(b.GetBlobSHA1());
        WriteAddFile();
    }
    //通过文件名生成blob
    public Blob CreatOneBlob(String name) throws IOException {
        LinkedList<File> files = GetworkFiles();
        int k = files.size();
        int i = 0;
        while (k > 0) {
            if (name.equals(files.get(i).getName())) {
                Blob b = new Blob(files.get(i));
                b.CreatBlobFolder();
                Bloblists.add(b);
                RecordnameShal.put(files.get(i).getName(), b.GetBlobSHA1());
                return b;
            }
            k--;
            i++;
        }
        return null;
    }
    //得到相同文件名的文件shal；
    public String GetSameBlobShal(String name) throws IOException {
        if (SearchObjectBlob(BLOB_DIR, name)) {
            Blob b = GetObjectBlobs(name);
            String shal = b.GetId();
            return shal;
        }
        return null;
    }
    //搜索文件是否存在
    public Boolean SearchFile(String name) {
        // LinkedList<File> files=new LinkedList<>();
        LinkedList<File> files = GetworkFiles();
        List<String> names = new ArrayList<>();
        int k = files.size();

        int i = 0;
        while (k > 0) {
            names.add(files.get(i).getName());
            k--;
            i++;
        }
        for (int j = 0; j < names.size(); j++) {
            if (names.get(j).equals(name)) {
                //  System.out.println(names.get(j));
                return true;
            }
        }
        return false;
    }
    //判断是否存在这个blob
    public int i;
    public Boolean SearchObjectBlob(File files, String name) {
        for (File f : files.listFiles()) {
            if (f.isDirectory()) {
                SearchObjectBlob(f, name);
            } else if (!f.isDirectory()) {
                Blob b = Utils.readObject(f, Blob.class);
                if (b.GetName().equals(name)&&index.contains(b.GetId())) {
                    i = 1;
                }
            } else {
                continue;
            }
        }
        if (i == 1) {
            return true;
        }
        return false;
    }
    //从object文件里面得到Blob对象
    private ArrayList<Blob> GetObjectBlob(File files, String name) {

        for (File f : files.listFiles()) {
            if (f.isDirectory()) {
                GetObjectBlob(f, name);
            } else if (!f.isDirectory()) {
                Blob b = Utils.readObject(f, Blob.class);
                if (b.GetName().equals(name)&&index.contains(b.GetId())) {
                    Blobs.add(b);

                }
            } else {
                continue;
            }
        }
        return Blobs;
    }

    public  HashSet<String> GetObjectBlob(File files) {
        for (File f : files.listFiles()) {
            if (f.isDirectory()) {
                GetObjectBlob(f);
            } else if (!f.isDirectory()) {
                Blob b = Utils.readObject(f, Blob.class);
                    Blobss.add(b.GetId());
            } else {
                continue;
            }
        }
        return Blobss;
    }

    public Blob GetObjectBlobs(String name) {
        GetObjectBlob(BLOB_DIR, name);
        return Blobs.get(0);
    }
    //更新
    public void upDate() throws IOException {
        GetIndex(BLOB_DIR);
        WriteAddFile();
    }
    public static void main(String[] args) throws IOException {
        Stage stage = new Stage();
       stage.GetObjectBlobs("Stage.java");
    }
    //清空Blob
    public void clear(File files){
        for(File f:files.listFiles()){
            if(f.isDirectory()){
                clear(f);
                f.delete();
            }
            else{
                f.delete();
            }
        }
    }



}
